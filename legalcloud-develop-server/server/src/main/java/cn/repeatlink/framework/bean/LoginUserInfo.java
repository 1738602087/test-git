/**
 * 
 */
package cn.repeatlink.framework.bean;

import cn.repeatlink.framework.common.Constant.UserType;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-12 16:18
 */

@Data
public class LoginUserInfo {

	
	private Long user_id;
	
	private UserType userType;
	
	private boolean all;
	
}
