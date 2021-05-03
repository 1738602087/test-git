/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 16:35
 */

@Data
public class AutoDeductionVo {
	
	@ApiModelProperty("组合ID")
	private String set_id;
	
	@ApiModelProperty("标题")
	private String title;
	
	@ApiModelProperty("备注")
	private String remark;
	
	@ApiModelProperty("金额")
	private Long money;
	
	@ApiModelProperty("物件列表")
	private List<EstateEasyVo> estate_list;

	@ApiModelProperty("合同日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date contract_date;
	
	@ApiModelProperty("下一次扣款日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date next_deduct_date;
	
	@ApiModelProperty("终止日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date quit_date;
	
	@ApiModelProperty("状态，1：已加入，0：未加入")
	private Short status;
	
	@ApiModelProperty("决済卡号")
	private String card_no;
	
	@ApiModelProperty("支付人")
	private String holder_name;
	
	@ApiModelProperty("是否只显示")
	private Boolean only_show;
	
}
