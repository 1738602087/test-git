/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2021-01-13 17:37
 */

@ApiModel("申请条目信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class LawApplyDetailVo extends LawApplyItemVo {
	
	@ApiModelProperty("法人情報：代表者名")
	private String corp_rpt_name;
	
	@ApiModelProperty("法人情報：代表者名フリガナ")
	private String corp_rpt_name_kana;
	
	@ApiModelProperty("法人情報：所在地")
	private String corp_addr;
	
	@ApiModelProperty("法人情報：固定電話番号")
	private String corp_phone;
	
	@ApiModelProperty("申し込み者情報：申込者氏名フリガナ")
	private String applicant_name_kana;
	
	@ApiModelProperty("申し込み者情報：申込者所属部署")
	private String applicant_dept;
	
	@ApiModelProperty("拒否する理由")
	private String reject_reason;
	
}
