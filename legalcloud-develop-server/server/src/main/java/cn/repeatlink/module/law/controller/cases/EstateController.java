/**
 * 
 */
package cn.repeatlink.module.law.controller.cases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.judicial.service.IEstateAddrService;
import cn.repeatlink.module.judicial.service.IEstateService;
import cn.repeatlink.module.judicial.service.IFaceService;
import cn.repeatlink.module.judicial.vo.estate.EstateAddrTagVo;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.law.controller.BaseLawController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-11 10:05
 */

@RestController("lawEstateController")
@RequestMapping(Define.APP_URL_PREFIX + "/estate")
@Api(value="不动产管理", tags = "不动产")
@ApiSort(4)
public class EstateController extends BaseLawController {
	
	@Autowired
	private IEstateService estateService;
	
	@Autowired
	private IEstateAddrService estateAddrService;
	
	@Autowired
	private IFaceService faceService;

	@ApiOperationSupport(order = 1, ignoreParameters = {"vo.estate_id", "vo.sellers", "vo.face_code","vo.create_time"})
	@ApiOperation(value="输入检索房产",notes = "通过输入房产信息进行检索房产")
	@PostMapping("/search")
	public AjaxResult<List<EstateUserInfo>> searchByInput(@RequestBody EstateUserInfo vo){
		return AjaxResult.success(this.estateService.getEstateInfoList(vo));
	}
	
	@ApiOperationSupport(order = 2)
	@ApiOperation(value="获取某房产信息",notes = "获取某房产信息")
	@ApiImplicitParams({
		@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "estate_id", value = "房产ID")
	})
	@GetMapping("/info/{estate_id}")
	public AjaxResult<EstateUserInfo> searchById(@PathVariable("estate_id")String estateId){
		return AjaxResult.success(this.estateService.getEstateInfo(estateId));
	}
	
	
	
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value="人脸识别检索房产",notes = "通过人脸进行识别出房主检索其名下房产")
//	@PostMapping("/search")
//	public AjaxResult<List<EstateUserInfo>> searchByFace(@RequestBody ListPicVo vo){
//		String faceId = this.faceService.searchFaceByImage(vo.getPic_base64_list().get(0));
//		return AjaxResult.success(this.estateService.getEstateInfoListByUserFaceId(faceId));
//	}
//	
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value="OCR识别房产信息",notes = "OCR识别房产信息")
//	@PostMapping("/ocr/info")
//	public AjaxResult<EstateBaseVo> ocrEstate(@RequestBody SinglePicVo vo){
//		return AjaxResult.success(null);
//	}
	
	@ApiOperationSupport(order = 10)
	@ApiOperation(value="获取不动产地址区域信息(都道府县)",notes = "获取不动产地址区域")
	@GetMapping("/addr/area")
	public AjaxResult<List<EstateAddrTagVo>> getAllAreas(){
		return AjaxResult.success(this.estateAddrService.getAllAreas());
	}
	
	@ApiOperationSupport(order = 11)
	@ApiOperation(value="获取不动产下一级地址",notes = "获取不动产下一级地址")
	@GetMapping("/addr/sub")
	public AjaxResult<List<EstateAddrTagVo>> getNextAddrs(@RequestParam("code") String code, @RequestParam("text") String text, @RequestParam("fulltext") String fulltext){
		return AjaxResult.success(this.estateAddrService.getNext(code, text, fulltext));
	}
	
}
