package cn.repeatlink.framework.util;

import tk.mybatis.mapper.util.StringUtil;

public class CommonUtil {
	
	public static final String EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~]|(?:\"[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~()<>\\[\\]:;,@.\\ (\\\")(\\\\)]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~])|(?:\"[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~()<>\\[\\]:;,@.\\ (\\\")(\\\\)]*\"))+)*)@[A-Za-z0-9]+([-.][A-Za-z0-9]+)*";
	public static boolean  validateEmail(String email) {
		if(StringUtil.isEmpty(email)) {
			return false;
		}
		return email.matches(EMAIL_REGEX);
	}
	/**
	 * 校验电话号码
	 * @param phonenum
	 * @return
	 */
	public static boolean  validatePhoneNum(String phonenum) {
		if(StringUtil.isEmpty(phonenum)) {
			return false;
		}
		return phonenum.matches("^\\d{10,12}$");
		
	}
}
