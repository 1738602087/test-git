/**
 * 
 */
package cn.repeatlink.module.general.util;

import cn.hutool.core.util.ReUtil;

/**
 * @author LAI
 * @date 2020-10-23 09:32
 */
public class PasswordUtil {
	
	public static boolean isValid(String password) {
		return ReUtil.isMatch("[0-9a-zA-Z]{6,16}", password);
	}

}
