/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estate;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-12-08 10:37
 */

@ApiModel("物件地址标签")
@Data
public class EstateAddrTagVo {
	
	@ApiModelProperty("标签")
	private String tag;
	
	@ApiModelProperty("地址集合")
	private List<EstateAddrVo> addrs;

}
