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

@Alias("configInfo")
@Data
@ApiModel("配置信息")
@JsonInclude(Include.NON_NULL)
public class ConfigInfo {
	@ApiModelProperty("配置ID")
	private Integer configId;
	@ApiModelProperty("配置名称")
	private String configName;
	@ApiModelProperty("配置键名")
	private String configKey;
	@ApiModelProperty("配置键值")
	private String configValue;
	@ApiModelProperty("系统内置（Y是 N否）")
	private String configType;
	@ApiModelProperty("状态")
	private Short status;
	@ApiModelProperty("说明")
	private String remark;
}
