/**
 * 
 */
package cn.repeatlink.module.judicial.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.EstateProtectionOrder;
import cn.repeatlink.common.mapper.EstateProtectionOrderMapper;
import cn.repeatlink.framework.scheduler.Job;
import cn.repeatlink.module.judicial.common.Constant.ProtectOrderPayTag;
import cn.repeatlink.module.judicial.mapper.ProtectOrderMapper;
import cn.repeatlink.module.judicial.service.IProtectOrderService;
import cn.repeatlink.module.judicial.service.IProtectService;
import cn.repeatlink.module.judicial.vo.order.ProtectOrderVo;
import cn.repeatlink.module.judicial.vo.order.ProtectPlanVo;
import lombok.extern.log4j.Log4j2;

/**
 * 保护计划定时任务<br>
 * 处理合同终止和续期<br>
 * 支付计划订单
 * @author LAI
 * @date 2020-12-29 14:25
 */

@Log4j2
@Component
public class ProtectJob implements Job {
	
	@Autowired
	private ProtectOrderMapper protectOrderMapper;
	
	@Autowired
	private EstateProtectionOrderMapper estateProtectionOrderMapper;
	
	@Autowired
	private IProtectService protectService;
	
	@Autowired
	private IProtectOrderService protectOrderService;
	
	private static boolean started = false;

	@Override
	public String exec() {
		if(started) {
			throw new RuntimeException("job is started.");
		}
		synchronized (ProtectJob.class) {
			if(started) {
				throw new RuntimeException("job is started.");
			}
			started = true;
		}
		
		try {
			// 获取所有有效保护计划记录
			List<Record> protectList = this.getAllValidProtectCustomerList();
			if(protectList == null || protectList.isEmpty()) {
				return "protect list is empty.";
			}
			Date nowtime = new Date();
			for (Record protect : protectList) {
				if(protect == null) continue;
				String customerId = protect.getStr("customer_id");
				String setId = protect.getStr("set_id");
				try {
					Date quitDate = protect.getDate("quit_date");
					// 情况A: 终止, 终止日不为空且小于当前
					if(quitDate != null && quitDate.before(nowtime)) {
						this.handleQuit(customerId, setId);
						continue;
					}
					Date nextDeductDate = protect.getDate("next_deduct_date");
					// 情况B: 续费
					if(nextDeductDate == null) {
						nextDeductDate = protect.getDate("start_date");
						protect.set("next_deduct_date", nextDeductDate);
						Db.update("estate_protection", "id", protect);
					}
					if(nextDeductDate != null) {
						this.handleNext(customerId, setId);
						continue;
					}
				} catch (Exception e) {
					log.error("protect handle error, customer_id = " + customerId + ", setId = " + setId, e);
				}
			}
			
			return "all protect work is completed.";
		
		} catch (Exception e) {
			throw e;
		} finally {
			started = false;
		}
	}
	
	
	private void handleQuit(String customerId, String setId) {
		// 合同状态无效处理
		Record record = Db.findFirst("select * from estate_protection where `status`=? and customer_id = ? and set_id=? ", Constant.STATUS_VALID, customerId, setId);
		record.set("status", Constant.STATUS_INVALID);
		record.set("update_time", new Date());
		Db.update("estate_protection", "id", record);
		// 既有保护订单处理 TODO
		
	}
	
	private void handleNext(String customerId, String setId) {
		// 先获取顾客既有订单
		List<EstateProtectionOrder> orderList = this.protectOrderMapper.findCustomerOrderList(customerId, setId, Constant.STATUS_VALID);
		Record protect = Db.findFirst("select * from estate_protection where `status`=? and customer_id = ? and set_id=? ", Constant.STATUS_VALID, customerId, setId);
		Date nextDeductDate = protect.getDate("next_deduct_date");
		boolean exist = false;
		for (EstateProtectionOrder order : orderList) {
			if(order.getStartDate().compareTo(nextDeductDate) >= 0) {
				exist = true;
				// 情况A: 存在但未支付
				// 处理:  支付
				if(!ProtectOrderPayTag.PAYED.equals(order.getPayTag())) {
					this.payOrder(protect, order.getOrderId(), order.getOrderNo(), order.getActualPrice());
				}
				break;
			}
		}
		if(exist) return;
		
		Date startDate = nextDeductDate;
		nextDeductDate = this.protectService.getNextStartDate(startDate);
		Date endDate = DateUtil.offsetDay(nextDeductDate, -1);
		
		ProtectPlanVo vo = new ProtectPlanVo();
		vo.setPrice(protect.getLong("money"));
		vo.setActualPrice(vo.getPrice());
		// 开始日2020-12-29
		vo.setStartDate(startDate);
		// 结束日2021-12-28
		vo.setEndDate(endDate);
		// 2021-02-23
		// 统一订单名
		vo.setName(this.protectService.getOrderName(setId, protect.getStr("title")));
		vo.setRemark(protect.getStr("remark"));
		// 创建下个订单
		ProtectOrderVo order = this.protectOrderService.createUniqueOrder(customerId, setId, vo);
		// 支付
		this.payOrder(protect, order.getOrder_id(), order.getOrder_no(), order.getActual_price());
	}
	
	private void payOrder(Record protect, String orderId, String orderNo, Long price) {
		// 是否立即支付订单 TODO
		EstateProtectionOrder order = this.estateProtectionOrderMapper.selectByPrimaryKey(orderId);
		try {
			order.setPayTimes(order.getPayTimes() == null ? 1 : (order.getPayTimes() + 1));
			this.estateProtectionOrderMapper.updateByPrimaryKey(order);
			this.protectOrderService.payOrder(protect.getStr("customer_id"), orderId, orderNo, price);
		} catch (Exception e) {
			log.error("customer_id[" + protect.getStr("customer_id") + "], order_id[" + orderId + "] pay fail, error_msg=" + e.getMessage());
			// 失败3次，解约
			if(order.getPayTimes() != null && order.getPayTimes() >= 3) {
				try {
					this.protectService.terminate(order.getCustomerId(), order.getSetId(), "3回の失敗した决済、自動キャンセル");
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error("customer_id[" + protect.getStr("customer_id") + "], setId[" + order.getSetId() + "], the count of aborted deduction is more than 3, and terminate plan take error");
				}
			}
			return;
		}
		this.protectService.setNextDeductDate(protect.getStr("customer_id"), order.getSetId(), DateUtil.offsetDay(order.getEndDate(), 1));
	}
	
	private List<Record> getAllValidProtectCustomerList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from estate_protection where set_id is not null and `status`=? and (next_deduct_date is  null or next_deduct_date <= ? or quit_date <= ?) group by customer_id, set_id");
		List<Object> paras = new ArrayList<>();
		paras.add(Constant.STATUS_VALID);
		String date = DateUtil.format(new Date(), "yyyy-MM-dd");
		paras.add(date);
		paras.add(date);
		return Db.find(sql.toString(), paras.toArray());
	}

}
