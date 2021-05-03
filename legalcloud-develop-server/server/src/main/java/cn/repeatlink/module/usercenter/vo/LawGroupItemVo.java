/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-13 17:37
 */

@ApiModel("事务所帐号条目信息")
@Data
public class LawGroupItemVo {
	
	@ApiModelProperty("事务所ID")
	private String group_id;
	
	@ApiModelProperty("発行日時")
	private Date reg_time;
	
	@ApiModelProperty("法人名")
	private String group_name;
	
	@ApiModelProperty("主担当者")
	private String staff_name;
	
	@ApiModelProperty("ログインID")
	private String login_id;
	
	@ApiModelProperty("メールアドレス")
	private String email;
	
	@ApiModelProperty("ステータス，0：廃棄，1：利用中")
	private Short status;
	
}
