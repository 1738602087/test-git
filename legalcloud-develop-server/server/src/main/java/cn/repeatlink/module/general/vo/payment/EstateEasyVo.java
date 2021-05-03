/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-22 16:59
 */

@Data
public class EstateEasyVo {
	
	@ApiModelProperty("物件ID")
	private String estate_id;
	
	@ApiModelProperty("物件所在地")
	private String estate_addr;

}
