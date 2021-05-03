/**
 * 
 */
package cn.repeatlink.framework.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.module.judicial.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-10 14:04
 */

@RestController("fwauthcontroller")
@RequestMapping("/auth")
@Api(value="认证", tags = "认证")
@ApiSort(1)
public class AuthController extends BaseController {
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="登录",notes = "获取token凭证")
	@PostMapping("/login")
	public AjaxResult<Object> login(@RequestBody LoginVo vo){
		return AjaxResult.success(null);
	}

	@ApiOperationSupport(order = 2)
	@ApiOperation(value="安全登出",notes = "安全登出")
	@PostMapping("/logout")
	public AjaxResult<Object> logout(){
		return AjaxResult.success(null);
	}
	
}
