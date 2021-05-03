package cn.repeatlink.module.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysJob;
import cn.repeatlink.common.entity.SysJobLog;
import cn.repeatlink.common.mapper.SysJobLogMapper;
import cn.repeatlink.common.mapper.SysJobMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.service.IScheduleJobService;
import cn.repeatlink.module.manage.common.Constant.JobGroup;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.JobInfo;
import cn.repeatlink.module.manage.dto.JobLogInfo;
import cn.repeatlink.module.manage.service.ISysJobService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysJobServiceImpl implements ISysJobService {

	@Autowired
	private SysJobMapper sysJobMapper;
	
	@Autowired
	private SysJobLogMapper sysJobLogMapper;
	
	@Autowired
	private IScheduleJobService scheduleJobService;
	
	@Override
	public List<SysJob> findAll() {
		return sysJobMapper.selectAll();
	}


	@Override
	public List<JobInfo> search(JobInfo jobInfo) {
		List<JobInfo> infos = this.sysJobMapper.search(jobInfo);
		if(infos!=null) { 
			
		}
		return infos;
	}
	
	@Override
	public JobInfo addJob(JobInfo jobInfo, Long operUserId) {
		validateJobInfo(jobInfo);

		SysJob job = new SysJob();
		job.setJobName(jobInfo.getJobName());
		if(StringUtils.isBlank(jobInfo.getJobGroup())) {
			job.setJobGroup(JobGroup.DEFAULT);
		} else {
			job.setJobGroup(jobInfo.getJobGroup());
		}
		job.setInvokeTarget(jobInfo.getInvokeTarget());
		job.setConcurrent(jobInfo.getConcurrent());
		job.setCronExpression(jobInfo.getCronExpression());
		job.setMisfirePolicy(jobInfo.getMisfirePolicy());
		job.setRemark(jobInfo.getRemark());
		job.setStatus(jobInfo.getStatus());
		job.setCreateBy(operUserId);
		job.setCreateTime(new Date());
		sysJobMapper.insert(job);

		jobInfo.setJobId(job.getJobId());
		
		// 注册任务
		this.registerJob(jobInfo);
		return jobInfo;
	}
	
	@Override
	public JobInfo updateJob(JobInfo jobInfo, Long operUserId) {
		validateJobInfo(jobInfo);

		SysJob job = this.sysJobMapper.selectByPrimaryKey(jobInfo.getJobId());
		job.setJobName(jobInfo.getJobName());
		if(StringUtils.isBlank(jobInfo.getJobGroup())) {
			job.setJobGroup(JobGroup.DEFAULT);
		} else {
			job.setJobGroup(jobInfo.getJobGroup());
		}
		job.setInvokeTarget(jobInfo.getInvokeTarget());
		job.setConcurrent(jobInfo.getConcurrent());
		job.setCronExpression(jobInfo.getCronExpression());
		job.setMisfirePolicy(jobInfo.getMisfirePolicy());
		job.setRemark(jobInfo.getRemark());
		job.setStatus(jobInfo.getStatus());
		job.setUpdateBy(operUserId);
		job.setUpdateTime(new Date());
		sysJobMapper.updateByPrimaryKey(job);
		// 注册任务
		this.registerJob(jobInfo);
		return jobInfo;
	}
	
	@Override
	public JobInfo findById(Long jobId) {
		SysJob job = sysJobMapper.selectByJobId(jobId);
		if (job == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NOT_FOUND);
		}
		JobInfo result = new JobInfo();
		result.setJobId(job.getJobId());
		result.setJobName(job.getJobName());
		result.setJobGroup(job.getJobGroup());
		result.setConcurrent(job.getConcurrent());
		result.setStatus(job.getStatus());
		result.setCronExpression(job.getCronExpression());
		result.setInvokeTarget(job.getInvokeTarget());
		result.setMisfirePolicy(job.getMisfirePolicy());
		result.setRemark(job.getRemark());
		return result;
	}
	
	@Override
	public List<JobLogInfo> getJobLogListByJobId(Long jobId) {
		List<SysJobLog> list = this.sysJobLogMapper.selectAllByJobId(jobId);
		List<JobLogInfo> infoList = new ArrayList<>();
		if(list != null) {
			final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			for (SysJobLog log : list) {
				JobLogInfo info = new JobLogInfo();
				info.setCreateTime(DateUtil.format(log.getCreateTime(), DATE_FORMAT));
				info.setEndTime(DateUtil.format(log.getEndTime(), DATE_FORMAT));
				info.setExceptionInfo(log.getExceptionInfo());
				info.setJobMessage(log.getJobMessage());
				info.setInvokeTarget(log.getInvokeTarget());
				info.setStatus(log.getStatus());
				info.setJobGroup(log.getJobGroup());
				info.setJobName(log.getJobName());
				infoList.add(info);
			}
		}
		// 返回分页数据
		Page page = (Page)list;
		page.clear();
		page.addAll(infoList);
		return page;
	}
	
	
	/**
	 * 校验job 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateJobInfo(JobInfo jobInfo) {
		if (jobInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NULL);
		}

		if (StringUtils.isEmpty(jobInfo.getJobName())) {
			throw new BaseRuntimeException(ManagerResultCode.JOB_NAME_NULL);
		}
		if (jobInfo.getJobName().length() < 2 || jobInfo.getJobName().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.JOB_NAME_INVALID);
		}
		if (StringUtils.isEmpty(jobInfo.getInvokeTarget())) {
			throw new BaseRuntimeException(ManagerResultCode.JOB_INVOKE_TARGET_INVALID);
		}
		if (StringUtils.isEmpty(jobInfo.getCronExpression())) {
			throw new BaseRuntimeException(ManagerResultCode.JOB_CRON_INVALID);
		}
		if(jobInfo.getStatus() == null) {
			jobInfo.setStatus(Constant.STATUS_VALID);
		}
		// 校验cron表达式
		try {
			CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
		} catch (Exception e) {
			throw new BaseRuntimeException(ManagerResultCode.JOB_CRON_INVALID);
		}
	}
	
	public void registerJob(JobInfo jobInfo) {
		if(!Constant.STATUS_VALID.equals(jobInfo.getStatus())) {
			try {
				this.scheduleJobService.createJob(jobInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseRuntimeException(ManagerResultCode.JOB_REGISTER_FAIL);
			}
		} else {
			try {
				this.scheduleJobService.deleteJob(jobInfo.getJobId());
			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseRuntimeException(ManagerResultCode.JOB_LOGOUT_FAIL);
			}
		}
	}

}
