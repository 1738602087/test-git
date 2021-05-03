/**
 * 
 */
package cn.repeatlink.framework.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-12-17 13:51
 */

@ApiModel("构建信息")
@Data
public class BuildInfo {
	
	@ApiModelProperty("构建时间")
	private String time;
	@ApiModelProperty("git提交版本号")
	private String git;
	@ApiModelProperty("项目版本号")
	private String version;

}
