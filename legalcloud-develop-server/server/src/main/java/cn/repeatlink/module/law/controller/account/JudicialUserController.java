/**
 * 
 */
package cn.repeatlink.module.law.controller.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.common.Constant;
import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IJudicialManageService;
import cn.repeatlink.module.law.vo.user.JudicialUserApplyVo;
import cn.repeatlink.module.law.vo.user.JudicialUserVo;
import cn.repeatlink.module.law.vo.user.ReqJudicialUserUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-10 13:45
 */

@RLPermission(userType = {UserType.LAW})
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/judicialmanage")
@Api(value="司法账户管理", tags = "司法账户管理")
@ApiSort(31)
public class JudicialUserController extends BaseLawController {
	
	@Autowired
	private IJudicialManageService judicialManageService;
	
	@ApiOperationSupport(order = 1, ignoreParameters = {"searchConditon.user_id", "searchConditon.reg_date", "searchConditon.status"})
	@ApiOperation(value="利用中一览",notes = "获取利用中一览")
	@PostMapping("/normallist")
	public AjaxResult<RLPage<JudicialUserVo>> getNormalList(@RequestBody PageDomain<JudicialUserVo> vo) throws Exception {
		RLPageHelper.startPage(vo);
		List<JudicialUserVo> datas = this.judicialManageService.getNormalUserList(super.groupId(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<JudicialUserVo>) datas));
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"searchConditon.id", "searchConditon.send_time"})
	@ApiOperation(value="申请中一览",notes = "获取申请中一览")
	@PostMapping("/applylist")
	public AjaxResult<RLPage<JudicialUserApplyVo>> getApplyList(@RequestBody PageDomain<JudicialUserApplyVo> vo) throws Exception {
		RLPageHelper.startPage(vo);
		List<JudicialUserApplyVo> datas = this.judicialManageService.getApplyUserList(super.groupId(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<JudicialUserApplyVo>) datas));
	}
	
	@ApiOperationSupport(order = 3, ignoreParameters = {"searchConditon.user_id", "searchConditon.reg_date", "searchConditon.status"})
	@ApiOperation(value="废弃一览",notes = "获取废弃一览")
	@PostMapping("/invalidlist")
	public AjaxResult<RLPage<JudicialUserVo>> getInvalidList(@RequestBody PageDomain<JudicialUserVo> vo) throws Exception {
		RLPageHelper.startPage(vo);
		List<JudicialUserVo> datas = this.judicialManageService.getInvalidUserList(super.groupId(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<JudicialUserVo>) datas));
	}
	
	@ApiOperationSupport(order = 4, ignoreParameters = {"vo.fullname", "vo.email", "vo.login_id", "vo.reg_date"})
	@ApiOperation(value="变更使用状态",notes = "变更使用状态")
	@PutMapping("/user/status")
	public AjaxResult<Object> changeStatus(@RequestBody JudicialUserVo vo) throws Exception {
		Short status = vo.getStatus();
		String userId = vo.getUser_id();
		if(Constant.STATUS_INVALID.equals(status)) {
			this.judicialManageService.invalidUser(super.loginUserInfo(), userId);
		} else if (Constant.STATUS_VALID.equals(status)) {
			this.judicialManageService.reviveUser(super.loginUserInfo(), userId);
		}
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 5, ignoreParameters = {"vo.user_id", "vo.fullname", "vo.status", "vo.login_id", "vo.reg_date"})
	@ApiOperation(value="新建账户",notes = "新建账户")
	@PostMapping("/user")
	public AjaxResult<Object> newUser(@RequestBody JudicialUserVo vo) throws Exception {
		this.judicialManageService.newUser(super.loginUserInfo(), vo.getEmail());
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 6, ignoreParameters = {"vo.user_id", "vo.fullname", "vo.status", "vo.login_id", "vo.reg_date"})
	@ApiOperation(value="再送信",notes = "再送信")
	@PostMapping("/user/sendmail")
	public AjaxResult<Object> sendMail(@RequestBody JudicialUserVo vo) throws Exception {
		this.judicialManageService.sendMailAgain(super.loginUserInfo(), vo.getEmail());
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 70, ignoreParameters = { })
	@ApiOperation(value="变更帐号信息",notes = "变更帐号信息")
	@PutMapping("/user/{user_id}")
	public AjaxResult<Object> updateUserInfo(@PathVariable("user_id") String userId, @RequestBody ReqJudicialUserUpdateVo vo) throws Exception {
		this.judicialManageService.updateUser(super.loginUserInfo(), userId, vo);
		return AjaxResult.success();
	}

}
