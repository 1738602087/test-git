/**
 * 
 */
package cn.repeatlink.module.law.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import io.swagger.annotations.Api;

/**
 * @author LAI
 * @date 2020-09-28 14:55
 */

@RLPermission(noCheck = true)
@Api(value="登录", tags = "认证")
@ApiSort(1)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/login")
public class LawLoginController extends BaseLawController {

//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value="帐号密码登录",notes = "通过帐号密码获取token凭证")
//	@PostMapping
//	public AjaxResult<Object> index(@RequestBody LoginVo vo){
//		return AjaxResult.success(null);
//	}
	
}
