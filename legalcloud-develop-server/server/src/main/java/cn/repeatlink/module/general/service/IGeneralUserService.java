/**
 * 
 */
package cn.repeatlink.module.general.service;

import cn.repeatlink.module.general.vo.auth.RegVo;
import cn.repeatlink.module.general.vo.user.UserAttributeInfo;
import cn.repeatlink.module.general.vo.user.UserAvatarVo;
import cn.repeatlink.module.general.vo.user.UserInfo;
import cn.repeatlink.module.general.vo.user.UserPwdVo;

/**
 * @author LAI
 * @date 2020-10-22 16:24
 */
public interface IGeneralUserService {

	/**
	 * @param userId
	 * @return
	 */
	UserInfo getUserInfo(String userId);

	/**
	 * @param operUserId
	 * @param userId
	 * @param info
	 */
	void updateUserInfo(String operUserId, String userId, UserInfo info);

	/**
	 *
	 * @param userId
	 * @param info
	 */
	void updateAttributeInfo(String userId, UserAttributeInfo info);

	/**
	 * @param userId
	 * @param vo
	 */
	void updateAvatar(String userId, UserAvatarVo vo);

	/**
	 * @param userId
	 * @param vo
	 */
	void updatePwd(String userId, UserPwdVo vo);

	/**
	 * @param vo
	 */
	void regUser(RegVo vo);

	/**
	 * @param target
	 * @param verifyCode
	 */
	void checkcode(String target, String verifyCode);

	/**
	 * @param target
	 * @param password
	 */
	void resetPwd(String target, String password);

	/**
	 *
	 * @param userId
	 * @return
	 */
	UserAttributeInfo getAttributeInfo(String userId);

	/**
	 * @param userId
	 */
	void setOrSkipJudicialAndLawGroup(String userId);

	/**
	 * @param userId
	 * @param judicialUserId
	 */
	void setOrSkipJudicialAndLawGroup(String userId, String judicialUserId);

	/**
	 * @param target
	 */
	void checkRegTarget(String target);
}
