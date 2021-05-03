/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 14:55
 */
@Data
@ApiModel("密码找回请求VO")
public class RevocerPwdVo {
	
	private String email;
	
	private String verify_code;
	
	private String password;

}
