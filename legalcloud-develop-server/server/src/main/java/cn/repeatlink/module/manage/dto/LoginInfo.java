/**
 * 
 */
package cn.repeatlink.module.manage.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-18 14:35
 */

@Alias("loginInfo")
@Data
@ApiModel("登录信息")
@JsonInclude(Include.NON_NULL)
public class LoginInfo {
	@ApiModelProperty("登录ID")
	private Long infoId;
	@ApiModelProperty("用户账号")
	private String userName;
	@ApiModelProperty("登录IP")
	private String ipaddr;
	@ApiModelProperty("登录地点")
	private String loginLocation;
	@ApiModelProperty("浏览器")
	private String browser;
	@ApiModelProperty("操作系统")
	private String os;
	@ApiModelProperty("状态")
	private String msg;
	@ApiModelProperty("提示消息")
	private Short status;
	@ApiModelProperty("登录时间")
	private String loginTime;
}
