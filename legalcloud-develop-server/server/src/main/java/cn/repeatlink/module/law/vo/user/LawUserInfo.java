/**
 * 
 */
package cn.repeatlink.module.law.vo.user;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-28 15:30
 */

@Data
@ApiModel("个人情报")
public class LawUserInfo {
	
	@ApiModelProperty("登录ID")
	private String login_id;
	
	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("性别")
	private Short gender;
	
	@ApiModelProperty("姓名")
	private String fullname;
	
	private String fullname_kana;
	
	@ApiModelProperty("组织名")
	private String group_name;
	
	@ApiModelProperty(name = "生日", example = "2020-10-10")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@ApiModelProperty("固定电话")
	private String tel;
	
	@ApiModelProperty("送信许可")
	private Boolean sendmail_flag;
	
	@ApiModelProperty("组织头像")
	private String group_photo;
	
	@ApiModelProperty("用户头像")
	private String user_photo;
	
	@JsonIgnore
	private byte[] avatar;
	
	public String getUser_photo() {
		if(StringUtils.isNotBlank(user_photo)) {
			String prefix = "data:image/png;base64,";
			if(!user_photo.startsWith("data:image/")) {
				return prefix + user_photo;
			}
			return user_photo;
		}
		return user_photo;
	}
}
