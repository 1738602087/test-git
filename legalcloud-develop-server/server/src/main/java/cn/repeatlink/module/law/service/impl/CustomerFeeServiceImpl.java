/**
 * 
 */
package cn.repeatlink.module.law.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.framework.common.Constant;
import cn.repeatlink.module.law.mapper.CustomerFeeMapper;
import cn.repeatlink.module.law.service.ICustomerFeeService;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.fee.FeeItemVo;
import cn.repeatlink.module.law.vo.fee.QuitFeeItemVo;
import cn.repeatlink.module.law.vo.fee.UseFeeItemVo;

/**
 * @author LAI
 * @date 2020-10-27 13:13
 */

@Service
public class CustomerFeeServiceImpl implements ICustomerFeeService {
	
	@Autowired
	private CustomerFeeMapper customerFeeMapper;
	
	@Override
	public List<UseFeeItemVo> getUsingFeeUserList(LawLoginUserInfo userInfo, UseFeeItemVo vo) {
		List<UseFeeItemVo> list = this.customerFeeMapper.getUsingFeeUserList(Constant.UserType.LAW.equals(userInfo.getUserType()) ? userInfo.getGroup_id(): null, vo != null ? vo.getFullname() : null);
		return list;
	}

	@Override
	public List<QuitFeeItemVo> getQuitFeeUserList(LawLoginUserInfo userInfo, QuitFeeItemVo vo) {
		List<QuitFeeItemVo> list = this.customerFeeMapper.getQuitFeeUserList(Constant.UserType.LAW.equals(userInfo.getUserType()) ? userInfo.getGroup_id(): null, vo != null ? vo.getFullname() : null);
		return list;
	}

	@Override
	public List<FeeItemVo> getCustomerFeeHistoryList(LawLoginUserInfo userInfo, String userId) {
		List<FeeItemVo> list = this.customerFeeMapper.getCustomerFeeHistoryList(userId);
		return list;
	}
	
}
