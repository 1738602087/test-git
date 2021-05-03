/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-18 11:39
 */

@Data
@ApiModel("一般用户决済检索请求信息")
public class ReqGeneralOrderSearchVo {
	
	@ApiModelProperty("顧客名")
	private String fullname;
	
	@ApiModelProperty("所属事務所")
	private String group_name;
	
	@ApiModelProperty("支払い状態，0：未払いです，1：成功，2：失敗")
	private Short pay_status;
	
	@ApiModelProperty("ステータス，0：退会，1：利用中")
	private Short join_status;

}
