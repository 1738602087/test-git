package cn.repeatlink.module.usercenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import cn.repeatlink.module.usercenter.service.IGeneralOrderService;
import cn.repeatlink.module.usercenter.vo.GeneralDeductRecordVo;
import cn.repeatlink.module.usercenter.vo.GeneralOrderItemVo;
import cn.repeatlink.module.usercenter.vo.ReqGeneralOrderSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author LAI
 * @date 2021-01-18 11:32
 */
@RLPermission(userType = UserType.SYS)
@RestController
@RequestMapping("/account/general")
@Api(value="一般用户帐号管理", tags = "一般用户帐号管理")
public class GeneralManageController extends BaseController {
	
	@Autowired
	private IGeneralOrderService generalOrderService;
	
	@ApiOperation(value="决済一覧",notes = "决済一覧")
	@PostMapping("/userlist")
	public AjaxResult<RLPage<GeneralOrderItemVo>> getUserList(@RequestBody PageDomain<ReqGeneralOrderSearchVo> pageDomain){
		RLPageHelper.startPage(pageDomain);
		List<GeneralOrderItemVo> datas = this.generalOrderService.getOrderGeneralList(pageDomain.getSearchConditon());
		return AjaxResult.success(new RLPage<>((Page<GeneralOrderItemVo>) datas));
	}
	
	@ApiOperation(value="决済详细",notes = "决済详细")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "user_id", value = "用户ID")
	@GetMapping("/{user_id}/deductlist")
	public AjaxResult<List<GeneralDeductRecordVo>> getUserDeductList(@PathVariable("user_id") String userId){
		return AjaxResult.success(this.generalOrderService.getUserDeductList(userId));
	}
	
}
