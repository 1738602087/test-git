package cn.repeatlink.module.manage.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.DeptInfo;
import cn.repeatlink.module.manage.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dept")
@Api(value = "部门管理", produces = "application/json", tags = "部门管理接口")
public class SysDeptController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysDeptService sysDeptService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.dept.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "部门檢索", notes = "根據條件查詢部门", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.deptId", "searchConditon.ancestors",
			"searchConditon.phone", "searchConditon.email", "searchConditon.delFlag"})
	public AjaxResult<RLPage<DeptInfo>> getAll(@RequestBody PageDomain<DeptInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("deptName", "dept_name")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<DeptInfo> datas = sysDeptService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<DeptInfo>) datas));
	}
	
	@PostMapping
	@ApiOperation(value = "添加部门", notes = "添加部门")
	@ApiOperationSupport(ignoreParameters = { "deptId", "status", "ancestors", "delFlag" })
	public AjaxResult<Object> add(@RequestBody DeptInfo dept) {
		sysDeptService.addDept(dept, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@GetMapping("/{deptId}")
	@ApiOperation(value = "获取部门信息", notes = "根据部门ID获取部门信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "deptId", value = "部门ID")
	public AjaxResult<DeptInfo> deptDetail(@PathVariable("deptId") Long deptId) {
		DeptInfo deptInfo = sysDeptService.findById(deptId);
		return AjaxResult.success(deptInfo);
	}
	
}
