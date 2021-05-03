package cn.repeatlink.module.manage.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import cn.repeatlink.module.manage.dto.OperLogInfo;
import cn.repeatlink.module.manage.service.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/operlog")
@Api(value = "操作日志管理", produces = "application/json", tags = "操作日志管理接口")
public class SysOperLogController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysOperLogService sysOperLogService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.operlog.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "操作日志檢索", notes = "根據條件查詢操作日志", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.id", "searchConditon.operId", "searchConditon.operIp",
			"searchConditon.phone", "searchConditon.jsonResult", "searchConditon.cost"})
	public AjaxResult<RLPage<OperLogInfo>> getAll(@RequestBody PageDomain<OperLogInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("operTime", "oper_time")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<OperLogInfo> datas = sysOperLogService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<OperLogInfo>) datas));
	}
	
//	@GetMapping("/{operLogId}")
//	@ApiOperation(value = "获取操作日志信息", notes = "根据操作日志ID获取操作日志信息", produces = "application/json")
//	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "operLogId", value = "操作日志ID")
//	public AjaxResult<OperLogInfo> operLogDetail(@PathVariable("operLogId") Long operLogId) {
//		OperLogInfo operLogInfo = sysOperLogService.findById(operLogId);
//		return AjaxResult.success(operLogInfo);
//	}
	
}
