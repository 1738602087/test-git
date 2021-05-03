/**
 * 
 */
package cn.repeatlink.module.law.controller.dashboard;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.bean.DataTableHeader;
import cn.repeatlink.common.bean.HeaderVo;
import cn.repeatlink.common.service.IDatatableHeaderService;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IDashboardService;
import cn.repeatlink.module.law.vo.SeachVo;
import cn.repeatlink.module.law.vo.cases.CaseInfo;
import cn.repeatlink.module.law.vo.dash.DownloadSeachVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-09 15:59
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/dashboard/case")
@Api(value="案件进行件数相关", tags = "dashboard")
@ApiSort(30)
public class ProcessCaseController extends BaseLawController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value="进行中件数及前月比",notes = "获取进行中件数及前月比")
//	@GetMapping("/info")
//	public AjaxResult<DashInfoVo> getInfo() throws Exception {
//		return AjaxResult.success(this.dashboardService.getProcessCaseCountAndPercent(super.loginUserInfo()));
//	}
//	
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value="案件进行率",notes = "获取案件进行率")
//	@GetMapping("/percent")
//	public AjaxResult<Object> getPercent() throws Exception {
//		return AjaxResult.success(this.dashboardService.getProcessCaseCompletePercent(super.loginUserInfo()));
//	}
	
	@ApiOperationSupport(order = 100)
	@ApiOperation(value="案件対応ランキング",notes = "案件対応ランキング")
	@GetMapping("/rank")
	public AjaxResult<Object> getRank() throws Exception {
		return AjaxResult.success(this.dashboardService.getCaseCompletedRank(super.loginUserInfo()));
	}

	@ApiOperationSupport(order = 110, ignoreParameters = "vo.searchConditon.type")
	@ApiOperation(value = "案件一览",notes = "案件一览")
	@PostMapping("/list")
	public AjaxResult<RLPage<CaseInfo>> getCaseInfos(@RequestBody PageDomain<SeachVo> vo){
		RLPageHelper.startPage(vo);
		List<CaseInfo> datas = this.dashboardService.getCaseInfos(super.loginUserInfo(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<CaseInfo>) datas));
	}
	
	@ApiOperationSupport(order = 120)
	@PostMapping("/list/download")
    @ApiOperation(value = "案件一览导出", notes = "案件一览导出", produces = "application/octet-stream")
    public void excelDownloadCase(HttpServletResponse response, @RequestBody DownloadSeachVo vo) throws IOException {

        DataTableHeader header = this.datatableHeaderService.getDataTableHeader(null, "dashboard.jud.case.table");
        List<CaseInfo> datas = this.dashboardService.getCaseInfos(super.loginUserInfo(), vo);
        List<HeaderVo> file_header = header.getFile_header();

        String fileName = MessageUtil.getMessage("jud.cases.excl.fileName") + DateUtil.formatDate(new Date()) + "." + vo.getFileType();
        super.prepareDownloadFile(response, fileName);
        super.export(response.getOutputStream(), vo.getFileType(), super.headerToMap(file_header), datas);
    }

}
