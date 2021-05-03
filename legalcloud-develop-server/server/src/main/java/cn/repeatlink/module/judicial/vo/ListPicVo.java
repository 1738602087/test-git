/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-11 10:39
 * 
 */

@Data
@ApiModel("人脸识别图片数据")
public class ListPicVo {

	private List<String> pic_base64_list;
	
}
