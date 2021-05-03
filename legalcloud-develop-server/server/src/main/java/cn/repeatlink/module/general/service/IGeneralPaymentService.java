/**
 * 
 */
package cn.repeatlink.module.general.service;

import java.util.List;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import cn.repeatlink.module.general.vo.payment.CreditCardTokenVo;
import cn.repeatlink.module.general.vo.payment.CreditCardVo;
import cn.repeatlink.module.general.vo.payment.FeeDetailVo;
import cn.repeatlink.module.general.vo.payment.FeeItemVo;

/**
 * @author LAI
 * @date 2020-10-23 15:17
 */
public interface IGeneralPaymentService {

	/**
	 * @param userId
	 * @return
	 */
	List<CreditCardVo> getCreditCardListByUserId(String userId);

	/**
	 * @param userId
	 * @param vo
	 */
	void bindCreditCard(String userId, CreditCardTokenVo vo);

	/**
	 * @param userId
	 * @param cardId
	 */
	void unbindCreditCard(String userId, String cardId);

	/**
	 * @param userId
	 * @param vo
	 * @return
	 */
	PageLoadMoreVo<FeeDetailVo, FeeItemVo> getFeeHistoryList(String userId, PageLoadMoreVo<FeeDetailVo, FeeItemVo> vo);

	/**
	 * @param userId
	 * @param feeId
	 * @return
	 */
	FeeDetailVo getFeeDetail(String userId, String feeId);

	/**
	 * @param userId
	 * @return
	 */
	List<AutoDeductionVo> getAutoDeductionInfo(String userId);

	/**
	 * @param userId
	 */
	void terminateAutoDeduction(String userId, String setId);

	/**
	 * @param userId
	 */
	void joinAutoDeduction(String userId, String setId);

	/**
	 * @param userId
	 * @param cardId
	 */
	void selectCreditCard(String userId, String cardId);

}
