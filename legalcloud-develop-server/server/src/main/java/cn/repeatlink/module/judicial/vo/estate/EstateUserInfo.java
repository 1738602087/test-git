/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estate;

import java.util.Date;
import java.util.List;

import cn.repeatlink.module.judicial.vo.user.SaleUserInfo;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-10 16:03
 */

@Data
@ApiModel("房产及名义人信息")
public class EstateUserInfo {

	@ApiModelProperty("房产ID")
	private String estate_id;
	
	@ApiModelProperty("房屋番号")
	private String house_id;
	@ApiModelProperty("所在地")
	private String addr;
	@ApiModelProperty("所在地（包含code）")
	private String addr_code;
	@ApiModelProperty("所在地2")
	@JsonProperty("addr3")
	private String addr2;
	@ApiModelProperty("种类")
	private String type;

	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_time;
	
	@ApiModelProperty("名义人列表")
	private List<SaleUserInfo> users;
	
	@ApiModelProperty("人脸识别码，用于选择房产时回传给服务器")
	private String face_code;
}
