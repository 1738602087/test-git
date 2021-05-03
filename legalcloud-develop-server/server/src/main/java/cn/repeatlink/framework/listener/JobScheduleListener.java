/**
 * 
 */
package cn.repeatlink.framework.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.service.IScheduleJobService;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-08-25 09:41
 * @TODO 监听应用启动成功后，启动定时任务
 */

@Log4j2
@Component
public class JobScheduleListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private IScheduleJobService scheduleJobService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("start register job schedule from database.");
		this.scheduleJobService.initJobs();
		log.info("completed register job schedule from database.");
	}

}
