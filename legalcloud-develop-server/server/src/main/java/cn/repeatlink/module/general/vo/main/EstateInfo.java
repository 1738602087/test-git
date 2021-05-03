/**
 * 
 */
package cn.repeatlink.module.general.vo.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-22 11:13
 */

@Data
@ApiModel("房产信息")
public class EstateInfo {
	
	@ApiModelProperty("房产ID")
	private String estate_id;
	
	@ApiModelProperty("家屋番号")
	private String house_id;
	
	@ApiModelProperty("種別")
	private String type;

	@ApiModelProperty("不動産番号")
	private String estate_no;
	
	@ApiModelProperty("種類")
	private String category;
	
	@ApiModelProperty("床面积")
	private String area;
	
	@ApiModelProperty("构造")
	private String struct;
	
	@ApiModelProperty("原因及びその日付〔登記の日付〕")
	private String record;
	
	@ApiModelProperty("所在地")
	private String addr;
	
	@ApiModelProperty("所在地（包含code）")
	private String addr_code;
	
	@ApiModelProperty("所在地2")
	@JsonProperty("addr3")
	private String addr2;
	
	@ApiModelProperty("是否有PDF文件，true: 表示可以下载")
	private Boolean has_pdf;
	
	@JsonIgnore
	private String _set_id;
}
