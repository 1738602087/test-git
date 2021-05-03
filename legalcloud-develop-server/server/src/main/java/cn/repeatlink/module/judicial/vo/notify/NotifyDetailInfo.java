/**
 * 
 */
package cn.repeatlink.module.judicial.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-09-11 10:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("通知详细信息")
public class NotifyDetailInfo extends NotifyRowInfo {
	
	@ApiModelProperty("通知内容")
	private String content;
	

}
