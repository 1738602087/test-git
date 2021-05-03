/**
 * 
 */
package cn.repeatlink.module.law.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IUserCenterService;
import cn.repeatlink.module.law.vo.ReqUpdatePwdVo;
import cn.repeatlink.module.law.vo.user.LawUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-28 15:29
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/personal")
@Api(value="个人信息管理", tags = "个人主页")
public class LawPersonalController extends BaseLawController {
	
	@Autowired
	private IUserCenterService userCenterService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取个人基本情报",notes = "获取个人基本情报")
	@GetMapping("/info")
	public AjaxResult<LawUserInfo> getInfo() throws Exception {
		return AjaxResult.success(this.userCenterService.getUserInfo(super.loginUserInfo()));
	}
	
	@ApiOperationSupport(order = 2, ignoreParameters = {"vo.group_name", "vo.group_photo"})
	@ApiOperation(value="保存个人基本情报",notes = "保存个人基本情报")
	@PutMapping("/info")
	public AjaxResult<Object> updateInfo(@RequestBody LawUserInfo vo) throws Exception {
		this.userCenterService.saveUserInfo(super.loginUserInfo(), vo);
		return AjaxResult.success();
	}
	
	@ApiOperationSupport(order = 30, ignoreParameters = { })
	@ApiOperation(value="变更密码（WEB端通用）",notes = "变更密码（WEB端通用）")
	@PutMapping("/password")
	public AjaxResult<Object> updatePwd(@RequestBody ReqUpdatePwdVo vo) throws Exception {
		this.userCenterService.savePwd(super.loginUserInfo(), vo);
		return AjaxResult.success();
	}

}
