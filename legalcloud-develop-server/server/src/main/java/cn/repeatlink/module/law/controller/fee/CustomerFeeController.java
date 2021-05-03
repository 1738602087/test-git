/**
 * 
 */
package cn.repeatlink.module.law.controller.fee;

import java.util.List;

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

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.PageDomain;
import cn.repeatlink.framework.bean.RLPage;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.util.RLPageHelper;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.ICustomerFeeService;
import cn.repeatlink.module.law.vo.fee.FeeItemVo;
import cn.repeatlink.module.law.vo.fee.QuitFeeItemVo;
import cn.repeatlink.module.law.vo.fee.UseFeeItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-10 14:08
 */

@RLPermission(userType = {UserType.SYS})
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/fee")
@Api(value="顾客决済", tags = "决済管理")
@ApiSort(51)
public class CustomerFeeController extends BaseLawController {
	
	@Autowired
	private ICustomerFeeService customerFeeService;

	@ApiOperationSupport(order = 1, ignoreParameters = {"vo.user_id", "vo.fullname", "vo.start_date", "vo.last_date", "last_money", "vo.last_fees"})
	@ApiOperation(value="利用中一览",notes = "获取利用中一览")
	@PostMapping("/uselist")
	public AjaxResult<RLPage<UseFeeItemVo>> getUseList(@RequestBody PageDomain<UseFeeItemVo> pageDomain) throws Exception {
		RLPageHelper.startPage(pageDomain);
		List<UseFeeItemVo> datas = this.customerFeeService.getUsingFeeUserList(super.loginUserInfo(), pageDomain.getSearchConditon() == null ? null : pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<UseFeeItemVo>) datas));
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.user_id", "vo.fullname", "vo.start_date", "vo.quit_date", "last_money", "vo.last_fees"})
	@ApiOperation(value="退会一览",notes = "获取退会一览")
	@PostMapping("/quitlist")
	public AjaxResult<RLPage<QuitFeeItemVo>> getQuitList(@RequestBody PageDomain<QuitFeeItemVo> pageDomain) throws Exception {
		RLPageHelper.startPage(pageDomain);
		List<QuitFeeItemVo> datas = this.customerFeeService.getQuitFeeUserList(super.loginUserInfo(), pageDomain.getSearchConditon() == null ? null : pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<QuitFeeItemVo>) datas));
	}
	
	@ApiOperationSupport(order = 3)
	@ApiOperation(value="顾客决済履历一览",notes = "获取顾客决済履历一览")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "顾客ID")
	})
	@GetMapping("/user/{user_id}/feelist")
	public AjaxResult<List<FeeItemVo>> getUserFeeList(@PathVariable("user_id")String userId) throws Exception {
		return AjaxResult.success(this.customerFeeService.getCustomerFeeHistoryList(super.loginUserInfo(), userId));
	}
	
}
