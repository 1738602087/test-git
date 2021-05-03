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
import cn.repeatlink.module.manage.dto.RoleInfo;
import cn.repeatlink.module.manage.dto.RoleSimpleInfo;
import cn.repeatlink.module.manage.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/role")
@Api(value = "角色管理", produces = "application/json", tags = "角色管理接口")
public class SysRoleController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysRoleService sysRoleService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.role.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "角色檢索", notes = "根據條件查詢角色", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.roleId", "searchConditon.status", "searchConditon.updateTime",
			"searchConditon.createTime", "searchConditon.roleSort"})
	public AjaxResult<RLPage<RoleInfo>> getAll(@RequestBody PageDomain<RoleInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("roleName", "role_name")
				.put("dataScope", "data_scope")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<RoleInfo> datas = sysRoleService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<RoleInfo>) datas));
	}
	
	@PostMapping
	@ApiOperation(value = "添加角色", notes = "添加角色")
	@ApiOperationSupport(ignoreParameters = { "roleId", "status", "roleKey", "roleSort", "updateTime", "createTime" })
	public AjaxResult<Object> add(@RequestBody RoleInfo role) {
		sysRoleService.addRole(role, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@PutMapping
	@ApiOperation(value = "修改角色", notes = "修改角色")
	@ApiOperationSupport(ignoreParameters = { "roleKey", "roleSort", "updateTime", "createTime" })
	public AjaxResult<Object> update(@RequestBody RoleInfo role) {
		sysRoleService.updateRole(role, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@GetMapping("/{roleId}")
	@ApiOperation(value = "获取角色信息", notes = "根据角色ID获取角色信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "roleId", value = "角色Id")
	public AjaxResult<RoleInfo> roleDetail(@PathVariable("roleId") Long roleId) {
		RoleInfo roleInfo = sysRoleService.findById(roleId);
		return AjaxResult.success(roleInfo);
	}
	
	@GetMapping("/all/simple_list")
	@ApiOperation(value = "获取所有角色简易信息", notes = "获取所有角色简易信息", produces = "application/json")
	public AjaxResult<List<RoleSimpleInfo>> getAllSimpleList() {
		return AjaxResult.success(this.sysRoleService.selectAllSimpleList());
	}
	
}
