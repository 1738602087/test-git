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
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.vo.auth.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-27 14:35
 */

@RLPermission(noCheck = true)
@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/login")
@Api(value="登录", tags = "认证")
@ApiSort(1)
public class GeneralLoginController extends BaseGeneralController {

	@ApiOperationSupport(order = 1)
	@ApiOperation(value="帐号密码登录",notes = "通过帐号密码获取token凭证")
	@PostMapping
	public AjaxResult<Object> index(@RequestBody LoginVo vo){
		return AjaxResult.success(null);
	}
	
}
