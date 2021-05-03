/**
 * 
 */
package cn.repeatlink.module.general.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.common.GeneralRuntimeException;
import cn.repeatlink.module.general.factory.GeneralRegValidateCodeServiceFactory;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;
import cn.repeatlink.module.usercenter.vo.SendCodeVo;
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
@ApiSort(3)
public class GeneralValidateCodeController {
	
	@ApiOperationSupport(order = 4)
	@ApiOperation(value="发送验证码",notes = "发送验证码")
	@PostMapping("/send")
	public AjaxResult<Object> sendCode(@RequestBody SendCodeVo vo){
		String target = vo.getTarget();
		String subject = MessageUtil.getMessage("general.auth.reg.send.code.mail.subject");
		String template = MessageUtil.getMessage("general.auth.reg.send.code.mail.text");
		IValidateCodeService service = GeneralRegValidateCodeServiceFactory.instance().getService();
		if(service == null) {
			throw GeneralRuntimeException.build("general.auth.reg.send.code.service.invalid");
		}
		service.sendCode(target, subject, template);
		return AjaxResult.success(null, MessageUtil.getMessage("general.auth.reg.send.code.success"));
	}

}
