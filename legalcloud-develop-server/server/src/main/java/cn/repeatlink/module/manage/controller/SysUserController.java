package cn.repeatlink.module.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.repeatlink.common.bean.DataTableHeader;
import cn.repeatlink.common.bean.HeaderVo;
import cn.repeatlink.common.service.IDatatableHeaderService;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysRoleService;
import cn.repeatlink.module.manage.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(value = "用户管理", produces = "application/json", tags = "用户管理接口")
public class SysUserController extends BaseSysController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private IDatatableHeaderService datatableHeaderService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.user.table");
		return AjaxResult.success(header);

	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "获取用户信息", notes = "根据用户ID获取用户信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "userId", value = "用户Id")
	public AjaxResult<UserInfo> userDetail(@PathVariable("userId") Long userId) {
		UserInfo userInfo = sysUserService.findById(userId);
		return AjaxResult.success(userInfo);
	}

	@PostMapping("/search")
	@ApiOperation(value = "用戶檢索", notes = "根據條件查詢用户", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.id", "searchConditon.status", "searchConditon.password",
			"searchConditon.confirmPassword", "searchConditon.remark" })
	public AjaxResult<RLPage<UserInfo>> getAll(@RequestBody PageDomain<UserInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>()).put("userName", "user_name").build());
		List<UserInfo> datas = sysUserService.search(pageDomain.getSearchConditon());
//		PageInfo<UserInfo> pageInfo=new PageInfo<>(datas);
		return AjaxResult.success(new RLPage<>((Page<UserInfo>) datas));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户", notes = "删除指定用户")
	@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "id", value = "用户Id")
	public AjaxResult<Object> deleteUser(@PathVariable("id") Long userId) {
		try {
			sysUserService.invalidUser(userId, SecurityUtils.getLoginUser().getUserId());
			return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
		} catch (BaseRuntimeException e) {
			return AjaxResult.failed(null, e);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.failed(ManagerResultCode.USER_USER_OPERATION_FAIL);
		}

	}

	@PostMapping("/{id}/enable/{enable}")
	@ApiOperation(value = "修改用户状态", notes = "修改用户状态")
	@ApiImplicitParams({
			@ApiImplicitParam(required = true, dataType = "Long", paramType = "path", name = "id", value = "用户Id"),
			@ApiImplicitParam(required = true, dataType = "Short", paramType = "path", name = "enable", value = "修改后用户状态，0：不可用，1：可用")

	})
	public AjaxResult<Object> updateUserEnable(@PathVariable("id") Long userId, @PathVariable("enable") short enable) {
		try {
			sysUserService.updateUserEnable(userId, enable, SecurityUtils.getLoginUser().getUserId());
			return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
		} catch (Exception e) {
			return AjaxResult.failed(ManagerResultCode.USER_USER_OPERATION_FAIL);
		}

	}

	@PostMapping
	@ApiOperation(value = "添加用户", notes = "添加用户", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "id", "enable", "status", "confirmPassword" })
	public AjaxResult<Object> add(@RequestBody UserInfo user) {
		try {
			sysUserService.addUser(user, SecurityUtils.getLoginUser().getUserId());
		} catch (BaseRuntimeException e) {
			return AjaxResult.failed(e.getErrocode());
		}
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}

	@PutMapping
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiOperationSupport(ignoreParameters = { "password", "confirmPassword" })
	public AjaxResult<Object> edit(@RequestBody UserInfo user) {
		try {
			sysUserService.updateUser(user, SecurityUtils.getLoginUser().getUserId());
		} catch (BaseRuntimeException e) {
			return AjaxResult.failed(e.getErrocode());
		}
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@PostMapping("/export")
	@ApiOperation(value = "导出Excel", notes = "导出全部用户Excel文件", produces = "application/octet-stream")
	public void export(HttpServletResponse response) throws Exception {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.user.table");
		List<UserInfo> datas = sysUserService.search(new UserInfo());
		List<HeaderVo> file_header = header.getFile_header();
		ExcelWriter bigWriter = ExcelUtil.getBigWriter();
		
		for (HeaderVo headerVo : file_header) {
			bigWriter = bigWriter.addHeaderAlias(headerVo.getDataIndex(), headerVo.getTitle());
		}
		bigWriter.setOnlyAlias(true);
		bigWriter.write(datas);
//		for (UserInfo vo : datas) {
//			bigWriter.writeRow(vo);
//		}
		String fileName = "全部用户_"+DateUtil.formatDate(new Date())+"导出.xlsx";
		super.prepareDownloadFile(response, fileName);
		bigWriter.flush(response.getOutputStream());
	}


}
