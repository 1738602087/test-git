package cn.repeatlink.module.judicial.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.service.IUserFaceService;
import cn.repeatlink.module.judicial.vo.SinglePicVo;
import cn.repeatlink.module.judicial.vo.face.FaceCodeInfo;
import cn.repeatlink.module.judicial.vo.user.FaceUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/userface")
@Api(value = "人脸相关", produces = "application/json", tags = "人脸相关接口")
@ApiSort(20)
public class JudicialrFaceUseController extends BaseJudicialController {
	
	@Autowired
	private IUserFaceService userFaceService;
	
	@ApiOperationSupport(order = 1)
	@PostMapping("/detect")
	@ApiOperation(value = "识别人脸", notes = "识别人脸，返回用户信息", produces = "application/json")
	public AjaxResult<FaceUserVo> detectFace(@RequestBody SinglePicVo vo) {
		FaceUserVo data = this.userFaceService.getUserByImage(vo.getPic_base64());
		if(data != null && StringUtils.isNotBlank(vo.getUse_type())) {
			data.setFace_code_info(new FaceCodeInfo().setUse_type(vo.getUse_type()).setBase64data(vo.getPic_base64()));
			data.setFace_code_info(this.userFaceService.buildFaceCode(data));
		}
		return AjaxResult.success(data);
	}
	
}
