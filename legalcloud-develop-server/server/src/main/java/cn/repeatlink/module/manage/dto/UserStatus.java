package cn.repeatlink.module.manage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserStatus {
	@ApiModelProperty("用户信息")
	private Long  userId;
	@ApiModelProperty("用户状态 0：不可用,1:可用")
	private Short enable;
	
}
