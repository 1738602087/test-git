/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-09 16:45
 */

@Data
@ApiModel("不动产情报")
public class CaseEstateInfo {
	
	@ApiModelProperty("物件ID")
	private String estate_id;

	@ApiModelProperty("都道府县")
	private String addr1;
	@ApiModelProperty("所在地")
	private String addr;
	@ApiModelProperty("所在地（包含code）")
	private String addr_code;
	@ApiModelProperty("所在地2")
	@JsonProperty("addr3")
	private String addr2;
	@ApiModelProperty("床面积")
	private String area;
	@ApiModelProperty("种类")
	private String type;
	@ApiModelProperty("构造")
	private String struct;
	@ApiModelProperty("家屋番号")
	private String house_id;
	@ApiModelProperty("原因")
	private String remark;
	@ApiModelProperty("不動産番号")
	private String estate_no;
	
	@ApiModelProperty("種類")
	private String category;
	
	@ApiModelProperty("是否有PDF文件，true: 表示可以下载")
	private Boolean has_pdf;
	
}
