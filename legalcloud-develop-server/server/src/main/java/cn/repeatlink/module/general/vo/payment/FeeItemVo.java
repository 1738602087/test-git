/**
 * 
 */
package cn.repeatlink.module.general.vo.payment;

import java.util.Date;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 16:03
 */

@Data
public class FeeItemVo {

	private String fee_id;
	
	private Date time;
	
	private Long money;
	
	private String title;
	
}
