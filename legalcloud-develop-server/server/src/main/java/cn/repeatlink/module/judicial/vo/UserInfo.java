/**
 * 
 */
package cn.repeatlink.module.judicial.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-11 10:41
 */

@Data
@ApiModel("个人情报")
public class UserInfo {

	private String famname;

	private String famname_kana;

	private String givename;

	private String givename_kana;

	private String fullname;
	
	private String fullname_kana;
	
	private String login_id;
	
	private String addr1;
	
	private String addr2;
	
	private String addr3;
	
	private Short gender;
	
	private String email;
	
	private String group;
	
	private String birthday;
	
	private String avatar;

}
