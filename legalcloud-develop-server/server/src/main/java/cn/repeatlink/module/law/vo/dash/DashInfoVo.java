/**
 * 
 */
package cn.repeatlink.module.law.vo.dash;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-09 16:15
 */

@Data
@ApiModel("dashboard中的简要信息")
public class DashInfoVo {
	
	@ApiModelProperty("数值")
	private Integer num = 0;
	
	@ApiModelProperty("百分比数（不含%）")
	private String percent = "0";
	
	private String type;
	
	@ApiModelProperty("ID，customer：新规登录数，case：案件，law：事务所，sale：売上")
	private String id;

}
