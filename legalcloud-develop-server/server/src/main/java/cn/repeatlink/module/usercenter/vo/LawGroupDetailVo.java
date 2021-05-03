/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-13 17:37
 */

@ApiModel("事务所信息")
@Data
public class LawGroupDetailVo {
	
	@ApiModelProperty("事务所ID")
	private String group_id;
	
	@ApiModelProperty("法人情報：法人名")
	private String corp_name;
	
	@ApiModelProperty("法人情報：代表者名")
	private String corp_rpt_name;
	
	@ApiModelProperty("法人情報：代表者名フリガナ")
	private String corp_rpt_name_kana;
	
	@ApiModelProperty("法人情報：所在地")
	private String corp_addr;
	
	@ApiModelProperty("法人情報：固定電話番号")
	private String corp_phone;
	
	@ApiModelProperty("主担当者情報：担当者氏名")
	private String applicant_name;
	
	@ApiModelProperty("主担当者情報：連絡先電話番号")
	private String applicant_phone;
	
	@ApiModelProperty("主担当者情報：メールアドレス")
	private String applicant_email;
	
	@ApiModelProperty("主担当者情報：担当者フリガナ")
	private String applicant_name_kana;
	
	@ApiModelProperty("主担当者情報：担当者所属部署")
	private String applicant_dept;
	
	@ApiModelProperty("ログインID")
	private String login_id;
	
	@ApiModelProperty("ステータス，0：廃棄，1：利用中")
	private Short status;
	
}
