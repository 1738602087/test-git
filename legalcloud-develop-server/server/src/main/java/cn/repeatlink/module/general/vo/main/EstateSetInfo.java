/**
 * 
 */
package cn.repeatlink.module.general.vo.main;

import java.util.List;

import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-22 17:09
 */

@Data
public class EstateSetInfo {
	
	@ApiModelProperty("物件组合ID")
	private String set_id;
	
	@ApiModelProperty("名义人")
	private List<EstateUserInfo> user_list;
	
	@ApiModelProperty("物件情报")
	private List<EstateInfo> estate_list;
	
	@ApiModelProperty("保护计划情报")
	private AutoDeductionVo deduction;

}
