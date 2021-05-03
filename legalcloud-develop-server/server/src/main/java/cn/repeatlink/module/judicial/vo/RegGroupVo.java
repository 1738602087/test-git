/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-15 14:23
 */

@Data
@ApiModel("组织信息")
public class RegGroupVo {
	@ApiModelProperty("组织名")
	private String group_name;
	@ApiModelProperty("所在地")
	private String addr;
	@ApiModelProperty("授权注册邮箱")
	private String email;
}
