/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 16:35
 */

@Data
@ApiModel("房产信息")
public class EstateBaseVo {
	
	@JsonIgnore
	private String record_id;

	@ApiModelProperty("房产ID")
	private String estate_id;
	
	@ApiModelProperty("房屋番号")
	private String house_id;
	
	@ApiModelProperty("所在地，源编码字符串")
	private String addr;
	
	@ApiModelProperty("所在地，可能包含code")
	private String addr_code;
	
	@ApiModelProperty("所在地2")
	@JsonProperty("addr3")
	private String addr2;
	
	@ApiModelProperty("都道府县")
	private String addr1;
	
	@ApiModelProperty("種別")
	private String type;
	
	@ApiModelProperty("床面积")
	private String area;
	
	@ApiModelProperty("构造")
	private String struct;
	
	@ApiModelProperty("原因及びその日付〔登記の日付〕")
	private String record;
	
	@ApiModelProperty("不動産番号")
	private String estate_no;
	
	@ApiModelProperty("種類")
	private String category;
	
	@ApiModelProperty("是否有PDF文件，true: 表示可以下载")
	private Boolean has_pdf;
}
