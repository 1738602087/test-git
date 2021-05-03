/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-09 16:43
 */

@Data
@ApiModel("买主个人情报")
public class CaseBuyerInfo {
	
	@ApiModelProperty("姓名")
	private String fullname;
	@ApiModelProperty("姓名（假名）")
	private String fullname_kana;
	@ApiModelProperty("性别")
	private Short gender;
	@ApiModelProperty("出生年月日")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@ApiModelProperty("住址")
	private String addr;
	
	@ApiModelProperty("ID")
	private String user_id;

}
