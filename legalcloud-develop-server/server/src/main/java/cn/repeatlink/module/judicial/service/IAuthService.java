/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import cn.repeatlink.module.judicial.vo.RegGroupVo;
import cn.repeatlink.module.judicial.vo.RegVo;
import cn.repeatlink.module.judicial.vo.RevocerPwdVo;
import cn.repeatlink.module.judicial.vo.UserBaseInfoVo;

/**
 * @author LAI
 * @date 2020-09-17 09:57
 */
public interface IAuthService {

	/**
	 * @param regCode
	 * @return
	 */
	RegGroupVo getRegCodeInfo(String regCode, String email);

	/**
	 * @param vo
	 */
	void regJudicial(RegVo vo);

	/**
	 * @param operaUserId
	 * @param vo
	 */
	void resetPwd(String operaUserId, RevocerPwdVo vo);

	/**
	 * @param operaUserId
	 * @param vo
	 */
	void resetPwd(String userId, UserBaseInfoVo vo);

}
