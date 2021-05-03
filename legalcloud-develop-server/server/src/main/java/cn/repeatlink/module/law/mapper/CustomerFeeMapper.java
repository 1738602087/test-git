/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import java.util.List;

import cn.repeatlink.module.law.vo.fee.FeeItemVo;
import cn.repeatlink.module.law.vo.fee.QuitFeeItemVo;
import cn.repeatlink.module.law.vo.fee.UseFeeItemVo;

/**
 * @author LAI
 * @date 2020-10-27 13:26
 */
public interface CustomerFeeMapper {
	
	List<UseFeeItemVo> getUsingFeeUserList(String groupId, String keyword);
	
	List<QuitFeeItemVo> getQuitFeeUserList(String groupId, String keyword);
	
	List<FeeItemVo> getCustomerFeeHistoryList(String userId);

}
