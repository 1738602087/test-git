/**
 * 
 */
package cn.repeatlink.module.law.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-07 11:00
 */

@Data
public class ReqUpdatePwdVo {
	
	@ApiModelProperty("旧密码")
	private String old_pwd;
	
	@ApiModelProperty("新密码")
	private String new_pwd;

}
