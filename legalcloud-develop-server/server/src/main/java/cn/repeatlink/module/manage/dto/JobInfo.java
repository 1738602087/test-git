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
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-08-18 14:35
 */

@Alias("jobInfo")
@Data
@Accessors(chain = true)
@ApiModel("任务信息")
@JsonInclude(Include.NON_NULL)
public class JobInfo {
	@ApiModelProperty("任务ID")
	private Long jobId;
	@ApiModelProperty("任务名称")
	private String jobName;
	@ApiModelProperty("任务组名")
	private String jobGroup;
	@ApiModelProperty("调用目标")
	private String invokeTarget;
	@ApiModelProperty("cron执行表达式")
	private String cronExpression;
	@ApiModelProperty("计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
	private String misfirePolicy;
	@ApiModelProperty("是否并发执行（0允许 1禁止）")
	private String concurrent;
	@ApiModelProperty("状态")
	private Short status;
	@ApiModelProperty("说明")
	private String remark;
}
