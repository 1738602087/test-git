package cn.repeatlink.module.manage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.manage.dto.PostcodeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/postcode")
@Api(value = "邮编管理", produces = "application/json", tags = "邮编管理接口")
public class PostcodeController {

	@GetMapping("/{postcode}")
	@ApiOperation(value = "邮编檢索", notes = "邮编檢索", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "postcode", value = "完整邮编")
	public AjaxResult<PostcodeInfo> getAll(@PathVariable("postcode") String postcode) {
		Record record = Db.findFirst("select * from postcode where postcode = ?", postcode);
		PostcodeInfo info = null;
		if(record != null) {
			info = new PostcodeInfo();
			info.setPostcode(postcode);
			info.setAddr1(record.getStr("addr1"));
			info.setAddr1(record.getStr("addr2"));
			info.setAddr1(record.getStr("addr3"));
		}
		return AjaxResult.success(info);
	}
	
	
}
