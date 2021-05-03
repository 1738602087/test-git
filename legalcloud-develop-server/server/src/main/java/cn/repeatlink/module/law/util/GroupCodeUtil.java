/**
 * 
 */
package cn.repeatlink.module.law.util;

import cn.hutool.core.util.RandomUtil;

/**
 * @author LAI
 * @date 2020-10-15 11:17
 */
public class GroupCodeUtil {
	
	public final static String BASE_STR = RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR_NUMBER.toUpperCase();
	
	public static String build() {
		String code = null;
		code = RandomUtil.randomNumbers(8);
		return code;
	}

	public static void main(String[] args) {
		System.out.println(build());
	}
}
