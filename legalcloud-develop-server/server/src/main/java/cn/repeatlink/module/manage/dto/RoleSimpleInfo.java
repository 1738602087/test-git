/**
 * 
 */
package cn.repeatlink.module.manage.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-25 16:36
 */

@Alias("roleSimpleInfo")
@Data
@ApiModel("角色简易信息")
@JsonInclude(Include.NON_NULL)
public class RoleSimpleInfo {
	@ApiModelProperty("角色的ID")
	private Long roleId;
	@ApiModelProperty("角色名称")
	private String roleName;
}
