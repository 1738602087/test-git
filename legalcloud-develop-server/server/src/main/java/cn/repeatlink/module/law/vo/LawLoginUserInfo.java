/**
 * 
 */
package cn.repeatlink.module.law.vo;

import cn.repeatlink.framework.bean.LoginUserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-10-12 16:21
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LawLoginUserInfo extends LoginUserInfo {
	
	private String group_id;
	
	private String judicial_user_id;

}
