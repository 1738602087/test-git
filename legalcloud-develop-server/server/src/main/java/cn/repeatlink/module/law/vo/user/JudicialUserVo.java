/**
 * 
 */
package cn.repeatlink.module.law.vo.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-10 13:37
 */

@Data
@ApiModel("司法书士账户利用中信息")
public class JudicialUserVo {
	@ApiModelProperty("账户ID")
	private String user_id;
	@ApiModelProperty("所有者")
	private String fullname;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("开始日（注册日期）")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reg_date;
	@ApiModelProperty("状态，1：有效，0：无效")
	private Short status;
	
	@ApiModelProperty("失效日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date invalid_date;
	
	@ApiModelProperty("所有者")
	private String fullname_kana;
	
	private String famname;

	private String famname_kana;

	private String givename;

	private String givename_kana;
}
