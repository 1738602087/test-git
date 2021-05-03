/**
 * 
 */
package cn.repeatlink.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-20 13:35
 */

@Data
public class PageMoreBaseVo {

	@JsonIgnore
	private String _orderStr;
	
}
