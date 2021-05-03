package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysJob;
import cn.repeatlink.module.manage.dto.JobInfo;
import cn.repeatlink.module.manage.dto.JobLogInfo;

public interface ISysJobService {

	
	List<SysJob> findAll();
	
	/**
	 * 根据条件查询任务
	 * @param userInfo
	 * @return
	 */
	List<JobInfo> search(JobInfo jobInfo);

	/**
	 * 添加任务
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	JobInfo addJob(JobInfo jobInfo, Long operUserId);

	/**
	 * 根据ID查找任务信息
	 * @param roleId
	 * @return
	 */
	JobInfo findById(Long jobId);

	/**
	 * @param jobId
	 * @return
	 */
	List<JobLogInfo> getJobLogListByJobId(Long jobId);

	/**
	 * 修改任务信息
	 * @param jobInfo
	 * @param operUserId
	 * @return
	 */
	JobInfo updateJob(JobInfo jobInfo, Long operUserId);

	
}
