/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-18 14:04
 */

@ApiModel("扣费记录信息")
@Data
public class GeneralDeductRecordVo {
	
	@ApiModelProperty("決済日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deduct_time;
	
	@ApiModelProperty("決済金額")
	private Long amount;
	
	@ApiModelProperty("決済カード（后4位）")
	private String card_no;
	
	@ApiModelProperty("決済状態，0：失敗，1：成功")
	private Short status;

}
