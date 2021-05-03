/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estatecase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-20 11:33
 */

@Data
public class CaseSearchVo {
	
	@ApiModelProperty("案件名")
	private String case_name;
	
	@ApiModelProperty("担当名")
	private String staff_name;
	
	@ApiModelProperty("卖主名")
	private String seller_name;
	
	@ApiModelProperty("买主名")
	private String buyer_name;
	
	@ApiModelProperty("创建年月，例：2021-01")
	private String create_time;
	
	@ApiModelProperty("完成年月，例：2021-01")
	private String complete_time;
	
	@ApiModelProperty("1：自分担当，2：事务所担当")
	private Short type;
	
	@ApiModelProperty("1：对应中，2：已完了")
	private Short state;
	
	@ApiModelProperty("排序字段，create_time：创建时间，complete_time：完成时间，case_name：案件名")
	private String order_field;
	
	@ApiModelProperty("排序方式，asc：升序，desc：降序")
	private String order_dir;
	
	/* 兼容旧版本 */
	private Short operate_auth;
	private Short step_reg_finish;
	private Short status;

}
