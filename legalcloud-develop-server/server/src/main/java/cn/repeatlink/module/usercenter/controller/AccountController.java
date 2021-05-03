package cn.repeatlink.module.usercenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysUserService;
import cn.repeatlink.module.usercenter.service.IAccountService;
import cn.repeatlink.module.usercenter.vo.AccountInfo;
import cn.repeatlink.module.usercenter.vo.UpdatePwdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/account")
@Api(value="账户管理", tags = "账号管理")
public class AccountController {
	
	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private IAccountService accountService;
	
	@ApiOperation(value="获取登录账号个人信息",notes = "获取登录账号个人信息")
	@GetMapping("/info")
	public AjaxResult<AccountInfo> info(){
		UserInfo userInfo = this.sysUserService.findById(SecurityUtils.getLoginUser().getUserId());
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setEmail(userInfo.getEmail());
		accountInfo.setGender(userInfo.getSex());
		accountInfo.setName(userInfo.getName());
		accountInfo.setPhonenumber(userInfo.getPhonenumber());
		if(userInfo.getUserType() != null) {
			accountInfo.setUser_type(userInfo.getUserType());
		}
		return AjaxResult.success(accountInfo);
	}
	
	@ApiOperation(value="更新账号信息",notes = "更新账号信息")
	@PutMapping("/info")
	public AjaxResult<Object> updateInfo(@RequestBody AccountInfo account){
		this.accountService.updateAccount(SecurityUtils.getLoginUser().getUserId(), account);
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
	@ApiOperation(value="修改登录密码",notes = "修改登录密码")
	@PutMapping("/pwd")
	public AjaxResult<Object> updatePwd(@RequestBody UpdatePwdVo updatePwdVo){
		// this.accountService.updatePwd(SecurityUtils.getLoginUser().getUserId(), updatePwdVo);
		return AjaxResult.success(null, ManagerResultCode.USER_USER_OPERATION_SUCCESS);
	}
	
}
