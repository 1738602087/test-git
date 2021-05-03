/**
 * 
 */
package cn.repeatlink.module.judicial.vo.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-27 15:02
 */

@Data
public class BuyerUserInfo {
	
	private String user_id;
	
	private String fullname;
	
	private String fullname_kana;
	
	private String postcode;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	private String addr1;
	
	private String addr2;
	
	private String addr3;
	
	private Short gender;

}
