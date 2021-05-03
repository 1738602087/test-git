/**
 * 
 */
package cn.repeatlink.module.general.vo.main;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-22 13:23
 */

@Data
@ApiModel("名义人信息")
public class EstateUserInfo {

	@ApiModelProperty("ID")
	private String user_id;
	
	@ApiModelProperty("姓名")
	private String fullname;
	
	@ApiModelProperty("姓名")
	private String fullname_kana;
	
	@ApiModelProperty("性别")
	private Short gender;
	
	@ApiModelProperty("地址")
	private String addr;
	
	@ApiModelProperty(value = "出生年月日", example = "2020-10-22")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@ApiModelProperty("颜情报登录状态，0：未登录，1：已登录")
	private Short face_flag;
	
	@ApiModelProperty("颜情报最新更新日")
	private Date face_update_time;
	
}
