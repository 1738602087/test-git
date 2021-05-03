package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysConfig;
import cn.repeatlink.module.manage.dto.ConfigInfo;

public interface ISysConfigService {

	
	List<SysConfig> findAll();
	
	/**
	 * 根据条件查询配置
	 * @param userInfo
	 * @return
	 */
	List<ConfigInfo> search(ConfigInfo configInfo);

	/**
	 * 添加配置
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	ConfigInfo addConfig(ConfigInfo configInfo, Long operUserId);

	/**
	 * 根据ID查找配置
	 * @param roleId
	 * @return
	 */
	ConfigInfo findById(Integer configId);

	/**
	 * 修改配置
	 * @param configInfo
	 * @param operUserId
	 * @return
	 */
	ConfigInfo updateConfig(ConfigInfo configInfo, Long operUserId);
	
	
	
}
