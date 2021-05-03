
/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.EstateProtectionOrder;
import cn.repeatlink.common.entity.OmDeductionRecord;
import cn.repeatlink.common.mapper.EstateProtectionOrderMapper;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.general.common.GeneralRuntimeException;
import cn.repeatlink.module.judicial.common.Constant.OmRecordType;
import cn.repeatlink.module.judicial.common.Constant.ProtectOrderPayTag;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.mapper.ProtectOrderMapper;
import cn.repeatlink.module.judicial.service.IOmRecordService;
import cn.repeatlink.module.judicial.service.IProtectOrderService;
import cn.repeatlink.module.judicial.vo.order.ProtectOrderVo;
import cn.repeatlink.module.judicial.vo.order.ProtectPlanVo;

/**
 * @author LAI
 * @date 2020-10-29 10:37
 */

@Service
public class ProtectOrderServiceImpl implements IProtectOrderService {
	
	@Autowired
	private EstateProtectionOrderMapper estateProtectionOrderMapper;
	
	@Autowired
	private ProtectOrderMapper protectOrderMapper;
	
	@Autowired
	private IOmRecordService omRecordService;
	
	@Override
	public ProtectOrderVo createOrder(String userId, String setId, ProtectPlanVo vo) {
		// 不检验保护日期重复与否
		Date nowtime = new Date();
		EstateProtectionOrder order = new EstateProtectionOrder();
		order.setOrderId(IDUtil.nextID());
		// 40位数字(15 + 25)的订单号
		order.setOrderNo(DateUtil.format(nowtime, "yyyyMMddHHmmsss") + StringUtils.left(StringUtils.leftPad(IDUtil.nextID(), 25, "0"), 25));
		order.setSetId(setId);
		order.setCustomerId(userId);
		order.setStartDate(vo.getStartDate());
		order.setEndDate(vo.getEndDate());
		order.setPrice(vo.getPrice());
		order.setActualPrice(vo.getActualPrice());
		order.setOffsetAmount(order.getPrice() - order.getActualPrice());
		order.setPayTag(ProtectOrderPayTag.NOT_PAY);
		order.setStatus(Constant.STATUS_VALID);
		order.setCreatedBy(userId);
		order.setCreateTime(nowtime);
		order.setName(vo.getName());
		order.setRemark(vo.getRemark());
		
		if(this.estateProtectionOrderMapper.insert(order) <= 0) {
			throw JudicialRuntimeException.build("estate.protection.create.order.error.save.fail");
		}
		return this.convert2Vo(order);
	}
	
	@Override
	public ProtectOrderVo createUniqueOrder(String userId, String setId, ProtectPlanVo vo) {
		// 检验保护日期重复与否
		List<EstateProtectionOrder> orderList = this.protectOrderMapper.findCustomerOrderList(userId, setId, Constant.STATUS_VALID);
		if(orderList != null) {
			for (EstateProtectionOrder order : orderList) {
				if((order.getStartDate().before(vo.getStartDate()) && order.getEndDate().after(vo.getStartDate()))
						|| (order.getStartDate().before(vo.getEndDate()) && order.getEndDate().after(vo.getEndDate()))) {
					throw JudicialRuntimeException.build("general.user.estate.protection.order.join.error.date.repeat");
				}
			}
		}
		
		Date nowtime = new Date();
		EstateProtectionOrder order = new EstateProtectionOrder();
		order.setOrderId(IDUtil.nextID());
		// 40位数字(15 + 25)的订单号
		order.setOrderNo(DateUtil.format(nowtime, "yyyyMMddHHmmsss") + StringUtils.left(StringUtils.leftPad(IDUtil.nextID(), 25, "0"), 25));
		order.setSetId(setId);
		order.setCustomerId(userId);
		order.setStartDate(vo.getStartDate());
		order.setEndDate(vo.getEndDate());
		order.setPrice(vo.getPrice());
		order.setActualPrice(vo.getActualPrice());
		order.setOffsetAmount(order.getPrice() - order.getActualPrice());
		order.setPayTag(ProtectOrderPayTag.NOT_PAY);
		order.setStatus(Constant.STATUS_VALID);
		order.setCreatedBy(userId);
		order.setCreateTime(nowtime);
		order.setName(vo.getName());
		order.setRemark(vo.getRemark());
		order.setPayTimes(0);
		
		if(this.estateProtectionOrderMapper.insert(order) <= 0) {
			throw JudicialRuntimeException.build("estate.protection.create.order.error.save.fail");
		}
		return this.convert2Vo(order);
	}
	
	@Override
	public void payOrder(String operUserId, String orderId, String orderno, Long money) {
		// 2020-12-22
		EstateProtectionOrder order = this.estateProtectionOrderMapper.selectByPrimaryKey(orderId);
		// 保护订单未找到
		if(order == null) {
			throw GeneralRuntimeException.build("estate.protection.order.not.found");
		}
		// 保护订单号不一致
		if(!StringUtils.equals(order.getOrderNo(), orderno)) {
			throw GeneralRuntimeException.build("estate.protection.order.orderno.error");
		}
		
		String recordId = null;
		try {
			// 创建支付订单
			recordId = this.omRecordService.createRecord(order.getCustomerId(), OmRecordType.DEDUCT, order.getOrderNo(), order.getActualPrice(), order.getName());
		} catch (Exception e) {
			// 创建支付订单失败
			order.setUpdateTime(new Date());
			order.setUpdatedBy(operUserId);
			order.setPayTag(ProtectOrderPayTag.PAY_FAIL);
			this.estateProtectionOrderMapper.updateByPrimaryKey(order);
			throw e;
		}
		// 支付
		boolean result = this.omRecordService.deductRecord(recordId);
		order.setUpdateTime(new Date());
		order.setUpdatedBy(operUserId);
		order.setPayTimes(order.getPayTimes() == null ? 1 : (order.getPayTimes() + 1));
		// 支付失败
		if(!result) {
			order.setPayTag(ProtectOrderPayTag.PAY_FAIL);
			this.estateProtectionOrderMapper.updateByPrimaryKey(order);
			throw GeneralRuntimeException.build("estate.protection.order.pay.fail");
		}
		order.setPayTag(ProtectOrderPayTag.PAYED);
		OmDeductionRecord record = this.omRecordService.getRecord(recordId);
		order.setPayAmount(record.getAmount());
		order.setPayTime(record.getDeductionTime());
		this.estateProtectionOrderMapper.updateByPrimaryKey(order);
	}
	
	@Transactional
	@Override
	public void updateByTerminate(String customerId, String setId, Date terminateDate, String terminateRemark) {
		List<EstateProtectionOrder> orderList = this.protectOrderMapper.getEstateProtectionOrderByDate(customerId, setId, DateUtil.formatDate(terminateDate), Constant.STATUS_VALID);
		if(orderList != null) {
			Date nowtime = new Date();
			for(EstateProtectionOrder order : orderList) {
				order.setEndDate(terminateDate);
				order.setUpdatedBy(customerId);
				order.setUpdateTime(nowtime);
				if(StringUtils.isNotBlank(terminateRemark)) {
					order.setRemark((StringUtils.isBlank(order.getRemark()) ? "" : (order.getRemark() + "\r\n")) + (DateUtil.formatDateTime(order.getUpdateTime()) + " " + terminateRemark));
				}
				// 未支付记录变成无效
				if(!ProtectOrderPayTag.PAYED.equals(order.getPayTag())) {
					order.setStatus(Constant.STATUS_INVALID);
				}
				this.estateProtectionOrderMapper.updateByPrimaryKey(order);
			}
		}
	}
	
	private ProtectOrderVo convert2Vo(EstateProtectionOrder order) {
		ProtectOrderVo vo = new ProtectOrderVo();
		vo.setOrder_id(order.getOrderId());
		vo.setOrder_no(order.getOrderNo());
		vo.setCustomer_id(order.getCustomerId());
		vo.setPrice(order.getPrice());
		vo.setActual_price(order.getActualPrice());
		vo.setOffset_amount(order.getOffsetAmount());
		vo.setName(order.getName());
		vo.setEnd_date(order.getEndDate());
		vo.setStart_date(order.getStartDate());
		vo.setStatus(order.getStatus());
		vo.setCreate_time(order.getCreateTime());
		vo.setPay_tag(order.getPayTag());
		vo.setPay_amount(order.getPayAmount());
		vo.setPay_time(order.getPayTime());
		vo.setCreated_by(order.getCreatedBy());
		vo.setRemark(order.getRemark());
		
		return vo;
	}

}
