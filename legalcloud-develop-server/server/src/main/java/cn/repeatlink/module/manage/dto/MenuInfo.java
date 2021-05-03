/**
 * 
 */
package cn.repeatlink.module.manage.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-18 10:42
 */

@Alias("menuInfo")
@Data
@ApiModel("菜单信息")
@JsonInclude(Include.NON_NULL)
public class MenuInfo {
	@ApiModelProperty("菜单ID")
	private Long menuId;
	@ApiModelProperty("菜单名称")
	@JsonProperty("name")
	private String menuName;
	@ApiModelProperty("路径")
	private String path;
	@ApiModelProperty("组件名")
	private String compoment;
	// @ApiModelProperty("路径是否为外链")
	// private Boolean isFrame;
	@ApiModelProperty("图标")
	private String icon;
	@ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
	private String menuType;
	@ApiModelProperty("父级菜单ID")
	private Long parentId;
	@ApiModelProperty("子菜单集合")
	@JsonProperty("children")
	private List<MenuInfo> subMenuList = new ArrayList<>();

}
