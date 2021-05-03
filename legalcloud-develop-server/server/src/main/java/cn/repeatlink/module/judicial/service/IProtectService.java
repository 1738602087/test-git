/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;

/**
 * @author LAI
 * @date 2020-12-29 11:22
 */
public interface IProtectService {

	/**
	 * 加入保护计划
	 * @param customerId
	 */
	void join(String customerId, String estateId);

	/**
	 * 获取保护计划信息
	 * @param customerId
	 * @return
	 */
	List<AutoDeductionVo> getProtectInfo(String customerId);

	/**
	 * 终止保护计划（下次扣费日生效）
	 * @param userId
	 */
	void terminate(String customerId, String setId, String remark);

	/**
	 * 获取下一周期开始日期（下一次扣费日期）
	 * @param startDate
	 * @return
	 */
	Date getNextStartDate(Date startDate);

	/**
	 * @return
	 */
	Kv getConfigOrderInfo();

	/**
	 * @param customerId
	 * @param nextDeductDate
	 */
	void setNextDeductDate(String customerId, String estateId, Date nextDeductDate);

	/**
	 * @param setId
	 * @param title
	 * @return
	 */
	String getOrderName(String setId, String title);

}
