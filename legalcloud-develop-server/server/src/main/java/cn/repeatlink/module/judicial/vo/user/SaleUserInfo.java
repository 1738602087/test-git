/**
 * 
 */
package cn.repeatlink.module.judicial.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 16:05
 */

@Data
@ApiModel("案件交易用户信息")
public class SaleUserInfo {
	
	@ApiModelProperty("用户ID")
	private String user_id;
	
	@ApiModelProperty("用户名")
	private String fullname;
	@ApiModelProperty("用户名（片假）")
	private String fullname_kana;
	@ApiModelProperty("性别，0女1男2未知")
	private Short gender;
	@ApiModelProperty("住址")
	private String addr;
	@ApiModelProperty("验证状态，1通过0未通过")
	private Short verify;

}
