/**
 * 
 */
package cn.repeatlink.module.general.controller.cases;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.service.IGeneralCaseService;
import cn.repeatlink.module.general.service.IGeneralEstateService;
import cn.repeatlink.module.general.vo.main.MainData;
import cn.repeatlink.module.judicial.service.IEstateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-20 14:59
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/main")
@Api(value="案件相关", tags = "主页")
@ApiSort(20)
public class GeneralCaseController extends BaseGeneralController {
	
	@Autowired
	private IGeneralCaseService generalCaseService;
	
	@Autowired
	private IGeneralEstateService generalEstateService;
	
	@Autowired
	private IEstateService estateService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取首页数据",notes = "获取首页数据")
	@GetMapping("/data")
	public AjaxResult<MainData> getMain() {
		MainData data = new MainData();
		data.setCase_list(this.generalCaseService.getProcessCaseList(super.generalUserId()));
		data.setEstate_list(this.generalEstateService.getUserEstateList(super.generalUserId()));
		data.setStatus(this.generalCaseService.getUserCaseStatus(super.generalUserId(), data.getCase_list()));
		return AjaxResult.success(data);
	}
	
	@ApiOperationSupport(order = 10)
	@ApiOperation(value="不动产登记情报PDF下载",notes = "不动产登记情报PDF下载")
	@GetMapping(value = "/estate/{estate_id}/pdf/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadPdf(@PathVariable("estate_id") String estateId, HttpServletResponse response) {
		File pdfFile = this.estateService.getEstatePdfFile(estateId);
		if(pdfFile == null) {
			throw new RuntimeException("");
		}
		String fileName = pdfFile.getName();
		super.downloadFile(response, pdfFile, fileName);
	}
	
	@ApiOperationSupport(order = 20)
	@ApiOperation(value="不动产登记情报确认",notes = "不动产登记情报确认")
	@PostMapping("/case/{case_id}/confirm")
	public AjaxResult<Object> confirmCase(@PathVariable("case_id") String caseId) {
		this.generalCaseService.confirm(super.generalUserId(), caseId);
		return AjaxResult.success();
	}
	

}
