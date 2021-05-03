/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 15:57
 */

@Data
@ApiModel("信用卡token信息")
public class CreditCardTokenVo {
	@ApiModelProperty("卡token")
	private String token;
}
