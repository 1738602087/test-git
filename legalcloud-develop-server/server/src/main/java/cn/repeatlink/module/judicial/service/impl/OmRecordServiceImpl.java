/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.json.JSONUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.OmDeductionRecord;
import cn.repeatlink.common.mapper.OmDeductionRecordMapper;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.general.mapper.GeneralOmMapper;
import cn.repeatlink.module.judicial.common.Constant.OmRecordType;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IOmRecordService;
import cn.repeatlink.module.judicial.service.IOmiseService;
import cn.repeatlink.module.judicial.vo.order.OmiseResultVo;
import cn.repeatlink.module.law.common.Constant.OmRecordStatus;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-10-29 11:34
 */

@Log4j2
@Service
public class OmRecordServiceImpl implements IOmRecordService {
	
	private static final Cache<String, OmDeductionRecord> RECORD_LOCK_CACHE = CacheBuilder.newBuilder().initialCapacity(1000).concurrencyLevel(10)
			.expireAfterAccess(10, TimeUnit.MINUTES).build();
	
	@Autowired
	private OmDeductionRecordMapper omDeductionRecordMapper;
	
	@Autowired
	private GeneralOmMapper omMapper;
	
	@Autowired
	private IOmiseService omiseService;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String createRecord(String customerId, Short type, String orderNo, Long amount, String desc) {
		Date nowtime = new Date();
		OmDeductionRecord record = new OmDeductionRecord();
		record.setId(IDUtil.nextID());
		record.setCustomerId(customerId);
		record.setType(type);
		record.setOrderNo(orderNo);
		record.setAmount(amount);
		record.setDesc(desc);
		record.setCreatedBy(customerId);
		record.setCreateTime(nowtime);
		record.setStatus(OmRecordStatus.NOT_REQ);
		// 设定元数据，关联双方信息，便于查找记录
		record.setMetadata(JSONUtil.toJsonStr(Kv.by("orderno", record.getOrderNo()).set("recordid", record.getId()).set("customerid", record.getCustomerId()).set("amount", record.getAmount())));
		// 使用默认卡
		Record credit = Db.findFirst("select * from om_customer_credit where customer_id=? and `status`=? and is_selected=? ", customerId, Constant.STATUS_VALID, Boolean.TRUE);
		if(credit == null) {
			throw JudicialRuntimeException.build("general.user.credit.card.default.not.found");
		}
		record.setCustomerCode(credit.getStr("customer_code"));
		record.setCardCode(credit.getStr("card_code"));
		record.setCardNo(credit.getStr("card_no"));
		record.setCreditCompanyName(credit.getStr("company_name"));
		record.setCreditHolderName(credit.getStr("name"));
		record.setCreditValidDate(credit.getStr("valid_date"));
		if(this.omDeductionRecordMapper.insert(record) <= 0) {
			throw JudicialRuntimeException.build("om.error.create.record.save.fail");
		}
		return record.getId();
	}
	
	@Override
	public OmDeductionRecord getRecord(String recordId) {
		return this.omDeductionRecordMapper.selectByPrimaryKey(recordId);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public boolean deductRecord(String recordId) {
		OmDeductionRecord lockInstance = this.getRecordLockInstance(recordId);
		synchronized (lockInstance) {
			OmDeductionRecord record = this.omDeductionRecordMapper.selectByPrimaryKey(recordId);
			// 非扣款类型
			if(!OmRecordType.DEDUCT.equals(record.getType())) {
				return false;
			}
			
			// 无效记录和已成功扣款记录不可进行扣款
			if(OmRecordStatus.INVALID.equals(record.getStatus()) || OmRecordStatus.SUCCESS.equals(record.getStatus())) {
				return false;
			}
			// 开始请求
			Db.update("update om_deduction_record set `status` = ? where id = ? "
					, OmRecordStatus.NOT_REQ.equals(record.getStatus()) ? OmRecordStatus.REQ_OM : OmRecordStatus.REQ_OM_AGAIN, recordId);
			
			// 调用OMISE接口
			OmiseResultVo resultVo = null;
			try {
				resultVo = this.omiseService.deduct(record.getCustomerCode(), record.getCardCode(), record.getAmount(), record.getDesc(), StringUtils.isBlank(record.getMetadata()) ? null : Kv.create().set(JSONUtil.toBean(record.getMetadata(), java.util.Map.class)));
			} catch (Exception e) {
				log.error("调用OMISE接口发生错误", e);
			}
			// 
			record.setUpdatedBy(record.getCustomerId());
			record.setUpdateTime(new Date());
			if(resultVo == null) {
				record.setStatus(OmRecordStatus.FAIL);
				record.setDeductionErrorMsg("调用OMISE接口发生错误");
			}
			else {
				record.setStatus(resultVo.getStatus() != null ? resultVo.getStatus() : OmRecordStatus.FAIL);
				record.setDeductionDate(resultVo.getDeduction_date());
				record.setDeductionTime(resultVo.getDeduction_time());
				record.setDeductionOrderno(resultVo.getDeduction_orderno());
				record.setOriginDeductionOrderno(resultVo.getOrigin_deduction_orderno());
				record.setDeductionErrorCode(resultVo.getDeduction_error_code());
				record.setDeductionErrorMsg(resultVo.getDeduction_error_msg());
			}
			this.omDeductionRecordMapper.updateByPrimaryKey(record);
			
			return OmRecordStatus.SUCCESS.equals(record.getStatus());
		}
	}

	private OmDeductionRecord getRecordLockInstance(String recordId) {
		OmDeductionRecord record = RECORD_LOCK_CACHE.getIfPresent(recordId);
		if(record == null) {
			synchronized (this) {
				record = RECORD_LOCK_CACHE.getIfPresent(recordId);
				if(record == null) {
					record = new OmDeductionRecord();
					record.setId(recordId);
					RECORD_LOCK_CACHE.put(recordId, record);
				}
			}
		}
		return record;
	}
}
