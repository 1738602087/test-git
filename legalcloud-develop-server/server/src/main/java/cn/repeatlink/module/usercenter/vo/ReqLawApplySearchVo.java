/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-13 17:38
 */

@Data
public class ReqLawApplySearchVo {
	
	@ApiModelProperty("申し込み法人")
	private String corp_name;
	
	@ApiModelProperty("申し込み者")
	private String applicant_name;
	
	@ApiModelProperty("申し込み日付, 2020-01-11")
	private String apply_date;
	
	@ApiModelProperty("ステータス，NULL：全部，1：審査済み，2：申し込み中，3：申し込み拒否")
	private Short status;

}
