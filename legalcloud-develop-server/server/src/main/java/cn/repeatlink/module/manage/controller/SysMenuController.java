package cn.repeatlink.module.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.manage.dto.MenuInfo;
import cn.repeatlink.module.manage.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/menu")
@Api(value = "菜单管理", produces = "application/json", tags = "角色管理接口")
public class SysMenuController extends BaseSysController {

	@Autowired
	private ISysMenuService sysMenuService;

	@GetMapping
	@ApiOperation(value = "获取菜单列表（分层）", produces = "application/json")
	public AjaxResult<List<MenuInfo>> getAll() {
		List<MenuInfo> datas = sysMenuService.getAllMenuLayerList();
		return AjaxResult.success(datas);
	}
	
}
