package cn.repeatlink.module.usercenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.usercenter.service.ILawManageService;
import cn.repeatlink.module.usercenter.vo.LawApplyDetailVo;
import cn.repeatlink.module.usercenter.vo.LawApplyItemVo;
import cn.repeatlink.module.usercenter.vo.LawGroupDetailVo;
import cn.repeatlink.module.usercenter.vo.LawGroupItemVo;
import cn.repeatlink.module.usercenter.vo.ReqLawApplyReviewVo;
import cn.repeatlink.module.usercenter.vo.ReqLawApplySearchVo;
import cn.repeatlink.module.usercenter.vo.ReqLawGroupSearchVo;
import cn.repeatlink.module.usercenter.vo.ReqLawGroupStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RLPermission(userType = UserType.SYS)
@RestController
@RequestMapping("/account/law")
@Api(value="事务所帐号管理", tags = "事务所帐号管理")
public class LawManageController extends BaseController {
	
	@Autowired
	private ILawManageService lawManageService;
	
	@ApiOperation(value="申し込み获取一覧",notes = "申し込み获取一覧")
	@PostMapping("/applylist")
	public AjaxResult<RLPage<LawApplyItemVo>> getApplyList(@RequestBody PageDomain<ReqLawApplySearchVo> pageDomain){
		RLPageHelper.startPage(pageDomain);
		List<LawApplyItemVo> datas = this.lawManageService.searchApplyList(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<LawApplyItemVo>) datas));
	}
	
	@ApiOperation(value="申し込み获取详细",notes = "申し込み获取详细")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "apply_id", value = "申请ID")
	@GetMapping("/apply/{apply_id}")
	public AjaxResult<LawApplyDetailVo> getApplyDetail(@PathVariable("apply_id") String applyId){
		return AjaxResult.success(this.lawManageService.getApplyDetail(applyId));
	}
	
	@ApiOperation(value="申し込み审核",notes = "申し込み审核")
	@PostMapping("/apply/{apply_id}/review")
	public AjaxResult<Object> reviewApply(@PathVariable("apply_id") String applyId, @RequestBody ReqLawApplyReviewVo vo){
		this.lawManageService.reviewApply(super.loginUserId().toString(), applyId, vo.getState(), vo.getReject_reason());
		return AjaxResult.success();
	}
	
	@ApiOperation(value="申し込み删除",notes = "申し込み删除")
	@DeleteMapping("/apply/{apply_id}")
	public AjaxResult<Object> deleteApply(@PathVariable("apply_id") String applyId){
		this.lawManageService.deleteApply(super.loginUserId().toString(), applyId);
		return AjaxResult.success();
	}
	
	@ApiOperation(value="申し込み再送信",notes = "申し込み再送信")
	@PostMapping("/apply/{apply_id}/sendmail")
	public AjaxResult<Object> sendApplyMail(@PathVariable("apply_id") String applyId){
		this.lawManageService.sendApplyResultMail(applyId);
		return AjaxResult.success();
	}
	
	@ApiOperation(value="アカウント获取一覧",notes = "アカウント获取一覧")
	@PostMapping("/grouplist")
	public AjaxResult<RLPage<LawGroupItemVo>> getGroupList(@RequestBody PageDomain<ReqLawGroupSearchVo> pageDomain){
		RLPageHelper.startPage(pageDomain);
		List<LawGroupItemVo> datas = this.lawManageService.searchGroupList(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<LawGroupItemVo>) datas));
	}
	
	@ApiOperation(value="アカウント获取详细",notes = "アカウント获取详细")
	@GetMapping("/group/{group_id}")
	public AjaxResult<LawGroupDetailVo> getGroupDetail(@PathVariable("group_id") String groupId){
		return AjaxResult.success(this.lawManageService.getGroupDetail(groupId));
	}
	
	@ApiOperation(value="アカウント状态变更",notes = "アカウント状态变更")
	@PostMapping("/group/{group_id}/status")
	public AjaxResult<Object> updateGroupStatus(@PathVariable("group_id") String groupId, @RequestBody ReqLawGroupStatusVo vo){
		this.lawManageService.updateGroupStatus(super.loginUserId().toString(), groupId, vo.getStatus());
		return AjaxResult.success();
	}
	
}
