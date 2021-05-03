/**
 * 
 */
package cn.repeatlink.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.framework.service.IFwService;
import cn.repeatlink.module.manage.dto.MenuInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-08-25 15:58
 */

@RestController
@RequestMapping("/framework")
@Api(value="系统", tags = "系统")
public class FwController extends BaseController {
	
	@Autowired
	private IFwService fwService;
	
	@ApiOperation(value="获取登录账号菜单信息",notes = "获取登录账号菜单信息")
	@GetMapping("/menus")
	public AjaxResult<List<MenuInfo>> info(){
		return AjaxResult.success(this.fwService.getMenuListByUserId(super.loginUserId()));
	}

}
