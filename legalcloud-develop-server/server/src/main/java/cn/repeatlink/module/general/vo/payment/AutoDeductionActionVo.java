/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-01 14:55
 */

@Data
public class AutoDeductionActionVo {
	
	@ApiModelProperty("物件组合ID")
	private String set_id;

}
