/**
 * 
 */
package cn.repeatlink.module.judicial.vo.order;

import java.util.Date;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-29 10:45
 */

@Data
public class ProtectPlanVo {
	
	private String name;
	
	private Long price;
	
	private Long actualPrice;
	
	private Date startDate;
	
	private Date endDate;
	
	private String remark;

}
