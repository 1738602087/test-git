/**
 * 
 */
package cn.repeatlink.module.law.service;

import java.util.List;

import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.fee.FeeItemVo;
import cn.repeatlink.module.law.vo.fee.QuitFeeItemVo;
import cn.repeatlink.module.law.vo.fee.UseFeeItemVo;

/**
 * @author LAI
 * @date 2020-10-27 13:13
 */
public interface ICustomerFeeService {

	/**
	 * @param userInfo
	 * @param vo
	 * @return
	 */
	List<UseFeeItemVo> getUsingFeeUserList(LawLoginUserInfo userInfo, UseFeeItemVo vo);

	/**
	 * @param userInfo
	 * @param vo
	 * @return
	 */
	List<QuitFeeItemVo> getQuitFeeUserList(LawLoginUserInfo userInfo, QuitFeeItemVo vo);

	/**
	 * @param userInfo
	 * @param userId
	 * @return
	 */
	List<FeeItemVo> getCustomerFeeHistoryList(LawLoginUserInfo userInfo, String userId);

}
