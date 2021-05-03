/**
 * 
 */
package cn.repeatlink.module.general.vo.main;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-22 11:12
 */

@Data
@ApiModel("首页数据")
public class MainData {
	
	@ApiModelProperty("状态，0：没有案件，1：初回案件处理中，2：生体情报已登录，99：其他")
	private Integer status;
	
	@ApiModelProperty("进行中案件")
	private List<CaseInfo> case_list;
	
	@ApiModelProperty("物件组合情报")
	private List<EstateSetInfo> estate_list;

}
