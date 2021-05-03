/**
 * 
 */
package cn.repeatlink.module.general.vo.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-20 15:15
 */

@Data
@ApiModel("用户信息")
public class UserInfo {

	private String user_id;
	
	private String famname;
	
	private String famname_kana;
	
	private String givename;
	
	private String givename_kana;

	private String fullname;

	private String fullname_kana;

	private Short gender;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	private String postcode;
	
	private String addr1;
	
	private String addr2;
	
	private String addr3;
	
	private String avatar;
	
	private String email;
	
}
