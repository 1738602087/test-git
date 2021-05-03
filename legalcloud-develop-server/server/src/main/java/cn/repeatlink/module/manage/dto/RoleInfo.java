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
 * @date 2020-08-17 14:01
 */

@Alias("roleInfo")
@Data
@ApiModel("角色信息")
@JsonInclude(Include.NON_NULL)
public class RoleInfo {
	
	@ApiModelProperty("角色的ID")
	private Long roleId;
	@ApiModelProperty("角色名称")
	private String roleName;
	@ApiModelProperty("角色权限字符串")
	private String roleKey;
	@ApiModelProperty("显示顺序")
	private Integer roleSort;
//	@ApiModelProperty("角色数据权限范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
//	private String dataScope;
	@ApiModelProperty("状态")
	private Short status;
	@ApiModelProperty("最后更新时间")
	private String updateTime;
	@ApiModelProperty("创建时间")
	private String createTime;
	@ApiModelProperty("菜单ID集合")
	private List<Long> menuIds;
}
