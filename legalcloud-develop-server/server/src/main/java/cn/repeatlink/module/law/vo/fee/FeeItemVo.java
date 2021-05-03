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
 * @date 2020-10-10 14:13
 */

@Data
@ApiModel("决済信息")
public class FeeItemVo {
	@ApiModelProperty("费用ID")
	private String fee_id;
	@ApiModelProperty("决済日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	@ApiModelProperty("信用卡号")
	private String cardno;
	@ApiModelProperty("状态，5：成功，4：失败，其他：未缴纳")
	private Short status;
	@ApiModelProperty("金额")
	private Long money;
	@ApiModelProperty("信用卡公司")
	private String credit_company_name;
	
}
