/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-12-08 10:40
 */

@ApiModel("物件地址信息")
@Data
public class EstateAddrVo {
	
	@ApiModelProperty("名称（显示用）")
	private String name;
	
	@ApiModelProperty("名称（显示用，包含code）")
	private String name_code;
	
	@ApiModelProperty("文本（回传用）")
	private String text;
	
	@ApiModelProperty("当前全文本")
	private String fulltext;
	
	@ApiModelProperty("编码")
	private String code;
	
	@ApiModelProperty("是否有下一级地址")
	private Boolean next;
	
}
