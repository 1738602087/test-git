package cn.repeatlink.module.manage.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import cn.hutool.core.map.MapBuilder;
import cn.repeatlink.common.bean.DataTableHeader;
import cn.repeatlink.common.service.IDatatableHeaderService;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.JobInfo;
import cn.repeatlink.module.manage.dto.JobLogInfo;
import cn.repeatlink.module.manage.service.ISysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/job")
@Api(value = "任务管理", produces = "application/json", tags = "任务管理接口")
public class SysJobController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysJobService sysJobService;

	@GetMapping("/header")
	@ApiOperation(value = "获取任务列表表头", notes = "获取任务列表表头", produces = "application/json")
	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.job.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "任务檢索", notes = "根據條件查詢任务", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.jobId", "searchConditon.cronExpression",
			"searchConditon.invokeTarget", "searchConditon.remark", "searchConditon.misfirePolicy", "searchConditon.concurrent"})
	public AjaxResult<RLPage<JobInfo>> getAll(@RequestBody PageDomain<JobInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("jobName", "job_name")
				.put("jobGroup", "job_group")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<JobInfo> datas = sysJobService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<JobInfo>) datas));
	}
	
	@PostMapping
	@ApiOperation(value = "添加任务", notes = "添加任务")
	@ApiOperationSupport(ignoreParameters = { "jobId", "status", "updateTime", "createTime" })
	public AjaxResult<Object> add(@RequestBody JobInfo job) {
		sysJobService.addJob(job, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@PutMapping
	@ApiOperation(value = "修改任务", notes = "修改任务")
	@ApiOperationSupport(ignoreParameters = { "updateTime", "createTime" })
	public AjaxResult<Object> update(@RequestBody JobInfo job) {
		sysJobService.updateJob(job, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@GetMapping("/{jobId}")
	@ApiOperation(value = "获取任务信息", notes = "根据任务ID获取任务信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "jobId", value = "任务Id")
	public AjaxResult<JobInfo> jobDetail(@PathVariable("jobId") Long jobId) {
		JobInfo info = sysJobService.findById(jobId);
		return AjaxResult.success(info);
	}
	
	@GetMapping("/log/header")
	@ApiOperation(value = "获取任务日志列表表头", notes = "获取任务日志列表表头", produces = "application/json")
	public AjaxResult<DataTableHeader> getLogHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.joblog.table");
		return AjaxResult.success(header);

	}
	@PostMapping("/{jobId}/log")
	@ApiOperation(value = "获取任务日志分页数据", notes = "根据任务ID获取任务执行日志信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "jobId", value = "任务Id")
	public AjaxResult<RLPage<JobLogInfo>> jobLogPage(@PathVariable("jobId") Long jobId, @RequestBody PageDomain<Object> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<JobLogInfo> datas = sysJobService.getJobLogListByJobId(jobId);
		return AjaxResult.success(new RLPage<>((Page<JobLogInfo>) datas));
	}
}
