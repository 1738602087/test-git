/**
 * 
 */
package cn.repeatlink.module.law.controller.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IDashboardService;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.dash.DashInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-09 15:59
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/dashboard/summary")
@Api(value="简要信息", tags = "dashboard")
@ApiSort(1)
public class DashboardController extends BaseLawController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取简要信息",notes = "获取简要信息")
	@GetMapping("/info")
	public AjaxResult<List<DashInfoVo>> getInfo() throws Exception {
		List<DashInfoVo> list = new ArrayList<>();
		LawLoginUserInfo loginUserInfo = super.loginUserInfo();
		list.add(this.dashboardService.getNewNumberAndPercent(loginUserInfo));
		if(UserType.SYS.equals(loginUserInfo.getUserType())) {
			list.add(this.dashboardService.getLawGroupDashInfo(loginUserInfo));
			list.add(this.dashboardService.getSaleCountAndPercent(loginUserInfo));
		}
		else {
			list.add(this.dashboardService.getProcessCaseCountAndPercent(loginUserInfo));
		}
		
		return AjaxResult.success(list);
	}
	
}
