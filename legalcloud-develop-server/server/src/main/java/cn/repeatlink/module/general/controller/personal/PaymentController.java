/**
 * 
 */
package cn.repeatlink.module.general.controller.personal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.service.IGeneralPaymentService;
import cn.repeatlink.module.general.vo.payment.AutoDeductionActionVo;
import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import cn.repeatlink.module.general.vo.payment.CreditCardTokenVo;
import cn.repeatlink.module.general.vo.payment.CreditCardVo;
import cn.repeatlink.module.general.vo.payment.FeeDetailVo;
import cn.repeatlink.module.general.vo.payment.FeeItemVo;
import cn.repeatlink.module.judicial.service.IOmiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-20 15:10
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/payment")
@Api(value="支付设定", tags = "支付设定")
@ApiSort(60)
public class PaymentController extends BaseGeneralController {
	
	@Autowired
	private IGeneralPaymentService generalPaymentService;
	
	@Autowired
	private IOmiseService omiseService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取信用卡列表",notes = "获取信用卡列表")
	@GetMapping("/cardlist")
	public AjaxResult<List<CreditCardVo>> getCardList() {
		return AjaxResult.success(this.generalPaymentService.getCreditCardListByUserId(super.generalUserId()));
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.id"})
	@ApiOperation(value="添加信用卡",notes = "添加信用卡")
	@PostMapping("/card")
	public AjaxResult<Object> addCard(@RequestBody CreditCardTokenVo vo) {
		this.generalPaymentService.bindCreditCard(super.generalUserId(), vo);
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 3)
	@ApiOperation(value="删除信用卡",notes = "删除信用卡")
	@DeleteMapping("/card/{id}")
	public AjaxResult<Object> deleteCard(@PathVariable("id") String id) {
		this.generalPaymentService.unbindCreditCard(super.generalUserId(), id);
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 4)
	@ApiOperation(value="设置默认信用卡",notes = "设置默认信用卡")
	@PutMapping("/card/{id}/select")
	public AjaxResult<Object> selectCard(@PathVariable("id") String id) {
		this.generalPaymentService.selectCreditCard(super.generalUserId(), id);
		return AjaxResult.success();
	}
	
	
	@ApiOperationSupport(order = 14, ignoreParameters = {"vo.datas"})
	@ApiOperation(value="获取决済履历一览",notes = "获取决済履历一览")
	@PostMapping("/feelist")
	public AjaxResult<PageLoadMoreVo<FeeDetailVo, FeeItemVo>> getFeeList(@RequestBody PageLoadMoreVo<FeeDetailVo, FeeItemVo> vo) {
		return AjaxResult.success(this.generalPaymentService.getFeeHistoryList(super.generalUserId(), vo));
	}
	
	@ApiOperationSupport(order = 15)
	@ApiOperation(value="获取决済详细",notes = "获取决済详细")
	@GetMapping("/fee/{fee_id}")
	public AjaxResult<FeeDetailVo> getFeeDetail(@PathVariable("fee_id") String feeId) {
		return AjaxResult.success(this.generalPaymentService.getFeeDetail(super.generalUserId(), feeId));
	}
	
	@ApiOperationSupport(order = 16)
	@ApiOperation(value="获取自动决済情报",notes = "获取自动决済情报")
	@GetMapping("/autodeduct")
	public AjaxResult<List<AutoDeductionVo>> getAutoDeductionInfo() {
		List<AutoDeductionVo> voList = this.generalPaymentService.getAutoDeductionInfo(super.generalUserId());
		if(voList != null) {
			// 2021-02-23
			// 只显示自己加入的
			for(int i = 0; i < voList.size(); i++) {
				if(Boolean.TRUE.equals(voList.get(i).getOnly_show())) {
					voList.remove(i--);
				}
			}
		}
		return AjaxResult.success(voList);
	}
	
	@ApiOperationSupport(order = 17)
	@ApiOperation(value="解约自动决済",notes = "解约自动决済")
	@DeleteMapping("/autodeduct")
	public AjaxResult<Object> terminate(@RequestBody AutoDeductionActionVo vo) {
		this.generalPaymentService.terminateAutoDeduction(super.generalUserId(), vo.getSet_id());
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 18)
	@ApiOperation(value="加入自动决済",notes = "加入保护计划，加入自动决済")
	@PostMapping("/autodeduct")
	public AjaxResult<Object> join(@RequestBody AutoDeductionActionVo vo) {
		this.generalPaymentService.joinAutoDeduction(super.generalUserId(), vo.getSet_id());
		return AjaxResult.success();
	}
	

	@ApiOperationSupport(order = 20)
	@ApiOperation(value="获取OMISE的PublicKey",notes = "获取OMISE的PublicKey")
	@GetMapping("/pkey")
	public AjaxResult<String> getPKey() {
		return AjaxResult.success(this.omiseService.getOmisePublicKey());
	}
	
}
