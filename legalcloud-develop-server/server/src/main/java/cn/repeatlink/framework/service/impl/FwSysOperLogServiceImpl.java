package cn.repeatlink.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.repeatlink.common.entity.SysOperLog;
import cn.repeatlink.common.mapper.SysOperLogMapper;
import cn.repeatlink.framework.service.IFwSysOperLogService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FwSysOperLogServiceImpl implements IFwSysOperLogService {
	
	@Autowired
	private SysOperLogMapper sysOperLogMapper;
	@Override
	public void inserLog(SysOperLog sysOperLog) {
		sysOperLogMapper.insert(sysOperLog);
	}

}
