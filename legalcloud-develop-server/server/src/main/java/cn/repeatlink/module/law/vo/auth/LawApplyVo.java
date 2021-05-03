/**
 * 
 */
package cn.repeatlink.module.law.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2021-01-13 17:16
 */

@ApiModel("発行申請信息")
@Data
@Accessors(chain = true)
public class LawApplyVo {
	
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
	
	@ApiModelProperty("申し込み者情報：申込者氏名")
	private String applicant_name;
	
	@ApiModelProperty("申し込み者情報：申込者氏名フリガナ")
	private String applicant_name_kana;
	
	@ApiModelProperty("申し込み者情報：申込者所属部署")
	private String applicant_dept;
	
	@ApiModelProperty("申し込み者情報：連絡先電話番号")
	private String applicant_phone;
	
	@ApiModelProperty("申し込み者情報：メールアドレス")
	private String applicant_email;
	
}
