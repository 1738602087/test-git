/**
 * 
 */
package cn.repeatlink.module.general.common;

/**
 * @author LAI
 * @date 2020-09-27 14:33
 */
public class Constant {
	
	public static interface ValidateCodeServiceType {
		String EMAIL = "email";
		String SMS_ZH = "sms_zh";
		String SMS_JP = "sms_jp";
	}

	public static interface GeneralMarriage {
		/** 0：未婚 */
		Short NOT = 0;
		/** 1：既婚 */
		Short MARRIED = 1;
		/** 2：離婚 */
		Short DIVORCE = 2;
	
	}
}
