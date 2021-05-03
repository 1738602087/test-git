/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.CaseBuyer;
import cn.repeatlink.common.entity.CaseBuyerChangeRecord;
import cn.repeatlink.common.mapper.CaseBuyerChangeRecordMapper;
import cn.repeatlink.framework.cache.CacheKit;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.ICaseBuyerChangeService;
import cn.repeatlink.module.judicial.vo.estatecase.BuyerInfoChangeItemVo;

/**
 * @author LAI
 * @date 2020-09-27 15:37
 */

@Service
public class CaseBuyerChangeServiceImple implements ICaseBuyerChangeService {
	
	@Autowired
	private CaseBuyerChangeRecordMapper caseBuyerChangeRecordMapper;
	
	@Transactional
	@Override
	public void saveChangeRecord(String operaUserId, CaseBuyer oldBuyer, CaseBuyer newBuyer) {
		final String[] fieldNames = { "fullname", "fullnameKana", "gender", "birthday", "addr:addr1+addr2+addr3" };
		Class<?> clazz = CaseBuyer.class;
		Date nowtime = new Date();
		for (String fieldName : fieldNames) {
			String oldValue = null;
			String newValue = null;
			// 多字段值合并处理
			if(fieldName.contains(":")) {
				String[] str1 = fieldName.split(":");
				fieldName = str1[0].trim();
				String[] str2 = str1[1].split("\\+");
				oldValue = "";
				newValue = "";
				for(String key : str2) {
					Object oldVal = BeanUtil.getFieldValue(oldBuyer, key.trim());
					Object newVal = BeanUtil.getFieldValue(newBuyer, key.trim());
					oldValue += (oldVal == null ? "" : StringUtils.trimToNull(oldVal.toString()));
					newValue += (newVal == null ? "" : StringUtils.trimToNull(newVal.toString()));
				}
			}
			else {
				Object oldVal = BeanUtil.getFieldValue(oldBuyer, fieldName);
				Object newVal = BeanUtil.getFieldValue(newBuyer, fieldName);
				oldValue = oldVal == null ? null : StringUtils.trimToNull(oldVal.toString());
				newValue = newVal == null ? null : StringUtils.trimToNull(newVal.toString());
			}
			if(!StringUtils.equals(oldValue, newValue)) {
				String fieldType = null;
				Field field = ClassUtil.getDeclaredField(clazz, fieldName);
				if(field != null) {
					Class<?> type = field.getType();
					fieldType = type.getSimpleName();
				} else {
					fieldType = "String";
				}
				
				CaseBuyerChangeRecord record = new CaseBuyerChangeRecord();
				record.setId(IDUtil.nextID());
				record.setCaseId(oldBuyer.getCaseId());
				record.setUserGeneralId(oldBuyer.getUserGeneralId());
				record.setActionBy(operaUserId);
				record.setActionTime(nowtime);
				record.setCreatedBy(operaUserId);
				record.setCreateTime(nowtime);
				record.setStatus(Constant.STATUS_VALID);
				record.setFieldName(fieldName);
				record.setFieldType(fieldType);
				record.setValueBefore(oldValue);
				record.setValueAfter(newValue);
				
				if(this.caseBuyerChangeRecordMapper.insert(record) <= 0) {
					//
					throw JudicialRuntimeException.build("judicial.case.update.buyer.error.add.record.fail");
				}
			}
		}
		
	}
	
	@Override
	public List<BuyerInfoChangeItemVo> getChangeRecordList(String caseId, String buyerId) {
		List<Record> list = Db.find("select * from case_buyer_change_record where case_id=? and user_general_id=? and `status`=? order by action_time desc ", caseId, buyerId, Constant.STATUS_VALID);
		List<BuyerInfoChangeItemVo> itemList = new ArrayList<>();
		if(list != null) {
			int index = 1;
			for (Record r : list) {
				BuyerInfoChangeItemVo itemVo = convert(r.getStr("field_name"), r.getStr("field_type"), r.getStr("value_before"), r.getStr("value_after"), r.getDate("action_time"));
				if(itemVo != null) {
					itemVo.setId("" + (index++));
					itemList.add(itemVo);
				}
			}
		}
		return itemList;
	}
	
	private BuyerInfoChangeItemVo convert(String fieldName, String fieldType, String beforeValue, String afterValue, Date actionTime) {
		BuyerInfoChangeItemVo item = null;
		Kv kv = getFieldConfigCache();
		Kv conf = kv.getAs(fieldName);
		if(conf == null) {
			return item;
		}
		item = new BuyerInfoChangeItemVo();
		item.setField_name(conf.getStr("name"));
		item.setAction_time(actionTime);
		item.setBefore_value(((ICallback)conf.getAs("callback")).call(beforeValue));
		item.setAfter_value(((ICallback)conf.getAs("callback")).call(afterValue));
		return item;
	}
	
	private Kv getFieldConfigCache() {
		String cacheKey = "case_buyer_change_field_configs";
		Kv kv = CacheKit.use(Define.CACHE_NAME_TEMP).get(cacheKey);
		if(kv == null || kv.isEmpty()) {
			kv = Kv.create();
			kv.set("fullname", Kv.by("name", "姓名(漢字)").set("callback", new ICallback() {
				@Override
				public String call(String src) {
					return src;
				}
			}));
			kv.set("fullnameKana", Kv.by("name", "姓名(フリガナ)").set("callback", new ICallback() {
				@Override
				public String call(String src) {
					return src;
				}
			}));
			kv.set("birthday", Kv.by("name", "生年月日").set("callback", new ICallback() {
				@Override
				public String call(String src) {
					try {
						return DateUtil.parse(src).toDateStr();
					} catch (Exception e) {
						return "-";
					}
				}
			}));
			kv.set("gender", Kv.by("name", "性別").set("callback", new ICallback() {
				@Override
				public String call(String src) {
					if("0".equals(src)) {
						return "女";
					}
					if("1".equals(src)) {
						return "男";
					}
					return "未知";
				}
			}));
			kv.set("addr", Kv.by("name", "住所").set("callback", new ICallback() {
				@Override
				public String call(String src) {
					return src;
				}
			}));
			
//			CacheKit.use(Define.CACHE_NAME_TEMP).put(cacheKey, kv);
		}
		return kv;
	}

	private static interface ICallback {
		String call(String src);
	}
}
