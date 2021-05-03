/**
 * 
 */
package cn.repeatlink.module.manage.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-08-18 14:35
 */

@Alias("jobLogInfo")
@Data
@ApiModel("任务执行日志信息")
@JsonInclude(Include.NON_NULL)
public class JobLogInfo {
	@ApiModelProperty("任务日志ID")
	private Long jobLogId;
	@ApiModelProperty("任务ID")
	private Long jobId;
	@ApiModelProperty("任务名称")
	private String jobName;
	@ApiModelProperty("任务组名")
	private String jobGroup;
	@ApiModelProperty("调用目标")
	private String invokeTarget;
	@ApiModelProperty("日志信息")
	private String jobMessage;
	@ApiModelProperty("异常信息")
	private String exceptionInfo;
	@ApiModelProperty("执行状态（0正常 1失败）")
	private Short status;
	@ApiModelProperty("创建时间")
	private String createTime;
	@ApiModelProperty("结束时间")
	private String endTime;
}
