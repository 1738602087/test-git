/**
 * 
 */
package cn.repeatlink.module.judicial.vo.notify;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-09-11 10:23
 */

@Data
@ApiModel("通知列表项信息")
public class NotifyRowInfo {
	
	@ApiModelProperty("通知ID")
	private String notify_id;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("发送者")
	private String sender;
	@ApiModelProperty("发送时间")
	private Date send_time;
	@ApiModelProperty("状态，0无效1已读2未读")
	private Short status;

}
