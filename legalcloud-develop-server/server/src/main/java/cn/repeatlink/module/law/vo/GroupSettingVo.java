/**
 * 
 */
package cn.repeatlink.module.law.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-10 14:16
 */

@Data
@ApiModel("组织信息")
public class GroupSettingVo {
	
	private String group_name;
	
	private String group_id;
	
	private String addr;
	
	private String tel;
	@ApiModelProperty("代表者")
	private String representer;
	
	private String representer_kana;
	
	private String staff;
	
	private String email;
	
	private String photo;

}
