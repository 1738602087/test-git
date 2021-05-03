/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import java.util.Date;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-11-23 13:59
 */

@Data
public class CaseUserInfoRecordVo {
	
	private String name;
	
	private String before_value;
	
	private String after_value;
	
	private Date time;

}
