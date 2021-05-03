/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import org.apache.ibatis.type.Alias;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-20 15:03
 */

@Alias("accountInfo")
@Data
@ApiModel("账户信息")
public class AccountInfo {
	
	@ApiModelProperty("姓名")
	private String name;
	
	@ApiModelProperty("性别（0女 1男）")
	private String gender;
	
	@ApiModelProperty("邮箱")
	private String email;
	
	@ApiModelProperty("电话号码")
	private String phonenumber;
	
	@ApiModelProperty("用户类型")
	private String user_type;
}
