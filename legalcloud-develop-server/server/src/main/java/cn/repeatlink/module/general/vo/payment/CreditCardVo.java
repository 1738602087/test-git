/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 15:57
 */

@Data
@ApiModel("信用卡信息")
public class CreditCardVo {
	@ApiModelProperty("唯一ID")
	private String id;
	@ApiModelProperty("顾客CODE")
	private String customer_code;
	@ApiModelProperty("卡CODE")
	private String card_code;
	@ApiModelProperty("卡号后四位")
	private String card_no;
	@ApiModelProperty("持卡人")
	private String holder_name;
	@ApiModelProperty("卡公司")
	private String company_name;
	@ApiModelProperty("有效日期")
	private String valid_date;
	@ApiModelProperty("是否默认")
	private Boolean selected;
	
}
