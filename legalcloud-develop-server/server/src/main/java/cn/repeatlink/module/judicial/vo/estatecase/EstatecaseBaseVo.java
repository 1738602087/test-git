/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estatecase;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.repeatlink.common.bean.PageMoreBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-09-10 15:03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("案件基本信息")
public class EstatecaseBaseVo extends PageMoreBaseVo {

	@ApiModelProperty("案件ID")
	private String case_id;
	@ApiModelProperty("案件名")
	private String case_name;
	@ApiModelProperty("担当者")
	private String assigned_to;
	@ApiModelProperty(value = "开始日", example = "2020-09-09")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start_date;
	
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
	
	@ApiModelProperty(value = "创建时间", example = "2020-09-09 12:12")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date create_time;
	
	@ApiModelProperty(value = "完成时间", example = "2020-09-09 12:12")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date complete_time;
	
	@ApiModelProperty("房产信息")
	private List<EstateBaseVo> estate_list;
	
	@ApiModelProperty("操作权限，0：不可编辑，1：可以编辑")
	private Short operate_auth;
	
	@JsonIgnore
	private String user_judicial_id;
	
}
