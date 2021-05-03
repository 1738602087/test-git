/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-12-01 10:21
 */

@Data
public class SendCodeVo {
	@ApiModelProperty("目标")
	private String target;
}
