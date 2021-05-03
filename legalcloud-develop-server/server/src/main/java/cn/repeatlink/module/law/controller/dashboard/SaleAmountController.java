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
import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IDashboardService;
import cn.repeatlink.module.law.vo.SeachVo;
import cn.repeatlink.module.law.vo.dash.DownloadSeachVo;
import cn.repeatlink.module.law.vo.dash.SaleRecordInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-09 15:59
 */

@RLPermission(userType = {UserType.SYS})
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/dashboard/sale")
@Api(value="売上金额相关", tags = "dashboard")
@ApiSort(50)
public class SaleAmountController extends BaseLawController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@Autowired
	private IDatatableHeaderService datatableHeaderService;
	
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value="売上金额及前月比",notes = "获取売上金额及前月比")
//	@GetMapping("/info")
//	public AjaxResult<DashInfoVo> getInfo() throws Exception {
//		return AjaxResult.success(this.dashboardService.getSaleCountAndPercent(super.loginUserInfo()));
//	}
	
	@ApiOperationSupport(order = 300)
	@ApiOperation(value="売上推移",notes = "获取売上推移数据")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "param", name = "type", value = "日:day, 周:week, 月:month")
	})
	@GetMapping("/tran")
	public AjaxResult<Object> getTran(@RequestParam("type") String type) throws Exception {
		return AjaxResult.success(this.dashboardService.getSaleTran(super.loginUserInfo(), type));
	}

	@ApiOperationSupport(order = 310, ignoreParameters = "vo.searchConditon.type")
	@ApiOperation(value = "支付一览",notes = "支付一览")
	@PostMapping("/list")
	public AjaxResult<RLPage<SaleRecordInfo>> getServerInfos(@RequestBody PageDomain<SeachVo> vo){
		RLPageHelper.startPage(vo);
		List<SaleRecordInfo> data = this.dashboardService.getServerInfos(super.loginUserInfo(), vo.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<SaleRecordInfo>) data));
	}
	
	@ApiOperationSupport(order = 320)
	@PostMapping("/list/download")
    @ApiOperation(value = "支付一览导出", notes = "支付一览导出", produces = "application/octet-stream")
    public void excelDownloadServer(HttpServletResponse response, @RequestBody DownloadSeachVo vo) throws IOException {

        DataTableHeader header = this.datatableHeaderService.getDataTableHeader(null, "dashboard.user.server.table");
        List<SaleRecordInfo> datas = this.dashboardService.getServerInfos(super.loginUserInfo(), vo);
        List<HeaderVo> file_header = header.getFile_header();
        
        String fileName = MessageUtil.getMessage("user.server.excl.fileName") + DateUtil.formatDate(new Date()) + "." + vo.getFileType();
        super.prepareDownloadFile(response, fileName);
        super.export(response.getOutputStream(), vo.getFileType(), super.headerToMap(file_header), datas);
    }

}
