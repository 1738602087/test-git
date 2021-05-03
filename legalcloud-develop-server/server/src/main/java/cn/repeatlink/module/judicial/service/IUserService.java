/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import cn.repeatlink.module.judicial.vo.UserInfo;

/**
 * @author LAI
 * @date 2020-09-24 13:51
 */
public interface IUserService {

	/**
	 * @param userId
	 * @param password
	 */
	void updatePwd(String userId, String password);

	/**
	 * @param password
	 */
	void checkPassword(String password);

	/**
	 * @param userId
	 * @param fullname
	 * @param fullnameKana
	 */
	void updateName(String userId, String famname, String famnameKana, String givename, String givenameKana);

	/**
	 * @param userId
	 * @return
	 */
	UserInfo getInfo(String userId);

	/**
	 * @param userId
	 * @param base64data
	 */
	void setAvatar(String userId, String base64data);

}
