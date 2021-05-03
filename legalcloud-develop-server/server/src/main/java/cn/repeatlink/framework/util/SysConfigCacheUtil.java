/**
 * 
 */
package cn.repeatlink.framework.util;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.common.Constant;
import cn.repeatlink.module.judicial.common.Define;

/**
 * @author LAI
 * @date 2020-11-04 10:15
 */
public class SysConfigCacheUtil extends SysConfigUtil {
	
	private static SysConfigCacheUtil instance = new SysConfigCacheUtil();
	
	public static SysConfigCacheUtil instance() {
		return instance;
	}

	@Override
	protected String getConfigValue(String configKey) {
		Record record = Db.findFirstByCache(Define.CACHE_NAME_SYS_CONFIG, configKey, "select config_value from sys_config where config_key=? and `status`=? limit 1", configKey, Constant.STATUS_VALID);
		return record == null ? null : record.getStr("config_value");
	}
	
	

}
