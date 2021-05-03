/**
 * 
 */
package cn.repeatlink.module.general.vo.main;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-22 11:12
 */

@Data
@ApiModel("案件信息")
public class CaseInfo {
	
	@ApiModelProperty("案件ID")
	private String case_id;
	
	@ApiModelProperty("开始日")
	private Date start_date;
	
	@ApiModelProperty("担当者")
	private String staff_name;
	
	@ApiModelProperty("案件名")
	private String case_name;
	
	@ApiModelProperty("卖主验证（0：未，1：已完成")
	private Short step_seller_verify;
	@ApiModelProperty("买主登录（0：未，1：已完成")
	private Short step_buyer_input;
	@ApiModelProperty("取引完了（0：未，1：已完成")
	private Short step_deal_finish;
	@ApiModelProperty("登记完了（0：未，1：已完成")
	private Short step_reg_finish;
	
	@ApiModelProperty("房产信息API获取（0：未，1：已完成")
	private Short fetch_api;
	
	
	@ApiModelProperty("案件状态（0：无效，1：有效")
	private Short status;

}
