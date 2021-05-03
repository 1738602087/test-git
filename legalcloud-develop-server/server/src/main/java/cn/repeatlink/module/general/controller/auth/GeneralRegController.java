/**
 * 
 */
package cn.repeatlink.module.general.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.auth.RegVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-27 14:34
 */

@RLPermission(noCheck = true)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/reg")
@Api(value="注册", tags = "认证")
@ApiSort(2)
public class GeneralRegController extends BaseGeneralController {
	
	@Autowired
	private IGeneralUserService generalUserService;
	
	@ApiOperationSupport(order = 1, includeParameters = { "vo.target", "vo.verify_code" })
	@ApiOperation(value="校验验证码",notes = "校验验证码")
	@PostMapping("/checkcode")
	public AjaxResult<Object> checkcode(@RequestBody RegVo vo){
		this.generalUserService.checkcode(vo.getTarget(), vo.getVerify_code());
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.login_id"})
	@ApiOperation(value="注册帐号",notes = "注册帐号")
	@PostMapping
	public synchronized AjaxResult<Object> reg(@RequestBody RegVo vo){
		this.generalUserService.regUser(vo);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 3, ignoreParameters = {"vo.login_id", "vo.email"})
	@ApiOperation(value="找回密码",notes = "找回密码")
	@PostMapping("/pwd/recover")
	public AjaxResult<Object> recoverpwd(@RequestBody RegVo vo){
		this.generalUserService.checkcode(vo.getTarget(), vo.getVerify_code());
		this.generalUserService.resetPwd(vo.getTarget(), vo.getPassword());
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 50, ignoreParameters = {"vo.login_id", "vo.password", "vo.verify_code", "vo.email"})
	@ApiOperation(value="检查帐号注册情况",notes = "检查帐号注册情况")
	@PostMapping("/checkaccount")
	public synchronized AjaxResult<Object> regCheckemail(@RequestBody RegVo vo){
		this.generalUserService.checkRegTarget(vo.getTarget());
		return AjaxResult.success(null);
	}

}
