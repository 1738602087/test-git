/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estatecase;

import java.util.List;

import cn.repeatlink.module.judicial.vo.face.FaceCodeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 15:13
 */

@Data
@ApiModel("卖主信息")
public class EstatecaseSellerVo {
	
	@ApiModelProperty("卖主ID")
	private String user_id;
	
	@ApiModelProperty("卖主人脸图片base64数据集合（此字段不再使用，使用face_code_info代替）")
	private List<String> pic_base64_list;
	
	@ApiModelProperty("验证状态，1通过0未通过")
	private Short verify;
	
	@ApiModelProperty("卖主姓氏")
	private String fullname;
	
	@ApiModelProperty("卖主姓氏（片假）")
	private String fullname_kana;
	
	@ApiModelProperty("性别")
	private Short gender;
	
	@ApiModelProperty("人脸识别码信息")
	private FaceCodeInfo face_code_info;
}
