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
 * @date 2020-09-10 15:13
 */

@Data
@ApiModel("买主信息")
public class EstatecaseBuyerVo {
	
	@ApiModelProperty("买主ID")
	private String user_id;
	
	private String user_name;
	
	@ApiModelProperty("买主姓氏")
	private String fullname;
	@ApiModelProperty("买主姓氏（片假）")
	private String fullname_kana;
	
	@ApiModelProperty(value = "出生年月日", example = "2020-09-27")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@ApiModelProperty("性别")
	private Short gender;
	
	@ApiModelProperty("邮编番号")
	private String postcode;
	
	private String addr1;

	private String addr2;
	
	private String addr3;
	
	@ApiModelProperty("买主证件图片base64数据")
	private String cert_base64;
	
	@ApiModelProperty("买主证件图片base64数据（反面）")
	private String cert_base64_back;
	
	@ApiModelProperty("买主颜情报登录状态，1已登录0未登录")
	private Short face_status;

	@ApiModelProperty("最新修改时间")
	private Date updateTime;
}
