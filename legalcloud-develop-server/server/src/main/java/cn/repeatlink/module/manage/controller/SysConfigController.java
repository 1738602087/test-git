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
import cn.repeatlink.module.manage.dto.ConfigInfo;
import cn.repeatlink.module.manage.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/config")
@Api(value = "配置管理", produces = "application/json", tags = "配置管理接口")
public class SysConfigController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysConfigService sysConfigService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.config.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "配置檢索", notes = "根據條件查詢配置", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.configId", "searchConditon.status", "searchConditon.configValue",
			"searchConditon.remark"})
	public AjaxResult<RLPage<ConfigInfo>> getAll(@RequestBody PageDomain<ConfigInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("configName", "config_name")
				.put("configKey", "config_key")
				.put("configType", "config_type")
				.put("configValue", "config_value")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<ConfigInfo> datas = sysConfigService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<ConfigInfo>) datas));
	}
	
	@PostMapping
	@ApiOperation(value = "添加配置", notes = "添加配置")
	@ApiOperationSupport(ignoreParameters = { "roleId", "status", "roleKey", "roleSort", "updateTime", "createTime" })
	public AjaxResult<Object> add(@RequestBody ConfigInfo config) {
		sysConfigService.addConfig(config, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@GetMapping("/{configId}")
	@ApiOperation(value = "获取配置信息", notes = "根据配置ID获取配置信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Integer", paramType = "path", name = "configId", value = "配置Id")
	public AjaxResult<ConfigInfo> configDetail(@PathVariable("configId") Integer configId) {
		ConfigInfo configInfo = sysConfigService.findById(configId);
		return AjaxResult.success(configInfo);
	}
	
	@PutMapping
	@ApiOperation(value = "修改配置", notes = "修改配置")
	@ApiOperationSupport(ignoreParameters = { "roleKey", "roleSort", "updateTime", "createTime" })
	public AjaxResult<Object> update(@RequestBody ConfigInfo info) {
		sysConfigService.updateConfig(info, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
}
