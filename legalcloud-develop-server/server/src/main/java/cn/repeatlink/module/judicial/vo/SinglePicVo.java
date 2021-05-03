/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-11 10:39
 */

@Data
public class SinglePicVo {

	private String pic_base64;
	
	@ApiModelProperty("人脸识别用途，传值后将返回CODE")
	private String use_type;
	
}
