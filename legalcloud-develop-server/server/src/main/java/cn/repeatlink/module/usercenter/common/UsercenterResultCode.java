package cn.repeatlink.module.usercenter.common;

import cn.repeatlink.framework.exception.BaseResultCode;

/**
 * @TODO 用户中心相关错误码
 * @author LAI
 * @date 2020-08-25 13:54
 */
public class UsercenterResultCode extends BaseResultCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsercenterResultCode(String code,String fieldname) {
		super(code,fieldname);
	}
	
	public static UsercenterResultCode USER_PASSWORD_INVALID =new UsercenterResultCode("[UC001]USER_PASSWORD_INVALID","usercenter.user.password.invalid");
	public static UsercenterResultCode USER_PASSWORDC_NOT =new UsercenterResultCode("[UC002]USER_PASSWORDC_NOT","usercenter.user.passwordc.not");
	public static UsercenterResultCode USER_PASSWORD_ERROR =new UsercenterResultCode("[UC003]USER_PASSWORD_ERROR","usercenter.user.password.error");
	
}