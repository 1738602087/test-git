/**
 * 
 */
package cn.repeatlink.module.judicial.vo.user;

import cn.repeatlink.module.judicial.vo.face.FaceCodeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-11 10:13
 */

@Data
public class FaceUserVo {

	private String user_id;
	
	private String fullname;
	
	private String fullname_kana;
	
	private Short gender;
	
	private String birthday;
	
	private String addr;
	
	@ApiModelProperty("此次人脸识别标识码信息，用于回传使用，5分钟内有效")
	private FaceCodeInfo face_code_info;
	
}
