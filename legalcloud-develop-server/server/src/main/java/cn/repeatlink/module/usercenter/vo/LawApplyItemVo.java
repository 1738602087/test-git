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

@ApiModel("申请条目信息")
@Data
public class LawApplyItemVo {
	
	@ApiModelProperty("ID")
	private String id;
	
	@ApiModelProperty("申し込み日時")
	private Date apply_time;
	
	@ApiModelProperty("申し込み法人")
	private String corp_name;
	
	@ApiModelProperty("申し込み者")
	private String applicant_name;
	
	@ApiModelProperty("連絡先電話番号")
	private String applicant_phone;
	
	@ApiModelProperty("メールアドレス")
	private String applicant_email;
	
	@ApiModelProperty("ステータス，0：无效，1：審査済み，2：申し込み中，3：申し込み拒否")
	private Short status;
	
}
