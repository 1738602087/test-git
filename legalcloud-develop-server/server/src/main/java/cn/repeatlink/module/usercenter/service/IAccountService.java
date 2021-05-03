/**
 * 
 */
package cn.repeatlink.module.usercenter.service;

import cn.repeatlink.module.usercenter.vo.AccountInfo;
import cn.repeatlink.module.usercenter.vo.UpdatePwdVo;

/**
 * @author LAI
 * @date 2020-08-20 15:25
 */
public interface IAccountService {

	/**
	 * 修改账号密码
	 * @param userId
	 * @param vo
	 */
	void updatePwd(Long userId, UpdatePwdVo vo);

	/**
	 * @param userId
	 * @param info
	 */
	void updateAccount(Long userId, AccountInfo info);

	/**
	 * 变更登录名
	 * @param userId
	 * @param userName
	 */
	void changeUserName(Long userId, String userName);

}
