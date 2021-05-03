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
import cn.repeatlink.module.manage.dto.DictDataInfo;
import cn.repeatlink.module.manage.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dictdata")
@Api(value = "字典管理", produces = "application/json", tags = "字典管理接口")
public class SysDictDataController extends BaseSysController {

	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	@Autowired
	private ISysDictDataService sysDictDataService;

	@GetMapping("/header")
	@ApiOperation(value = "获取列表表头", notes = "获取列表表头", produces = "application/json")

	public AjaxResult<DataTableHeader> getHeader() {
		DataTableHeader header = datatableHeaderService.getDataTableHeader(null, "manager.dict.table");
		return AjaxResult.success(header);

	}

	@PostMapping("/search")
	@ApiOperation(value = "字典檢索", notes = "根據條件查詢字典", produces = "application/json")
	@ApiOperationSupport(ignoreParameters = { "searchConditon.dictCode", "searchConditon.cssClass", "searchConditon.dictValue",
			"searchConditon.remark"})
	public AjaxResult<RLPage<DictDataInfo>> getAll(@RequestBody PageDomain<DictDataInfo> pageDomain) {
		RLPageHelper.startPage(pageDomain, MapBuilder.create(new HashMap<String, String>())
				.put("dictName", "dict_name")
				.put("dictKey", "dict_key")
				.put("dictType", "dict_type")
				.put("dictValue", "dict_value")
				.put("createTime", "create_time")
				.put("updateTime", "update_time").build());
		List<DictDataInfo> datas = sysDictDataService.search(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<DictDataInfo>) datas));
	}
	
	@PostMapping
	@ApiOperation(value = "添加字典", notes = "添加字典")
	@ApiOperationSupport(ignoreParameters = { "dictCode", "status", "roleKey", "roleSort", "updateTime", "createTime" })
	public AjaxResult<Object> add(@RequestBody DictDataInfo dict) {
		sysDictDataService.addDictData(dict, SecurityUtils.getLoginUser().getUserId());
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@GetMapping("/{dictCode}")
	@ApiOperation(value = "获取字典信息", notes = "根据字典CODE获取字典信息", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "Integer", paramType = "path", name = "dictCode", value = "字典CODE")
	public AjaxResult<DictDataInfo> dictDetail(@PathVariable("dictCode") Long dictCode) {
		DictDataInfo dictInfo = sysDictDataService.findByCode(dictCode);
		return AjaxResult.success(dictInfo);
	}
	
}
