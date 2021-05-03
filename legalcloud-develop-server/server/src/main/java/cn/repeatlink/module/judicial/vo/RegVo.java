/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 14:24
 */

@Data
@ApiModel("司法书士账号注册信息")
public class RegVo {
	
	@ApiModelProperty("邀请码")
	private String reg_code;

	@ApiModelProperty("注册邮箱")
	private String email;
	
	@ApiModelProperty("验证码")
	private String verify_code;
	
	@ApiModelProperty("登录ID")
	private String login_id;
	
	@ApiModelProperty("登录密码")
	private String password;
	
}
