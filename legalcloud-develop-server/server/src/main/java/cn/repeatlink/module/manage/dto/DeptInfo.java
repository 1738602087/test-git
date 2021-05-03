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

@Alias("deptInfo")
@Data
@ApiModel("部门信息")
@JsonInclude(Include.NON_NULL)
public class DeptInfo {
	@ApiModelProperty("部门ID")
	private Long deptId;
	@ApiModelProperty("父级部门ID")
	private Long parentId;
	@ApiModelProperty("部门名称")
	private String deptName;
	@ApiModelProperty("祖级列表")
	private String ancestors;
	@ApiModelProperty("负责人")
	private String leader;
	@ApiModelProperty("联系电话")
	private String phone;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("删除标志（0代表存在 2代表删除）")
	private Short delFlag;
	@ApiModelProperty("状态")
	private Short status;
}
