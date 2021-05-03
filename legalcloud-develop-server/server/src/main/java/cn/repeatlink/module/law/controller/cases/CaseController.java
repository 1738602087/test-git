/**
 * 
 */
package cn.repeatlink.module.law.controller.cases;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.judicial.service.ICaseBuyerChangeService;
import cn.repeatlink.module.judicial.vo.estatecase.BuyerInfoChangeItemVo;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.ICaseManageService;
import cn.repeatlink.module.law.vo.cases.CaseInfoVo;
import cn.repeatlink.module.law.vo.cases.CaseItemVo;
import cn.repeatlink.module.law.vo.cases.ReqCaseSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-10 11:06
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/casemanage")
@Api(value="案件管理", tags = "案件管理")
@ApiSort(11)
public class CaseController extends BaseLawController {
	
	@Autowired
	private ICaseManageService caseManageService;
	
	@Autowired
	private ICaseBuyerChangeService caseBuyerChangeService;
	
	@ApiOperationSupport(order = 10)
	@ApiOperation(value="对应完了一览",notes = "获取对应完了一览")
	@PostMapping("/completelist")
	public AjaxResult<RLPage<CaseItemVo>> getComplete(@RequestBody PageDomain<ReqCaseSearchVo> pageDomain) throws Exception {
		RLPageHelper.startPage(pageDomain);
		List<CaseItemVo> datas = this.caseManageService.getCompletedCaseList(super.loginUserInfo(), pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<CaseItemVo>) datas));
	}
	
	@ApiOperationSupport(order = 20)
	@ApiOperation(value="对应中一览",notes = "获取对应中一览", produces = "application/json")
	@PostMapping("/processlist")
	public AjaxResult<RLPage<CaseItemVo>> getProcess(@RequestBody PageDomain<ReqCaseSearchVo> pageDomain) throws Exception {
		RLPageHelper.startPage(pageDomain);
		List<CaseItemVo> datas = this.caseManageService.getProcessCaseList(super.loginUserInfo(), pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<CaseItemVo>) datas));
	}
	
	@ApiOperationSupport(order = 30)
	@ApiOperation(value="案件详细",notes = "获取案件详细")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
	})
	@GetMapping("/case/{case_id}")
	public AjaxResult<CaseInfoVo> getCaseDetail(@PathVariable("case_id")String caseId) throws Exception {
		return AjaxResult.success(this.caseManageService.getCaseDetail(caseId));
	}
	
	@ApiOperationSupport(order = 40)
	@ApiOperation(value="买主情报变更履历",notes = "获取买主情报变更履历")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID"),
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "buyer_id", value = "买主ID")
	})
	@GetMapping("/case/{case_id}/buyer/{buyer_id}/changerecord")
	public AjaxResult<List<BuyerInfoChangeItemVo>> getCaseBuyerRecord(@PathVariable("case_id")String caseId, @PathVariable("buyer_id")String buyerId) throws Exception {
		return AjaxResult.success(this.caseBuyerChangeService.getChangeRecordList(caseId, buyerId));
	}
	
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value="更新案件",notes = "更新案件")
//	@ApiImplicitParams({
//		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "case_id", value = "案件ID")
//	})
//	@PutMapping("/case/{case_id}")
//	public AjaxResult<Object> updateCase(@PathVariable("case_id")String caseId, @RequestBody CaseInfoVo vo) throws Exception {
//		if(CaseStatus.COMPLETED.equals(vo.getStatus())) {
//			this.caseManageService.doneCase(super.loginUserInfo().getGroup_id(), caseId);
//		}
//		return AjaxResult.success();
//	}
	
	@ApiOperationSupport(order = 90)
	@ApiOperation(value="获取担当者列表",notes = "获取担当者列表")
	@GetMapping("/stafflist")
	public AjaxResult<List<?>> getStaffList() throws Exception {
		List<Record> list = Db.find("select user_id as id , fullname as name from user_judicial where group_id=? and trim(ifnull(fullname,'')) <> '' ", super.groupId());
		List<Kv> kvList = new ArrayList<>();
		String userId = super.judicialUserId();
		for(Record r : list) {
			if(StringUtils.isNotBlank(userId) && userId.equals(r.getStr("id"))) {
				kvList.add(0, Kv.by("id", r.getStr("id")).set("name", r.getStr("name") + "（本人）").set("selected", true));
			} else {
				kvList.add(Kv.by("id", r.getStr("id")).set("name", r.getStr("name")));
			}
		}
		kvList.add(Kv.by("id", "").set("name", "事務所すべて"));
		return AjaxResult.success(kvList);
	}

}
