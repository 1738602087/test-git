/**
 * 
 */
package cn.repeatlink.module.judicial.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.controller.BaseJudicialController;
import cn.repeatlink.module.judicial.service.IAuthService;
import cn.repeatlink.module.judicial.service.IUserService;
import cn.repeatlink.module.judicial.vo.ListPicVo;
import cn.repeatlink.module.judicial.vo.SinglePicVo;
import cn.repeatlink.module.judicial.vo.UserBaseInfoVo;
import cn.repeatlink.module.judicial.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-11 10:36
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/personal")
@Api(value="个人信息管理", tags = "个人主页")
@ApiSort(6)
public class PersonalInfoController extends BaseJudicialController {
	
	@Autowired
	private IAuthService authService;
	
	@Autowired
	private IUserService userService;
	
	@ApiOperationSupport(order = 1, ignoreParameters = {"vo.use_type"})
	@ApiOperation(value="头像设置",notes = "头像设置")
	@PostMapping("/avatar")
	public AjaxResult<Object> setAvatar(@RequestBody SinglePicVo vo){
		this.userService.setAvatar(super.judicialUserId(), vo.getPic_base64());
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.password", "vo.old_password"})
	@ApiOperation(value="姓名设置",notes = "姓名设置")
	@PostMapping("/fullname")
	public AjaxResult<Object> setFullname(@RequestBody UserBaseInfoVo vo){
		this.userService.updateName(super.judicialUserId(), vo.getFamname(), vo.getFamname_kana(), vo.getGivename(), vo.getGivename_kana());
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 3, ignoreParameters = {"vo.famname", "vo.famname_kana", "vo.givename", "vo.givename_kana"})
	@ApiOperation(value="密码设置",notes = "密码设置")
	@PostMapping("/password")
	public AjaxResult<Object> setPassword(@RequestBody UserBaseInfoVo vo){
		this.authService.resetPwd(super.judicialUserId(), vo);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 4)
	@ApiOperation(value="颜情报更新",notes = "颜情报更新")
	@PostMapping("/faceinfo")
	public AjaxResult<Object> updateFaceinfo(@RequestBody ListPicVo vo){
		return AjaxResult.success(null);
	}

	@ApiOperationSupport(order = 5)
	@ApiOperation(value="获取个人基本情报",notes = "获取个人基本情报")
	@GetMapping("/info")
	public AjaxResult<UserInfo> getInfo(){
		return AjaxResult.success(this.userService.getInfo(super.judicialUserId()));
	}
}
