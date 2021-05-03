package cn.repeatlink.module.manage.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Alias("userInfo")
@Data
@ApiModel("用户信息")
@JsonInclude(Include.NON_NULL)
public class UserInfo {
	@ApiModelProperty("用戶的ID")
	private Long userId;
	@ApiModelProperty("登录账号")
	private String userName;
	@ApiModelProperty("用户姓名")
	private String name;
	@ApiModelProperty("邮箱地址")
	private String email;
	@ApiModelProperty("电话号码")
	private String phonenumber;
	@ApiModelProperty(value="性别,0:女性，1男性")
	private String sex;
	@ApiModelProperty("是否启用")
	private Boolean enable;
	@ApiModelProperty("状态")
	private Short status;
	@ApiModelProperty("备注")
	private  String remark;
	@ApiModelProperty("密碼")
	private String password;
	@ApiModelProperty("验证密码")
	private String confirmPassword;
	@ApiModelProperty("用户角色ID")
	private Long roleId;
	
	@JsonIgnore
	private String userType;
}
