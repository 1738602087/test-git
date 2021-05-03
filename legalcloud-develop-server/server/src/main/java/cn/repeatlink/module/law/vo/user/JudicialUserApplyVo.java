/**
 * 
 */
package cn.repeatlink.module.law.vo.user;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-10 13:37
 */

@Data
@ApiModel("司法书士账户申请中信息")
public class JudicialUserApplyVo {
	@ApiModelProperty("ID")
	private String id;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("送信时间")
	private Date send_time;
}
