/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-09 16:38
 */

@Data
@ApiModel("案件条目信息")
public class CaseItemVo {
	@ApiModelProperty("案件ID")
	private String case_id;
	@ApiModelProperty("案件名")
	private String case_name;
	@ApiModelProperty("开始日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start_date;
	@ApiModelProperty("案件状态（0：无效，1：有效）")
	private Short status;
	
	@ApiModelProperty("担当者")
	private String staff;
	
	@ApiModelProperty("终了日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date end_date;
	
	@ApiModelProperty("卖主验证（-1：不需要，0：未，1：已完成")
	private Short step_seller_verify;
	@ApiModelProperty("买主登录（-1：不需要，0：未，1：已完成")
	private Short step_buyer_input;
	@ApiModelProperty("取引完了（0：未，1：已完成")
	private Short step_deal_finish;
	@ApiModelProperty("登记完了（0：未，1：已完成")
	private Short step_reg_finish;
	
	@ApiModelProperty("房产信息API获取（0：未，1：已完成")
	private Short fetch_api;
	
	@ApiModelProperty("物件名集合")
	private List<String> estate_names;
	
	@ApiModelProperty("物件名集合，包含code")
	private List<String> estate_code_names;
	
	@ApiModelProperty("买主名集合")
	private List<String> buyer_names;

	@ApiModelProperty("操作权限，0：不可编辑，1：可以编辑")
	private Short operate_auth;
	
	
	@JsonIgnore
	private String _estate_names;
	@JsonIgnore
	private String _estate_code_names;
	@JsonIgnore
	private String _buyer_names;
	@JsonIgnore
	private String _staff_id;
	@JsonIgnore
	private String user_judicial_id;
}
