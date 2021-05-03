package cn.repeatlink.framework.bean;

import cn.repeatlink.framework.common.Constant.UserType;

public class LoginUserGeneral extends LoginUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String user_id;
	
	private String salt;
	
	/**
	 * @param username
	 * @param password
	 */
	public LoginUserGeneral(String username, String password) {
		super(username, password);
		super.setUserType(UserType.GENERAL.typeValue());
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
