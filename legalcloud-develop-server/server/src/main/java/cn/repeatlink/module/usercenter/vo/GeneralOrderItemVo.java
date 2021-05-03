/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-18 11:31
 */

@ApiModel("一般用户决済条目信息")
@Data
public class GeneralOrderItemVo {
	
	@ApiModelProperty("一般用户ID")
	private String user_id;
	
	@ApiModelProperty("顧客名")
	private String user_fullname;
	
	@ApiModelProperty("携帯番号")
	private String user_tel;
	
	@ApiModelProperty("メールアドレス")
	private String user_email;
	
	@ApiModelProperty("最終決済日")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date last_deduct_time;
	
	@ApiModelProperty("所属事務所")
	private String law_group_name;
	
	@ApiModelProperty("ステータス，0：退会，1：利用中")
	private Short status;
	
	@ApiModelProperty("決済総金額")
	private Long total_amount;
	
	@ApiModelProperty("滞納額")
	private Long nonpay_amount;
	

}
