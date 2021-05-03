/**
 * 
 */
package cn.repeatlink.module.judicial.vo.order;

import java.util.Date;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-29 10:48
 */

@Data
public class ProtectOrderVo {

	private String order_id;
	
	private String order_no;
	
	private String customer_id;
	
	private String name;
	
	private Long price;
	
	private Long actual_price;
	
	private Long offset_amount;
	
	private Date start_date;
	
	private Date end_date;
	
	private Short pay_tag;
	
	private Date pay_time;
	
	private Long pay_amount;
	
	private Short status;
	
	private String remark;
	
	private Date create_time;
	
	private String created_by;
	
	private String created_by_name;
	
}
