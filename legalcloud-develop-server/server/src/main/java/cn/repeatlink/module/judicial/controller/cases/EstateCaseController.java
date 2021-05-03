/**
 * 
 */
package cn.repeatlink.module.judicial.controller.cases;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jfinal.plugin.activerecord.Db;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.CaseEstate;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.judicial.common.Constant.CaseStepVal;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.controller.BaseJudicialController;
import cn.repeatlink.module.judicial.service.IEstateCaseService;
import cn.repeatlink.module.judicial.service.IEstateService;
import cn.repeatlink.module.judicial.service.IUserFaceService;
import cn.repeatlink.module.judicial.vo.ListPicVo;
import cn.repeatlink.module.judicial.vo.estatecase.CaseSearchVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBaseVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBuyerVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseDetailVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseSellerVo;
import cn.repeatlink.module.judicial.vo.user.BuyerUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-10 14:59
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/estatecase")
@Api(value="不动产案件管理", tags = "不动产案件")
@ApiSort(3)
public class EstateCaseController extends BaseJudicialController {
	
	@Autowired
	private IEstateCaseService estateCaseService;
	
	@Autowired
	private IEstateService estateService;
	
	@Autowired
	private IUserFaceService userFaceService;
	
	@ApiOperationSupport(order = 1, ignoreParameters = {"vo.datas", "vo.size", "vo.search_vo.estate_list", "vo.search_vo.case_id", "vo.search_vo.fetch_api"
			, "vo.search_vo.start_date", "vo.search_vo.step_buyer_input", "vo.search_vo.step_deal_finish"
			, "vo.search_vo.step_seller_verify"})
	@ApiOperation(value="检索案件",notes = "条件检索案件")
	@PostMapping("/list")
	public AjaxResult<PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo>> list(@RequestBody PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> vo){
		return AjaxResult.success(this.estateCaseService.getCaseList(super.groupId(), super.judicialUserId(), vo));
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.status", "vo.step_seller_verify", "vo.case_id", "vo.step_buyer_input", "vo.step_deal_finish"
			, "vo.step_reg_finish", "vo.fetch_api", "vo.create_time", "vo.operate_auth", "vo.user_judicial_id"})
	@ApiOperation(value="新规案件",notes = "案件新规")
	@PostMapping("/create")
	public AjaxResult<Object> create(@RequestBody EstatecaseBaseVo vo){
		return AjaxResult.success(this.estateCaseService.createNewCase(super.judicialUserId(), vo));
	}
	
	@ApiOperationSupport(order = 3, ignoreParameters = {"vo.status", "vo.case_id", "vo.step_seller_verify", "vo.step_buyer_input", "vo.step_deal_finish"
			, "vo.step_reg_finish", "vo.fetch_api", "vo.create_time", "vo.operate_auth", "vo.user_judicial_id"})
	@ApiOperation(value="更新案件",notes = "更新案件")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
	})
	@PutMapping("/{case_id}")
	public AjaxResult<Object> update(@PathVariable("case_id") String caseId, @RequestBody EstatecaseBaseVo vo){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		vo.setCase_id(caseId);
		return AjaxResult.success(this.estateCaseService.updateCase(super.judicialUserId(), vo));
	}
	
	@ApiOperationSupport(order = 20)
	@ApiOperation(value="获取用户情报",notes = "获取用户情报")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "用户ID")
	})
	@GetMapping("/{case_id}/buyer/{user_id}")
	public AjaxResult<BuyerUserInfo> getBuyerInfo(@PathVariable("case_id") String caseId, @PathVariable("user_id") String userId){
		return AjaxResult.success(this.estateCaseService.getBuyerUserInfo(userId));
	}
	
	
	@ApiOperationSupport(order = 30, includeParameters = {"Authorization", "case_id", "vo.user_id", "vo.fullname", "vo.fullname_kana"
			, "vo.gender", "vo.postcode", "vo.birthday", "vo.addr1", "vo.addr2", "vo.addr3", "vo.cert_base64"})
	@ApiOperation(value="添加买主",notes = "追加买主名义人")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@PostMapping("/{case_id}/buyer/add")
	public AjaxResult<Object> addBuyer(@PathVariable("case_id") String caseId, @RequestBody EstatecaseBuyerVo vo){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		this.estateCaseService.addCaseBuyer(super.judicialUserId(), caseId, vo);
		return AjaxResult.success(null, MessageUtil.getMessage("judicial.case.add.buyer.success.msg"));
	}
	
	@ApiOperationSupport(order = 33, includeParameters = {"Authorization", "case_id", "user_id", "vo.fullname", "vo.fullname_kana"
			, "vo.gender", "vo.postcode", "vo.birthday", "vo.addr1", "vo.addr2", "vo.addr3"})
	@ApiOperation(value="更新买主信息",notes = "更新买主名义人")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "用户ID")
	})
	@PutMapping("/{case_id}/buyer/{user_id}")
	public AjaxResult<Object> updateBuyer(@PathVariable("case_id") String caseId, @PathVariable("user_id") String userId, @RequestBody EstatecaseBuyerVo vo){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		this.estateCaseService.updateCaseBuyer(super.judicialUserId(), caseId, userId, vo);
		return AjaxResult.success(null, MessageUtil.getMessage("judicial.case.update.buyer.success.msg"));
	}
	
	@ApiOperationSupport(order = 40)
	@ApiOperation(value="移除买主",notes = "移除买主名义人")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "买主ID")
	})
	@DeleteMapping("/{case_id}/buyer/{user_id}")
	public AjaxResult<Object> removeBuyer(@PathVariable("case_id") String caseId, @PathVariable("user_id")String userId){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		this.estateCaseService.removeCaseBuyer(super.judicialUserId(), caseId, userId);
		return AjaxResult.success(null);
	}
	
//	@ApiOperationSupport(order = 50, includeParameters = {"Authorization", "case_id", "vo.estate_id", "vo.face_code"})
//	@ApiOperation(value="选择房产",notes = "选择要交易的房产")
//	@ApiImplicitParams({
//		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
//	})
//	@PostMapping("/{case_id}/estate/appoint")
//	public AjaxResult<EstateUserInfo> appointEstate(@PathVariable("case_id") String caseId, @RequestBody EstateUserInfo vo){
//		return AjaxResult.success(this.estateCaseService.chooseEstate(super.judicialUserId(), caseId, vo.getEstate_id(), vo.getFace_code()));
//	}
	
	@ApiOperationSupport(order = 60, includeParameters = {"Authorization", "case_id", "vo.user_id", "vo.pic_base64_list"})
	@ApiOperation(value="验证卖主",notes = "人脸验证卖主")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@PostMapping("/{case_id}/seller/verify")
	public AjaxResult<Object> verifySeller(@PathVariable("case_id") String caseId, @RequestBody EstatecaseSellerVo vo){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		this.estateCaseService.verifyCaseSeller(super.judicialUserId(), caseId, vo);
		return AjaxResult.success(null);
	}

//	@ApiOperationSupport(order = 70, ignoreParameters = {"vo.estate_id"})
//	@ApiOperation(value="录入房产信息",notes = "录入房产信息")
//	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
//	@PostMapping("/{case_id}/estate/bind")
//	public AjaxResult<Object> bindEstate(@PathVariable("case_id") String caseId, @RequestBody EstateBaseVo vo){
//		this.estateCaseService.inputNewEstate(super.judicialUserId(), caseId, vo);
//		return AjaxResult.success(null);
//	}
	
	@ApiOperationSupport(order = 69)
	@ApiOperation(value="买主登记完了",notes = "买主登记完了")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@PostMapping("/{case_id}/step/buyerinput")
	public AjaxResult<Object> stepBuyerInput(@PathVariable("case_id") String caseId){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		// 
		this.estateCaseService.stepBuyerInput(super.judicialUserId(), caseId);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 70)
	@ApiOperation(value="取引完了",notes = "取引完了")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@PostMapping("/{case_id}/step/dealfinish")
	public AjaxResult<Object> dealFinish(@PathVariable("case_id") String caseId){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		// 
		this.estateCaseService.stepDealFinished(super.judicialUserId(), caseId);
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 71)
	@ApiOperation(value="登记完了",notes = "登记完了")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@PostMapping("/{case_id}/step/regfinish")
	public AjaxResult<Object> regFinish(@PathVariable("case_id") String caseId){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		// 
		this.estateCaseService.stepRegFinished(super.judicialUserId(), caseId);
		return AjaxResult.success(null);
	}

	
	@ApiOperationSupport(order = 90)
	@ApiOperation(value="买主颜情报更新",notes = "买主颜情报更新")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "买主ID")
	})
	@PostMapping("/{case_id}/{user_id}/faceinfo")
	public AjaxResult<Object> updateBuyerFaceinfo(@PathVariable("case_id")String caseId, @PathVariable("user_id")String userId, @RequestBody ListPicVo vo){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		// 2021-03-25
		// 允许颜情报更新
//		CaseEstate ce = this.estateCaseService.checkCase(caseId, null);
//		// 已取引完了
//		if(CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
//			throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
//		}
		List<EstatecaseBuyerVo> buyerList = this.estateCaseService.getCaseBuyersInfo(caseId);
		this.checkBuyer(buyerList, userId);
		this.userFaceService.inputFaceData(super.judicialUserId(), userId, vo.getPic_base64_list().get(0));
		return AjaxResult.success(null);
	}
	
	@ApiOperationSupport(order = 91)
	@ApiOperation(value="买主颜情报删除",notes = "买主颜情报删除")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "买主ID")
	})
	@DeleteMapping("/{case_id}/{user_id}/faceinfo")
	public AjaxResult<Object> deleteBuyerFaceinfo(@PathVariable("case_id")String caseId, @PathVariable("user_id")String userId){
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		
		CaseEstate ce = this.estateCaseService.checkCase(caseId, null);
		// 已取引完了
		if(CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
			throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
		}
		List<EstatecaseBuyerVo> buyerList = this.estateCaseService.getCaseBuyersInfo(caseId);
		this.checkBuyer(buyerList, userId);
		this.userFaceService.deleteFaceByUserId(super.judicialUserId(), userId);
		return AjaxResult.success(null);
	}

	@ApiOperationSupport(order = 100)
	@ApiOperation(value="获取案件详情",notes = "获取案件详情")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@GetMapping("/{case_id}")
	public AjaxResult<EstatecaseDetailVo> getCaseDetail(@PathVariable("case_id")String caseId) {
		return AjaxResult.success(this.estateCaseService.getCaseDetail(super.judicialUserId(), caseId));
	}
	
	@ApiOperationSupport(order = 110)
	@ApiOperation(value="不动产登记情报PDF下载",notes = "不动产登记情报PDF下载")
	@GetMapping(value = "/{case_id}/estate/{estate_id}/pdf/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadPdf(@PathVariable("case_id")String caseId, @PathVariable("estate_id")String estateId, HttpServletResponse response) {
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		File pdfFile = this.estateService.getEstatePdfFile(estateId);
		if(pdfFile == null) {
			throw new RuntimeException("judicial.case.estate.error.estate.file.pdf.not.found");
		}
		String fileName = pdfFile.getName();
		super.downloadFile(response, pdfFile, fileName);
	}
	
	@ApiOperationSupport(order = 120)
	@ApiOperation(value="删除案件",notes = "删除案件")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	@DeleteMapping("/{case_id}")
	public AjaxResult<EstatecaseDetailVo> invalidCase(@PathVariable("case_id")String caseId) {
		// 操作权限检查
		this.checkCaseOpera(super.judicialUserId(), caseId);
		this.estateCaseService.invalidCase(super.judicialUserId(), caseId);
		return AjaxResult.success();
	}
	
	private void checkBuyer(List<EstatecaseBuyerVo> buyerList, String buyerId) {
		if(buyerList != null) {
			for (EstatecaseBuyerVo vo : buyerList) {
				if(vo.getUser_id().equals(buyerId)) {
					return;
				}
			}
		}
		throw JudicialRuntimeException.build("judicial.case.step.error.no.buyer");
	}
	
	private void checkCaseOpera(String judicialUserId, String caseId) {
		String caseUserId = Db.queryStr("select user_judicial_id from case_estate where case_id = ?", caseId);
		String operaUserId = judicialUserId;
		String caseUserGroupId = Db.queryStr("select group_id from user_judicial where user_id=? ", caseUserId);
		String operaUserGroupId = Db.queryStr("select group_id from user_judicial where user_id=? ", operaUserId);
		if(StringUtils.isBlank(operaUserGroupId) || !StringUtils.equals(operaUserGroupId, caseUserGroupId)) {
			throw JudicialRuntimeException.build("judicial.case.update.not.permission");
		}
	}
}
