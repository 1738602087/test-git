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

@Alias("dictDataInfo")
@Data
@ApiModel("字典信息")
@JsonInclude(Include.NON_NULL)
public class DictDataInfo {
	@ApiModelProperty("字典编码")
	private Long dictCode;
	@ApiModelProperty("字典排序")
	private Integer dictSort;
	@ApiModelProperty("字典标签")
	private String dictLabel;
	@ApiModelProperty("配置键值")
	private String dictValue;
	@ApiModelProperty("字典类型")
	private String dictType;
	@ApiModelProperty("样式属性")
	private String cssClass;
	@ApiModelProperty("表格回显样式")
	private String listClass;
	@ApiModelProperty("是否默认（Y是 N否）")
	private String isDefault;
	@ApiModelProperty("状态")
	private Short status;
	@ApiModelProperty("说明")
	private String remark;
}
