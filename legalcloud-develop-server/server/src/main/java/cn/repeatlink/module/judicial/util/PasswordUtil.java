/**
 * 
 */
package cn.repeatlink.module.judicial.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * @author LAI
 * @date 2020-09-17 14:44
 */
public class PasswordUtil {
	
	public static String buildSalt() {
		return IdUtil.simpleUUID();
	}
	
	public static String encrypt(String password, String salt) {
		return SecureUtil.md5(password + salt);
	}

	public static void main(String[] args) {
		String salt = buildSalt();
		String password = "123456";
		System.out.println(password);
		System.out.println(salt);
		System.out.println(encrypt(password, salt));
	}
}
