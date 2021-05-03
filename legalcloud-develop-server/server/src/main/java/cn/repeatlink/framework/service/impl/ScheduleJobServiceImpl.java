/**
 * 
 */
package cn.repeatlink.framework.service.impl;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import cn.repeatlink.common.Constant;
import cn.repeatlink.common.mapper.SysJobMapper;
import cn.repeatlink.framework.scheduler.Job;
import cn.repeatlink.framework.scheduler.JobExecutor;
import cn.repeatlink.framework.service.IScheduleJobService;
import cn.repeatlink.framework.util.SpringBeanFactory;
import cn.repeatlink.module.manage.common.Constant.JobConcurrent;
import cn.repeatlink.module.manage.common.Constant.JobMisfirePolicy;
import cn.repeatlink.module.manage.dto.JobInfo;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-08-24 13:52
 */

@Log4j2
@Service
public class ScheduleJobServiceImpl implements IScheduleJobService {
	
	/**
	 * 定时任务工厂类
	 */
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private SysJobMapper sysJobMapper;
	
	/**
	 * 从DB初始化任务
	 */
	@Override
	public synchronized void initJobs() {
		List<JobInfo> list = this.sysJobMapper.search(new JobInfo());
		if(list == null) return;
		for (JobInfo jobInfo : list) {
			if(!Constant.STATUS_VALID.equals(jobInfo.getStatus())) {
				try {
					this.deleteJob(jobInfo.getJobId());
				} catch (Exception e) {
					log.error("job({}) delete fail. error message is {}", jobInfo.getJobId(), e.getMessage());
				}
				continue;
			}
			try {
				this.createJob(jobInfo);
			} catch (Exception e) {
				log.error("job({}) register fail. error message is {}", jobInfo.getJobId(), e.getMessage());
			}
		}
	}
	
	/**
	 * 根据job信息创建可执行任务
	 * @param jobInfo
	 */
	@Override
	public void createJob(JobInfo job) throws Exception {

		// 获取调度器
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();

		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId().toString());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (null == trigger) {
			// 不存在，创建一个
			MethodInvokingJobDetailFactoryBean methodInvJobDetailFB = new MethodInvokingJobDetailFactoryBean();
			methodInvJobDetailFB.setName(job.getJobName());
			// methodInvJobDetailFB.setTargetObject(SpringContextUtil.getApplicationContext().getBean(job.getClassname()));SpringUtil.getApplicationContext().getBeansWithAnnotation(Service.class);
			Object target = this.getBean(job.getInvokeTarget());
			if(!(target instanceof Job)) {
				log.error("JobId({}) target not a Job object. Please check it.", job.getJobId());
				throw new RuntimeException("target not a <Job> object");
			}
			JobExecutor jobExecutor = SpringBeanFactory.getBean(JobExecutor.class);
			jobExecutor.setJobInfo(job);
			jobExecutor.setJob((Job)target);
			methodInvJobDetailFB.setTargetObject(jobExecutor);
			methodInvJobDetailFB.setTargetMethod("run");
			methodInvJobDetailFB.afterPropertiesSet();
			methodInvJobDetailFB.setConcurrent(JobConcurrent.ALLOW.equals(job.getConcurrent()));
			JobDetail jobDetail = (JobDetail) methodInvJobDetailFB.getObject();// 动态
			jobDetail.getJobDataMap().put("jobInfo", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 设置misfire
			scheduleBuilder = this.setJobMisfire(scheduleBuilder, job.getMisfirePolicy());
			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId().toString())
					.withSchedule(scheduleBuilder).build();
			// 加载定时任务
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			// 定时设置没有变化，则不作更新
			if(trigger.getCronExpression().equals(job.getCronExpression())) {
				return;
			}
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 设置misfire
			scheduleBuilder = this.setJobMisfire(scheduleBuilder, job.getMisfirePolicy());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	
	}
	
	/**
	 * 暂停任务
	 * @param jobId
	 */
	public void pauseJob(Long jobId) throws Exception {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobId.toString());
		scheduler.pauseJob(jobKey);
	}
	
	/**
	 * 恢复任务
	 * @param jobId
	 */
	public void resumeJob(Long jobId) throws Exception {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobId.toString());
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 移除任务
	 * @param jobId
	 */
	@Override
	public void deleteJob(Long jobId) throws Exception {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobId.toString());
		scheduler.deleteJob(jobKey);
	}

	private Object getBean(String invokeTarget) {
		Object bean = null;
		try {
			bean = SpringBeanFactory.getBean(invokeTarget);
		} catch (Exception e) { }
		if(bean == null) {
			try {
				bean = SpringBeanFactory.getBean(Class.forName(invokeTarget));
			} catch (Exception e) { }
		}
		if(bean == null) {
			throw new RuntimeException("target bean not found. please check it.");
		}
		return bean;
	}
	
	private CronScheduleBuilder setJobMisfire(CronScheduleBuilder scheduleBuilder, String misfirePolicy) {
		if(JobMisfirePolicy.NOW.equals(misfirePolicy)) {
			return scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
		}
		if(JobMisfirePolicy.DO_NOTHING.equals(misfirePolicy)) {
			return scheduleBuilder.withMisfireHandlingInstructionDoNothing();
		}
		if(JobMisfirePolicy.ONE_TIME.equals(misfirePolicy)) {
			return scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
		}
		return scheduleBuilder;
	}
}
