/**
 * 
 */
package cn.repeatlink.framework.service;

import cn.repeatlink.module.manage.dto.JobInfo;

/**
 * @author LAI
 * @date 2020-08-24 13:52
 */
public interface IScheduleJobService {

	/**
	 * 
	 */
	void initJobs();

	/**
	 * @param job
	 * @throws Exception
	 */
	void createJob(JobInfo job) throws Exception;

	/**
	 * @param jobId
	 * @throws Exception
	 */
	void deleteJob(Long jobId) throws Exception;

}
