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

@Alias("operLogInfo")
@Data
@ApiModel("操作日志信息")
@JsonInclude(Include.NON_NULL)
public class OperLogInfo {
	@ApiModelProperty("ID")
	private Long id;
	@ApiModelProperty("请求方式")
	private String requestMethod;
	@ApiModelProperty("操作人员ID")
	private Long operId;
	@ApiModelProperty("操作人员")
	private String operName;
	@ApiModelProperty("请求URL")
	private String operUrl;
	@ApiModelProperty("主机IP")
	private String operIp;
	@ApiModelProperty("操作地点")
	private String operLocation;
	@ApiModelProperty("执行方法")
	private String method;
	@ApiModelProperty("操作状态（1成功 2业务异常 3内部错误 4无权访问 5禁止访问）")
	private String status;
	@ApiModelProperty("响应状态")
	private String responseCode;
	@ApiModelProperty("操作时间")
	private String operTime;
	@ApiModelProperty("处理时长")
	private Integer cost;
	@ApiModelProperty("请求参数")
	private String operParam;
	@ApiModelProperty("返回结果")
	private String jsonResult;
	@ApiModelProperty("错误消息")
	private String errorMsg;
}
