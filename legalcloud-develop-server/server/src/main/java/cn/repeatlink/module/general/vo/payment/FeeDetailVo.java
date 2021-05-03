/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-10-20 16:28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class FeeDetailVo extends FeeItemVo {
	
	private String card_no;
	
	private String credit_company_name;

}
