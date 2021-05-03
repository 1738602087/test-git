/**
 * 
 */
package cn.repeatlink.module.judicial.vo.order;

import java.util.Date;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-29 13:38
 */

@Data
public class OmiseResultVo {

	private Short status;
	
	private Long amount;
	
	private String deduction_orderno;
	
	private String origin_deduction_orderno;
	
	private Date deduction_date;
	
	private Date deduction_time;
	
	private String deduction_error_code;
	private String deduction_error_msg;
	
}
