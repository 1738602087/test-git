/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2021-02-04 09:57
 */

@ApiModel("案件检索条件")
@Data
@Accessors(chain = true)
public class ReqCaseSearchVo {
	
	@ApiModelProperty("案件名")
	private String case_name;
	
	@ApiModelProperty("物件名")
	private String estate_name;
	
	@ApiModelProperty("担当名")
	private String staff_name;
	
	@ApiModelProperty("买主名")
	private String buyer_name;
	
	@ApiModelProperty("开始日，2021-01")
	private String start_date;
	
	@ApiModelProperty("结束日，2021-01")
	private String end_date;
	
	@ApiModelProperty("1：自分担当，2：事务所担当")
	private Short type;

}
