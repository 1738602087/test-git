/**
 * 
 */
package cn.repeatlink.module.judicial.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.controller.BaseJudicialController;
import cn.repeatlink.module.judicial.service.IAuthService;
import cn.repeatlink.module.judicial.vo.LoginVo;
import cn.repeatlink.module.judicial.vo.RegGroupVo;
import cn.repeatlink.module.judicial.vo.RegVo;
import cn.repeatlink.module.judicial.vo.RevocerPwdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-10 14:04
 */

@RLPermission(noCheck = true)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/auth")
@Api(value="认证", tags = "认证")
@ApiSort(1)
public class AuthController extends BaseJudicialController {
	
	@Autowired
	private IAuthService authService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="登录",notes = "获取token凭证")
	@PostMapping("/login")
	public AjaxResult<Object> login(@RequestBody LoginVo vo){
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.login_id"})
	@ApiOperation(value="注册",notes = "注册账号")
	@PostMapping("/reg")
	public synchronized AjaxResult<Object> reg(@RequestBody RegVo vo){
		this.authService.regJudicial(vo);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 3)
	@ApiOperation(value="找回密码",notes = "找回密码")
	@PostMapping("/pwd/recover")
	public AjaxResult<Object> recoverPwd(@RequestBody RevocerPwdVo vo){
		this.authService.resetPwd(null, vo);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 4, includeParameters = {"vo.email", "vo.reg_code"})
	@ApiOperation(value="验证邀请码",notes = "获取邀请码具体信息")
	@PostMapping("/reg_code")
	public AjaxResult<RegGroupVo> regCode(@RequestBody RegVo vo){
		return AjaxResult.success(this.authService.getRegCodeInfo(vo.getReg_code(), vo.getEmail()));
	}

}
