/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.vo;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-11-25 17:12
 */
@Data
@Accessors(chain = true)
public class AddrInfo {
	
	private Long id;
	
	private String type;
	
	private Long pid;
	
	private String code;
	
	private String text;
	
	private String fulltext;
	
	private String tag;
	
	private String name;
	
	private String remark;
	
	private Short status;
	
	private Date date;
	
	private boolean next = false;

}
