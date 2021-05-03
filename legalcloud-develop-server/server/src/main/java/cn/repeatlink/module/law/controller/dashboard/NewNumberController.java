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
import org.springframework.web.bind.annotation.RequestParam;
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
import cn.repeatlink.module.law.vo.dash.DownloadSeachVo;
import cn.repeatlink.module.law.vo.user.GeneralInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-09 15:59
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/dashboard/general")
@Api(value="新规登录顾客相关", tags = "dashboard")
@ApiSort(20)
public class NewNumberController extends BaseLawController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value="新规登录人数及前月比",notes = "获取新规登录人数及前月比")
//	@GetMapping("/info")
//	public AjaxResult<DashInfoVo> getInfo() throws Exception {
//		return AjaxResult.success(this.dashboardService.getNewNumberAndPercent(super.loginUserInfo()));
//	}
	
	@ApiOperationSupport(order = 20)
	@ApiOperation(value="新规登录顾客人数推移",notes = "新规登录顾客人数推移")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "param", name = "type", value = "日:day, 周:week, 月:month")
	})
	@GetMapping("/tran")
	public AjaxResult<Object> getTran(@RequestParam("type") String type) throws Exception {
		return AjaxResult.success(this.dashboardService.getNewNumberTran(super.loginUserInfo(), type));
	}

	@ApiOperationSupport(order = 30 , ignoreParameters = "vo.searchConditon.type")
	@ApiOperation(value = "顾客一览",notes = "顾客一览")
	@PostMapping("/list")
	public AjaxResult<RLPage<GeneralInfo>> getGeneralInfos(@RequestBody PageDomain<SeachVo> vo){
		RLPageHelper.startPage(vo);
		List<GeneralInfo> datas = this.dashboardService.getGeneralInfos(super.loginUserInfo(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<GeneralInfo>) datas));
	}
	
	@ApiOperationSupport(order = 40)
	@PostMapping("/list/download")
    @ApiOperation(value = "顾客一览导出", notes = "顾客一览导出", produces = "application/octet-stream")
    public void excelDownloadUser(HttpServletResponse response, @RequestBody DownloadSeachVo vo) throws IOException {

        DataTableHeader header = this.datatableHeaderService.getDataTableHeader(null, "dashboard.user.general.table");
        List<GeneralInfo> datas = this.dashboardService.getGeneralInfos(super.loginUserInfo(), vo);
        List<HeaderVo> file_header = header.getFile_header();

        String fileName = MessageUtil.getMessage("user.logInfo.excl.fileName") + DateUtil.formatDate(new Date()) + "." + vo.getFileType();
        super.prepareDownloadFile(response, fileName);

        super.export(response.getOutputStream(), vo.getFileType(), super.headerToMap(file_header), datas);
    }

}
