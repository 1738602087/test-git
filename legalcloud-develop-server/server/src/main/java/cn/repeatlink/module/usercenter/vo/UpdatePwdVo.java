/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-20 15:21
 */

@Alias("updatePwdVo")
@Data
@ApiModel("修改密码请求信息")
@JsonInclude(Include.NON_NULL)
public class UpdatePwdVo {
	@ApiModelProperty("旧密码")
	private String oldpwd;
	@ApiModelProperty("新密码")
	private String newpwd;
	@ApiModelProperty("新密码确认")
	private String newpwdc;

}
