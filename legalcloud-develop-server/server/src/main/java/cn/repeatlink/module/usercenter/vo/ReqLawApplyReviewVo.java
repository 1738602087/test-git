/**
 * 
 */
package cn.repeatlink.module.usercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2021-01-14 09:49
 */

@ApiModel("审核申请请求信息")
@Data
@Accessors(chain = true)
public class ReqLawApplyReviewVo {

	@ApiModelProperty("状态，1：承認，3：拒否")
	private Short state;
	
	@ApiModelProperty("拒否する理由")
	private String reject_reason;
	
}
