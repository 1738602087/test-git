/**
 * 
 */
package cn.repeatlink.module.law.vo.fee;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-10 13:57
 */

@Data
@ApiModel("决済条目信息（退会）")
public class QuitFeeItemVo {
	@ApiModelProperty("顾客ID")
	private String user_id;
	@ApiModelProperty("登录ID")
	private String login_id;
	@ApiModelProperty("顾客名")
	private String fullname;
	@ApiModelProperty("利用开始日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start_date;
	@ApiModelProperty("退会日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date quit_date;
	@ApiModelProperty("最后决済金额")
	private Long last_money;
	@ApiModelProperty("決済金額合計")
	private Long total_money;
	@ApiModelProperty("滞纳金额")
	private Long late_fees;
	@ApiModelProperty("決済カード")
	private String card_no;
}
