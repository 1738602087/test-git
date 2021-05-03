/**
 * 
 */
package cn.repeatlink.framework.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.repeatlink.common.entity.SysJobLog;
import cn.repeatlink.common.mapper.SysJobLogMapper;
import cn.repeatlink.framework.common.Constant.JobLogStatus;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.manage.dto.JobInfo;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-08-24 15:58
 * @TODO 任务执行者。每一个任务分派一个执行者，独立记录执行日志
 */

@Log4j2
@Component
@Scope("prototype")
public final class JobExecutor {
	
	@Autowired
	private SysJobLogMapper sysJobLogMapper;
	
	private Job job;
	
	private JobInfo jobInfo;
	
	public final void run() {
		SysJobLog log = new SysJobLog();
		log.setJobId(jobInfo.getJobId());
		log.setCreateTime(new Date());
		log.setInvokeTarget(jobInfo.getInvokeTarget());
		log.setJobName(jobInfo.getJobName());
		log.setJobGroup(jobInfo.getJobGroup());
		log.setStatus(JobLogStatus.EXEC);
		this.sysJobLogMapper.insert(log);
		JobExecutor.log.info("Job({} - {}) started.", jobInfo.getJobId(), jobInfo.getJobName());
		try {
			log.setJobMessage(this.job.exec());
			log.setStatus(JobLogStatus.SUCCESS);
			JobExecutor.log.info("Job({} - {}) execute success.", jobInfo.getJobId(), jobInfo.getJobName());
		} catch (Exception e) {
			// 记录异常
			JobExecutor.log.error("Job({" + jobInfo.getJobId() + "} - {" + jobInfo.getJobName() + "}) execute error.", e);
			e.printStackTrace();
			log.setStatus(JobLogStatus.FAIL);
			if(e instanceof BaseRuntimeException) {
				log.setExceptionInfo(this.getErrorMessage((BaseRuntimeException)e));
			}
			else {
				log.setExceptionInfo(e.getMessage());
			}
		} finally {
			// 更新日志
			Date endTime = new Date();
			log.setEndTime(endTime);
			this.sysJobLogMapper.updateByPrimaryKey(log);
			JobExecutor.log.info("Job({} - {}) execute completed.", jobInfo.getJobId(), jobInfo.getJobName());
		}
	}
	
	public final void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
	
	public final void setJob(Job job) {
		this.job = job;
	}
	
	private final String getErrorMessage(BaseRuntimeException e) {
		BaseResultCode errocode = e.getErrocode();
		if(errocode == null) {
			return e.getMessage();
		}
		return "[" + errocode.getCode() + "]" + MessageUtil.getMessage(errocode.getFieldname(), e.getParams());
	}
}
