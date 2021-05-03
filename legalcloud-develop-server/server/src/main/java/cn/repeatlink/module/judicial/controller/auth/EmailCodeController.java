/**
 * 
 */
package cn.repeatlink.module.judicial.controller.auth;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.hutool.core.io.IoUtil;
import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.vo.EmailVo;
import cn.repeatlink.module.judicial.vo.RegVo;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-10 14:16
 */

@RLPermission(noCheck = true)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/validatecode")
@Api(value="验证码", tags = "认证")
@ApiSort(2)
public class EmailCodeController {
	
	@Resource(name = "emailValidateCodeService")
	private IValidateCodeService validateCodeService;
	
	
	@ApiOperationSupport(order = 4)
	@ApiOperation(value="发送邮箱验证码",notes = "发送邮箱验证码")
	@PostMapping("/send/email")
	public AjaxResult<Object> sendEmail(@RequestBody EmailVo vo){
		String email = vo.getEmail();
		String subject = MessageUtil.getMessage("judicial.auth.reg.send.code.mail.subject");
		// String template = MessageUtil.getMessage("judicial.auth.reg.send.code.mail.text");
		String template = IoUtil.read(EmailCodeController.class.getResourceAsStream("_ValidateCodeEmailTemplate.txt"), "UTF-8");
		this.validateCodeService.sendCodeByEnjoy(email, subject, template, 10, null);
		return AjaxResult.success(null, MessageUtil.getMessage("judicial.auth.reg.send.code.mail.result.success"));
	}
	
	@ApiOperationSupport(order = 10, ignoreParameters = {"vo.reg_code", "vo.login_id", "vo.password"})
	@ApiOperation(value="验证邮箱验证码",notes = "验证邮箱验证码")
	@PostMapping("/checkcode")
	public AjaxResult<Object> checkCode(@RequestBody RegVo vo){
		this.validateCodeService.validate(vo.getEmail(), vo.getVerify_code());
		return AjaxResult.success();
	}

}
