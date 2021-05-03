/**
 * 
 */
package cn.repeatlink.module.general.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 15:25
 */

@Data
public class UserPwdVo {

	@ApiModelProperty("旧密码")
	private String old_password;
	@ApiModelProperty("新密码")
	private String new_password;
	
}
