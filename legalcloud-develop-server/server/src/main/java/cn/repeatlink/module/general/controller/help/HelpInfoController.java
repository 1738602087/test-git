/**
 * 
 */
package cn.repeatlink.module.general.controller.help;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.common.Define.ConfigKeys;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.vo.help.HelpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2021-02-02 11:23
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/help")
@Api(value = "帮助中心", produces = "application/json", tags = "帮助中心")
@ApiSort(90)
public class HelpInfoController extends BaseGeneralController {
	
	@ApiOperationSupport(order = 1)
	@GetMapping("/info")
	@ApiOperation(value = "获取帮助信息", notes = "获取帮助信息", produces = "application/json")
	public AjaxResult<HelpInfo> getHelpInfo() {
		return AjaxResult.success(new HelpInfo()
				.setLegal_name(SysConfigCacheUtil.instance().getValue(ConfigKeys.SYSTEM_HELP_INFO_LEGAL_NAME, StringUtils.EMPTY))
				.setContact_tel(SysConfigCacheUtil.instance().getValue(ConfigKeys.SYSTEM_HELP_INFO_CONTACT_TEL, StringUtils.EMPTY))
				);
	}

}
