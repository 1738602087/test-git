/**
 * 
 */
package cn.repeatlink.module.law.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.ILawRegService;
import cn.repeatlink.module.law.vo.auth.LawApplyVo;
import cn.repeatlink.module.law.vo.auth.LawRegVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-28 14:55
 */

@RLPermission(noCheck = true)
@Api(value="注册", tags = "认证")
@ApiSort(10)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/reg")
public class LawRegController extends BaseLawController {
	
	@Autowired
	private ILawRegService lawRegService;
	
	@ApiOperationSupport(order = 10)
	@ApiOperation(value="発行申請信息检查",notes = "発行申請信息检查")
	@PostMapping("/applycheck")
	public synchronized AjaxResult<Object> applyCheck(@RequestBody LawApplyVo vo){
		this.lawRegService.applyCheck(vo);
		return AjaxResult.success();
	}

	@ApiOperationSupport(order = 11)
	@ApiOperation(value="発行申請",notes = "発行申請")
	@PostMapping("/apply")
	public synchronized AjaxResult<Object> apply(@RequestBody LawApplyVo vo){
		this.lawRegService.apply(vo);
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 21)
	@ApiOperation(value="新规登录",notes = "新规登录")
	@PostMapping
	public synchronized AjaxResult<Object> reg(@RequestBody LawRegVo vo){
		this.lawRegService.regGroup(vo);
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 31, ignoreParameters = {"vo.login_id", "vo.password"})
	@ApiOperation(value="招待码验证",notes = "招待码验证")
	@PostMapping("/regcode")
	public AjaxResult<Object> checkRegcode(@RequestBody LawRegVo vo){
		this.lawRegService.checkRegCode(vo.getEmail(), vo.getReg_code());
		return AjaxResult.success();
	}
	
}
