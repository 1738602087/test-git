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
public class ReqLawGroupSearchVo {
	
	@ApiModelProperty("法人名")
	private String group_name;
	
	@ApiModelProperty("主担当者名")
	private String staff_name;
	
	@ApiModelProperty("発行日付, 2020-01-11")
	private String reg_date;
	
	@ApiModelProperty("ステータス，NULL：全部，0：廃棄，1：利用中")
	private Short status;

}
