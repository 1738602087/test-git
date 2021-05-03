/**
 * 
 */
package cn.repeatlink.framework.util;

import com.jfinal.plugin.activerecord.Db;

import cn.repeatlink.common.Constant;

/**
 * @author LAI
 * @date 2020-11-04 10:07
 */
public class SysConfigUtil {
	
	private static SysConfigUtil instance = new SysConfigUtil();
	
	public static SysConfigUtil instance() {
		return instance;
	}
	
	protected String getConfigValue(String configKey) {
		String str = Db.queryStr("select config_value from sys_config where config_key=? and `status`=? limit 1", configKey, Constant.STATUS_VALID);
		return str;
	}

	public String getValue(String configKey) {
		return getConfigValue(configKey);
	}
	
	public String getValue(String configKey, String defaultValue) {
		String value = getConfigValue(configKey);
		return value == null ? defaultValue : value;
	}
	
	public Short getValue(String configKey, Short defaultValue) {
		String value = getConfigValue(configKey);
		return value == null ? defaultValue : Short.valueOf(value);
	}
	
	public Long getValue(String configKey, Long defaultValue) {
		String value = getConfigValue(configKey);
		return value == null ? defaultValue : Long.valueOf(value);
	}
	
	public Integer getValue(String configKey, Integer defaultValue) {
		String value = getConfigValue(configKey);
		return value == null ? defaultValue : Integer.valueOf(value);
	}
	
	public Boolean getValue(String configKey, Boolean defaultValue) {
		String value = getConfigValue(configKey);
		return value == null ? defaultValue : Boolean.valueOf(value);
	}
	
}
