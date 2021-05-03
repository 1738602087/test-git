/**
 * 
 */
package cn.repeatlink.framework.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.service.IScheduleJobService;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-08-24 16:20
 * @TODO 定时任务管理器
 */

@Log4j2
@Component
public class JobManager {
	
	@Autowired
	private IScheduleJobService scheduleJobService;
	
	/**
	 * 每隔5分钟刷新任务设定
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void startJobs() {
		JobManager.log.info("refresh job from database is started.");
		this.scheduleJobService.initJobs();
		JobManager.log.info("refresh job from database is completed.");
	}

}
