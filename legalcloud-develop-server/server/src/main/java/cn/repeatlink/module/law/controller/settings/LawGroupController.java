/**
 * 
 */
package cn.repeatlink.module.law.controller.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import cn.repeatlink.module.law.service.IGroupService;
import cn.repeatlink.module.law.vo.GroupSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-10 14:34
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/setting/group")
@Api(value="组织设定", tags = "设定")
@ApiSort(71)
public class LawGroupController extends BaseLawController {
	
	@Autowired
	private IGroupService groupService;
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取组织信息",notes = "获取组织信息")
	@GetMapping("/info")
	public AjaxResult<GroupSettingVo> getInfo() throws Exception {
		return AjaxResult.success(this.groupService.getGroupInfo(super.loginUserInfo().getGroup_id()));
	}

	@ApiOperationSupport(order = 2)
	@ApiOperation(value="保存组织信息",notes = "获取组织信息")
	@PutMapping("/info")
	public AjaxResult<Object> updateInfo(@RequestBody GroupSettingVo vo) throws Exception {
		this.groupService.saveGroupInfo(super.loginUserInfo().getGroup_id(), super.loginUserInfo().getGroup_id(), vo);
		return AjaxResult.success();
	}

}
