/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.EstateProtection;
import cn.repeatlink.common.mapper.EstateProtectionMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.general.common.Define.ConfigKeys;
import cn.repeatlink.module.general.common.GeneralRuntimeException;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import cn.repeatlink.module.general.vo.payment.EstateEasyVo;
import cn.repeatlink.module.judicial.service.IProtectOrderService;
import cn.repeatlink.module.judicial.service.IProtectService;
import cn.repeatlink.module.judicial.vo.order.ProtectOrderVo;
import cn.repeatlink.module.judicial.vo.order.ProtectPlanVo;
import lombok.extern.log4j.Log4j2;

/**
 * 保护服务类
 * @author LAI
 * @date 2020-12-29 11:22
 */

@Log4j2
@Service
public class ProtectServiceImpl implements IProtectService {
	
	private static final Cache<String, Object> CUS_LOCK_CACHE = CacheBuilder.newBuilder().initialCapacity(1000).concurrencyLevel(10)
			.expireAfterAccess(10, TimeUnit.MINUTES).build();

	@Autowired
	private EstateProtectionMapper estateProtectionMapper;
	
	@Autowired
	private IProtectOrderService protectOrderService;
	
	@Autowired
	private IGeneralUserService GeneralUserService;
	
	@Override
	public List<AutoDeductionVo> getProtectInfo(String customerId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT                                                                               ");
		sql.append(" 	ei.estate_id,  ei.set_id,                                                                   ");
		sql.append(" 	concat( ifnull(ei.addr,''), ifnull(ei.addr2,''), ifnull(ei.house_id,'') ) AS `estate_addr`,  ");
		sql.append(" 	ep.money,                                                                         ");
		sql.append(" 	ep.title,                                                                         ");
		sql.append(" 	ep.remark,                                                                        ");
		sql.append("    ep.id,                                                                           ");
		sql.append(" 	ep.start_date,                                                                    ");
		sql.append(" 	ep.next_deduct_date,                                                              ");
		sql.append(" 	ep.quit_date                                                                      ");
		sql.append(" FROM                                                                                 ");
		sql.append(" 	( SELECT * FROM estate_user eu WHERE user_general_id = ? AND `status` = ? ) eu    ");
		sql.append(" 	LEFT JOIN estate_info ei ON eu.estate_id = ei.estate_id                           ");
		sql.append(" 	AND ei.`status` = ?                                                               ");
		sql.append(" 	LEFT JOIN estate_protection ep ON ep.set_id = ei.set_id                     ");
		sql.append(" 	AND ep.customer_id = ?                                                            ");
		sql.append(" 	AND ep.`status` = ?                                                               ");
		sql.append(" WHERE                                                                                ");
		sql.append(" 	ei.estate_id IS NOT NULL                                                          ");
		sql.append(" ORDER BY ei.set_id DESC, ei.estate_id DESC                                                          ");
		params.add(customerId);
		params.add(Constant.STATUS_VALID);
		params.add(Constant.STATUS_VALID);
		params.add(customerId);
		params.add(Constant.STATUS_VALID);
		
		List<Record> recordList = Db.find(sql.toString(), params.toArray());
		List<AutoDeductionVo> voList = new ArrayList<>();
		if(recordList != null) {
			String setId = null;
			for(Record record : recordList) {
				// 组合区分
				if(setId == null || !StringUtils.equals(setId, record.getStr("set_id"))) {
					AutoDeductionVo vo = new AutoDeductionVo();
					vo.setSet_id(record.getStr("set_id"));
					vo.setEstate_list(new ArrayList<>());
					String id = record.getStr("id");
					if(StringUtils.isNotBlank(id)) {
						vo.setContract_date(record.getDate("start_date"));
						vo.setNext_deduct_date(record.getDate("next_deduct_date"));
						vo.setQuit_date(record.getDate("quit_date"));
						vo.setMoney(record.getLong("money"));
						vo.setRemark(record.getStr("remark"));
						vo.setTitle(record.getStr("title"));
						vo.setStatus(Constant.ENABLE);
						vo.setCard_no(Db.queryStr("select card_no from om_customer_credit where customer_id=? and is_selected=? and `status`=? ", customerId, true, Constant.STATUS_VALID));
						vo.setHolder_name(Db.queryStr("select ifnull(fullname,'') from user_general where user_id=? ", customerId));
					} else {
						vo.setStatus(Constant.DISABLED);
						// 未加入时，设置默认购买价格、标题等信息
						Kv order = this.getConfigOrderInfo();
						vo.setMoney(order.getLong("money"));
						vo.setTitle(order.getStr("title"));
						vo.setRemark(order.getStr("remark"));
					}
					voList.add(vo);
					setId = vo.getSet_id();
				}
				// 向组合里添加关联的物件
				EstateEasyVo eev = new EstateEasyVo();
				eev.setEstate_id(record.getStr("estate_id"));
				eev.setEstate_addr(record.getStr("estate_addr"));
				voList.get(voList.size() - 1).getEstate_list().add(eev);
			}
		}
		return voList;
	}
	
	@Override
	public void join(String customerId, String setId) {
		Object lockInstance = this.getLockInstance(customerId);
		// 防止同个顾客ID重复提交
		synchronized (lockInstance) {
			final Kv kv = Kv.create();
			try {
				// 使用事务
				boolean result = Db.tx(new IAtom() {
					@Override
					public boolean run() throws SQLException {
						Record record = Db.findFirst("select * from estate_protection where customer_id = ? and set_id=? and `status`=?", customerId, setId, Constant.STATUS_VALID);
						if(record != null) {
							throw GeneralRuntimeException.build("general.user.estate.protection.join.error.exist");
						}
						// 2021-02-22
						// 同一个房产只需要一个名义人加入
						Record rr = Db.findFirst("select * from estate_protection where set_id=? and `status`=?", setId, Constant.STATUS_VALID);
						if(rr != null && !StringUtils.equals(customerId, rr.getStr("customer_id"))) {
							throw GeneralRuntimeException.build("general.user.estate.protection.join.error.exist");
						}
						// 2021-02-23
						// set_id有效性
						rr = null;
						rr = Db.findFirst("select ei.* from (select * from estate_info where set_id=? and `status`=?) ei LEFT JOIN estate_user eu ON eu.estate_id = ei.estate_id "
								+ "AND eu.`status` = ? WHERE eu.user_general_id = ? ", setId, Constant.STATUS_VALID, Constant.STATUS_VALID, customerId);
						if(rr == null) {
							throw GeneralRuntimeException.build("msg.resource.not.found");
						}
						
						record = new Record();
						record.set("id", IDUtil.nextID());
						record.set("set_id", setId);
						record.set("customer_id", customerId);
						record.set("scope", 1);
						record.set("start_date", new Date());
						record.set("status", Constant.STATUS_VALID);
						record.set("created_by", customerId);
						record.set("create_time", new Date());
						Kv kv = getConfigOrderInfo();
						
						
						List<Record> estateList = Db.find("select * from estate_info where set_id=? and `status`=? ", setId, Constant.STATUS_VALID);
						StringBuffer title = new StringBuffer();
						if(estateList != null) {
							for (Record r : estateList) {
								if(title.length() > 0) {
									title.append("·");
								}
								title.append(StringUtils.trimToEmpty(r.getStr("addr")) + StringUtils.trimToEmpty(r.getStr("addr2")) + StringUtils.trimToEmpty(r.getStr("house_id")));
							}
						}
						record.set("money", kv.get("money"));
						record.set("title", StringUtils.left(kv.getStr("title")/* + " - " + title */, 200));
						record.set("remark", kv.get("remark"));
						
						record.set("next_deduct_date", record.get("start_date"));
						kv.set("protectionId", record.getStr("id"));
						return Db.save("estate_protection", "id", record);
					}
				});
				if(!result) {
					throw new RuntimeException("result: false");
				}
				
				Record record = Db.findFirst("select * from estate_protection where customer_id = ? and set_id=? and `status`=?", customerId, setId, Constant.STATUS_VALID);
				ProtectPlanVo vo = new ProtectPlanVo();
				vo.setPrice(record.getLong("money"));
				vo.setActualPrice(vo.getPrice());
				// 开始日2020-12-29
				vo.setStartDate(record.getDate("start_date"));
				// 结束日2021-12-28
				vo.setEndDate(DateUtil.offsetDay(this.getNextStartDate(vo.getStartDate()), -1));
				vo.setName(this.getOrderName(setId, record.getStr("title")));
				vo.setRemark(record.getStr("remark"));
				// 创建首个订单
				ProtectOrderVo order = this.protectOrderService.createUniqueOrder(customerId, setId, vo);
				kv.set("orderId", order.getOrder_id());
				// 支付首个订单
				this.protectOrderService.payOrder(customerId, order.getOrder_id(), order.getOrder_no(), order.getActual_price());
				
				// 成功后将下个周期开始日记为下次扣费日
				this.setNextDeductDate(customerId, setId, DateUtil.offsetDay(vo.getEndDate(), 1));
				// 关联事务所
				this.GeneralUserService.setOrSkipJudicialAndLawGroup(customerId);
				
			} catch (Exception e) {
				if( !(e instanceof BaseRuntimeException) ) {
					e.printStackTrace();
				}
				// 失败删除记录
				if(kv.getStr("protectionId") != null) {
					Db.update("update estate_protection set `status`=? where id=?", Constant.STATUS_INVALID, kv.getStr("protectionId"));
				}
				if(kv.getStr("orderId") != null) {
					Db.update("update estate_protection_order set `status`=? where order_id=?", Constant.STATUS_INVALID, kv.getStr("orderId"));
				}
				// 业务异常直接抛出
				if(e instanceof BaseRuntimeException) {
					throw (BaseRuntimeException)e;
				}
				throw GeneralRuntimeException.build("general.user.estate.protection.join.error.save.fail");
			}
		}
	}
	
	@Override
	public String getOrderName(String setId, String title) {
		// 2021-02-23
		// 保护计划记录不包含物件名，保护计划订单单独加上，订单名可区分
		String orderName = title;
		List<Record> estateList = Db.find("select * from estate_info where set_id=? and `status`=? ", setId, Constant.STATUS_VALID);
		if(estateList != null) {
			StringBuffer estateNames = new StringBuffer();
			for (Record r : estateList) {
				String estateName = StringUtils.trimToEmpty(r.getStr("addr")) + StringUtils.trimToEmpty(r.getStr("addr2")) + StringUtils.trimToEmpty(r.getStr("house_id"));
				// 去掉重复的物件名
				if(orderName != null && orderName.contains(estateName)) {
					continue;
				}
				if(estateNames.indexOf(estateName) >= 0) {
					continue;
				}
				
				if(estateNames.length() > 0) {
					estateNames.append(" · ");
				}
				estateNames.append(estateName);
			}
			if(estateNames.length() > 0) {
				orderName = (StringUtils.left(StringUtils.trimToEmpty(orderName) + " - " + estateNames.toString(), 200));
			}
		}
		return orderName;
	}
	
	@Override
	public void setNextDeductDate(String customerId, String setId, Date nextDeductDate) {
		Record record = Db.findFirst("select * from estate_protection where customer_id = ? and set_id=? and `status`=?", customerId, setId, Constant.STATUS_VALID);
		Date date = record.getDate("next_deduct_date");
		if(date == null || date.before(nextDeductDate)) {
			record.set("next_deduct_date", nextDeductDate);
		}
		Db.update("estate_protection", "id", record);
	}
	
	@Transactional
	@Override
	public void terminate(String customerId, String setId, String remark) {
		Record record = Db.findFirst("select * from estate_protection where set_id=? and customer_id = ? and `status`=?", setId, customerId, Constant.STATUS_VALID);
		if(record == null) {
			// 2021-02-23
			// 只能解除自己加入的，共享的不可操作
			Record rr = Db.findFirst("select ei.* from (select * from estate_info where set_id=? and `status`=?) ei LEFT JOIN estate_user eu ON eu.estate_id = ei.estate_id "
					+ "AND eu.`status` = ? WHERE eu.user_general_id = ? ", setId, Constant.STATUS_VALID, Constant.STATUS_VALID, customerId);
			// 顾客在set_id组合中
			if(rr != null) {
				throw GeneralRuntimeException.build("judicial.auth.not.permission");
			}
			throw GeneralRuntimeException.build("general.user.estate.protection.unbind.error.not.bind");
		}
		String id = record.getStr("id");
		EstateProtection entity = this.estateProtectionMapper.selectByPrimaryKey(id);
		// 立马生效，设置终止日，清空下个扣费日
		entity.setQuitDate(new Date());
		entity.setNextDeductDate(null);
		entity.setStatus(Constant.STATUS_INVALID);
		entity.setUpdatedBy(customerId);
		entity.setUpdateTime(new Date());
		// 追加备注
		if(StringUtils.isNotBlank(remark)) {
			entity.setRemark((StringUtils.isBlank(entity.getRemark()) ? "" : (entity.getRemark() + "\r\n")) + (DateUtil.formatDateTime(entity.getUpdateTime()) + " " + remark));
		}
		this.protectOrderService.updateByTerminate(customerId, setId, entity.getQuitDate(), remark);
		if(this.estateProtectionMapper.updateByPrimaryKey(entity) <= 0) {
			throw GeneralRuntimeException.build("general.user.estate.protection.unbind.error.save.fail");
		}
	}
	

	@Override
	public Kv getConfigOrderInfo() {
		return Kv.by("title", SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_PROTECTION_ORDER_DEFAULT_TITLE, "-"))
				.set("remark", SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_PROTECTION_ORDER_DEFAULT_REMARK, "-"))
				.set("money", SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_PROTECTION_ORDER_DEFAULT_AMOUNT, 1000L));
	}
	
	@Override
	public Date getNextStartDate(Date startDate) {
		// 系统配置有的话，取配置的，默认1年
		String period = SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_PROTECT_ORDER_PERIOD);
		int offset = 1;
		DateField dateField = DateField.YEAR;
		if(StringUtils.isNotBlank(period)) {
			period = period.trim().toLowerCase();
			try {
				offset = Integer.valueOf(period.replace("month", "").replace("year", "").replace("day", "").replace("week", "").trim());
				if(period.endsWith("month")) {
					dateField = DateField.MONTH;
				} else if(period.endsWith("week")) {
					dateField = DateField.WEEK_OF_YEAR;
				} else if(period.endsWith("day")) {
					dateField = DateField.DAY_OF_YEAR;
				} else {
					
				}
			} catch (Exception e) {
				log.error("sys_config[key=" + ConfigKeys.ESTATE_PROTECT_ORDER_PERIOD + "] value is invalid.");
			}
			
		}
		return DateUtil.offset(startDate, dateField, offset);
	}
	
	private Object getLockInstance(String customerId) {
		Object obj = CUS_LOCK_CACHE.getIfPresent(customerId);
		if(obj == null) {
			synchronized (this) {
				obj = CUS_LOCK_CACHE.getIfPresent(customerId);
				if(obj == null) {
					obj = new Object();
					CUS_LOCK_CACHE.put(customerId, obj);
				}
			}
		}
		return obj;
	}
	
}
