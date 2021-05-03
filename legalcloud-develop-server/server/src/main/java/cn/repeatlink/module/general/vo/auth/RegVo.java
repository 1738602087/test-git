/**
 * 
 */
package cn.repeatlink.module.general.vo.auth;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-27 14:40
 */

@Data
public class RegVo {
	
	private String target;
	
	private String login_id;
	
	private String password;
	
	private String verify_code;
	
	private String email;

}
