package cn.repeatlink.module.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.repeatlink.common.entity.SysConfig;
import cn.repeatlink.common.mapper.SysConfigMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.ConfigInfo;
import cn.repeatlink.module.manage.service.ISysConfigService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysConfigServiceImpl implements ISysConfigService {

	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	@Override
	public List<SysConfig> findAll() {
		return sysConfigMapper.selectAll();
	}


	@Override
	public List<ConfigInfo> search(ConfigInfo configInfo) {
		List<ConfigInfo> infos = this.sysConfigMapper.search(configInfo);
		if(infos!=null) { 
			
		}
		return infos;
	}
	
	@Override
	public ConfigInfo addConfig(ConfigInfo configInfo, Long operUserId) {
		validateConfigInfo(configInfo);
		
		List<SysConfig> list = this.sysConfigMapper.selectByConfigKey(configInfo.getConfigKey());
		if(list != null && list.size() > 0) {
			throw new BaseRuntimeException(ManagerResultCode.CONFIG_KEY_EXIST);
		}

		SysConfig config = new SysConfig();
		config.setConfigKey(configInfo.getConfigKey());
		config.setConfigValue(configInfo.getConfigValue());
		config.setConfigName(configInfo.getConfigName());
		config.setConfigType(configInfo.getConfigType());
		config.setStatus(configInfo.getStatus());
		config.setCreateBy(operUserId);
		config.setCreateTime(new Date());
		sysConfigMapper.insert(config);

		configInfo.setConfigId(config.getConfigId());
		return configInfo;
	}
	
	@Override
	public ConfigInfo updateConfig(ConfigInfo configInfo, Long operUserId) {
		validateConfigInfo(configInfo);
		
		// 修改了key的情况
		List<SysConfig> list = this.sysConfigMapper.selectByConfigKey(configInfo.getConfigKey());
		if(list != null && list.size() > 0) {
			if(!list.get(0).getConfigId().equals(configInfo.getConfigId())) {
				throw new BaseRuntimeException(ManagerResultCode.CONFIG_KEY_EXIST);
			}
		}

		SysConfig config = this.sysConfigMapper.selectByPrimaryKey(configInfo.getConfigId());
		config.setConfigKey(configInfo.getConfigKey());
		config.setConfigValue(configInfo.getConfigValue());
		config.setConfigName(configInfo.getConfigName());
		config.setConfigType(configInfo.getConfigType());
		config.setStatus(configInfo.getStatus());
		config.setUpdateBy(operUserId);
		config.setUpdateTime(new Date());
		sysConfigMapper.updateByPrimaryKey(config);

		return configInfo;
	}
	
	@Override
	public ConfigInfo findById(Integer configId) {
		SysConfig config = sysConfigMapper.selectByPrimaryKey(configId);
		if (config == null) {
			throw new BaseRuntimeException(ManagerResultCode.CONFIG_NOT_FOUND);
		}
		ConfigInfo result = new ConfigInfo();
		result.setConfigId(config.getConfigId());
		result.setConfigKey(config.getConfigKey());
		result.setConfigValue(config.getConfigValue());
		result.setStatus(config.getStatus());
		result.setConfigValue(config.getConfigValue());
		result.setConfigType(config.getConfigType());
		return result;
	}
	
	
	/**
	 * 校验config 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateConfigInfo(ConfigInfo configInfo) {
		if (configInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NULL);
		}

		if (StringUtils.isEmpty(configInfo.getConfigName())) {
			throw new BaseRuntimeException(ManagerResultCode.CONFIG_NAME_NULL);
		}
		if (configInfo.getConfigName().length() < 2 || configInfo.getConfigName().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.CONFIG_NAME_INVALID);
		}
		if (StringUtils.isEmpty(configInfo.getConfigKey())) {
			throw new BaseRuntimeException(ManagerResultCode.CONFIG_KEY_NULL);
		}

	}

}
