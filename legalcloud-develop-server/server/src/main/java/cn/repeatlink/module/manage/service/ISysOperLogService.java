package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysOperLog;
import cn.repeatlink.module.manage.dto.OperLogInfo;

public interface ISysOperLogService {

	
	List<SysOperLog> findAll();
	
	/**
	 * 根据条件查询操作日志
	 * @param userInfo
	 * @return
	 */
	List<OperLogInfo> search(OperLogInfo operLogInfo);


	/**
	 * 根据ID查找操作日志
	 * @param roleId
	 * @return
	 */
	OperLogInfo findById(Long operLogId);
	
	
	
}
