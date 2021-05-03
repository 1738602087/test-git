/**
 * 
 */
package cn.repeatlink.framework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.hutool.setting.dialect.Props;
import cn.repeatlink.framework.bean.BuildInfo;
import cn.repeatlink.framework.common.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-12-17 13:05
 */

@RestController
@RequestMapping("/buildinfo")
@Api(value="其他", tags = "其他")
@ApiSort(1)
public class BuildInfoController {
	
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="获取项目构建信息",notes = "获取项目构建信息")
	@GetMapping
	public AjaxResult<BuildInfo> getInfo() {
		Props prop = Props.getProp("build.properties", "UTF-8");
		return AjaxResult.success(prop.toBean(BuildInfo.class));
	}

}
