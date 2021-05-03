/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estatecase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-03 13:48
 */

@ApiModel("买主情报变更记录")
@Data
public class BuyerInfoChangeItemVo {
	
	@ApiModelProperty("ID")
	private String id;
	
	@ApiModelProperty("字段名")
	private String field_name;
	
	@ApiModelProperty("变更前的值")
	private String before_value;
	
	@ApiModelProperty("变更后的值")
	private String after_value;
	
	@ApiModelProperty("变更时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date action_time;

}
