/**
 * 
 */
package cn.repeatlink.module.law.service;

import java.util.List;

import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.user.JudicialUserApplyVo;
import cn.repeatlink.module.law.vo.user.JudicialUserVo;
import cn.repeatlink.module.law.vo.user.ReqJudicialUserUpdateVo;

/**
 * @author LAI
 * @date 2020-10-14 14:59
 */
public interface IJudicialManageService {

	/**
	 * @param vo
	 * @return
	 */
	List<JudicialUserVo> getNormalUserList(String groupId, JudicialUserVo vo);

	/**
	 * @param vo
	 * @return
	 */
	List<JudicialUserVo> getInvalidUserList(String groupId, JudicialUserVo vo);

	/**
	 * @param vo
	 * @return
	 */
	List<JudicialUserApplyVo> getApplyUserList(String groupId, JudicialUserApplyVo vo);

	/**
	 * @param operaUser
	 * @param userId
	 */
	void invalidUser(LawLoginUserInfo operaUser, String userId);

	/**
	 * @param operaUser
	 * @param userId
	 */
	void reviveUser(LawLoginUserInfo operaUser, String userId);

	/**
	 * @param operaUser
	 * @param email
	 */
	void newUser(LawLoginUserInfo operaUser, String email);

	/**
	 * @param operaUser
	 * @param email
	 */
	void sendMailAgain(LawLoginUserInfo operaUser, String email);

	/**
	 * @param operaUser
	 * @param vo
	 */
	void updateUser(LawLoginUserInfo operaUser, String userId, ReqJudicialUserUpdateVo vo);

}
