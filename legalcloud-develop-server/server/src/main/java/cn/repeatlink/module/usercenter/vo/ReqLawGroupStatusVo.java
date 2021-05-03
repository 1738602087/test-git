/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-13 17:37
 */

@Data
public class ReqLawGroupStatusVo {
	
	@ApiModelProperty("ステータス，0：无效，1：复活")
	private Short status;
	
}
