package cn.repeatlink.module.manage.dto;

import java.util.List;

import cn.repeatlink.common.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("用户列表")
@Getter
@Setter
public class Users {
	@ApiModelProperty(value = "返回用户数",position = 0)
	private int count;
	@ApiModelProperty(value = "用户列表",position = 1)
	private List<SysUser> user;
}
