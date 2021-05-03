/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.Date;

import cn.repeatlink.module.judicial.vo.order.ProtectOrderVo;
import cn.repeatlink.module.judicial.vo.order.ProtectPlanVo;

/**
 * @author LAI
 * @date 2020-10-29 10:37
 */
public interface IProtectOrderService {

	/**
	 * 根据给定的保护日期和价格创建订单，不检验保护日期重复性
	 * @param userId
	 * @param vo
	 * @return
	 */
	ProtectOrderVo createOrder(String userId, String setId, ProtectPlanVo vo);

	/**
	 * 根据给定的保护日期和价格创建订单，会检验保护日期重复性
	 * @param userId
	 * @param vo
	 * @return
	 */
	ProtectOrderVo createUniqueOrder(String userId, String setId, ProtectPlanVo vo);

	/**
	 * @param operUserId
	 * @param orderId
	 * @param orderno
	 * @param money
	 */
	void payOrder(String operUserId, String orderId, String orderno, Long money);

	/**
	 * @param customerId
	 * @param terminateDate
	 * @param terminateRemark
	 */
	void updateByTerminate(String customerId, String setId, Date terminateDate, String terminateRemark);

}
