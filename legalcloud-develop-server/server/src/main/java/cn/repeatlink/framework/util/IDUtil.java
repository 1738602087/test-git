/**
 * 
 */
package cn.repeatlink.framework.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author LAI
 * @date 2020-09-17 14:21
 */
public class IDUtil {
	
	public static String nextID() {
		Snowflake snowflake = IdUtil.getSnowflake(1L, 1L);
		return snowflake.nextIdStr();
	}

	public static void main(String[] args) {
		Snowflake snowflake = IdUtil.getSnowflake(1L, 1L);
		for (int i = 0; i < 10; i++) {
			String str = snowflake.nextIdStr();
			System.out.println(str + "  " + str.length());
		}
	}
}
