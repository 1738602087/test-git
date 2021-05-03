package cn.repeatlink.module.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.entity.SysOperLog;
import cn.repeatlink.common.mapper.SysOperLogMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.OperLogInfo;
import cn.repeatlink.module.manage.service.ISysOperLogService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysOperLogServiceImpl implements ISysOperLogService {

	@Autowired
	private SysOperLogMapper sysOperLogMapper;
	
	@Override
	public List<SysOperLog> findAll() {
		return sysOperLogMapper.selectAll();
	}


	@Override
	public List<OperLogInfo> search(OperLogInfo operLogInfo) {
		List<OperLogInfo> infos = this.sysOperLogMapper.search(operLogInfo);
		if(infos!=null) { 
			
		}
		return infos;
	}
	
	@Override
	public OperLogInfo findById(Long operLogId) {
		SysOperLog operLog = sysOperLogMapper.selectByPrimaryKey(operLogId.intValue());
		if (operLog == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NOT_FOUND);
		}
		OperLogInfo result = new OperLogInfo();
		result.setId(operLog.getId().longValue());
		result.setMethod(operLog.getMethod());
		result.setOperName(operLog.getOperName());
		result.setOperIp(operLog.getOperIp());
		result.setOperLocation(operLog.getOperLocation());
		result.setOperTime(DateUtil.format(operLog.getOperTime(), "yyyy-MM-dd HH:mm:ss"));
		result.setCost(operLog.getCost());
		result.setOperUrl(operLog.getOperUrl());
		result.setRequestMethod(operLog.getRequestMethod());
		result.setResponseCode(operLog.getResponseCode());
		result.setErrorMsg(operLog.getErrorMsg());
		return result;
	}
	
}
