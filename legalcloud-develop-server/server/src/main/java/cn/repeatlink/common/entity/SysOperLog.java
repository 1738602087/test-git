package cn.repeatlink.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("日誌記錄")
public class SysOperLog implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.id
     *
     * @mbg.generated
     */
	@ApiModelProperty("记录ID")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.request_method
     *
     * @mbg.generated
     */
	@ApiModelProperty("HTTP请求方法")
    private String requestMethod;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_id
     *
     * @mbg.generated
     */
	@ApiModelProperty("操作者")
    private Long operId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_name
     *
     * @mbg.generated
     */
	@ApiModelProperty("操作者名")
    private String operName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_url
     *
     * @mbg.generated
     */
	@ApiModelProperty("请求路径")
    private String operUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_ip
     *
     * @mbg.generated
     */
	@ApiModelProperty("操作者IP")
    private String operIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_location
     *
     * @mbg.generated
     */
    private String operLocation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.method
     *
     * @mbg.generated
     */
    @ApiModelProperty("後端处理方法")
    private String method;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_param
     *
     * @mbg.generated
     */
    @ApiModelProperty("请求参数")
    private String operParam;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.json_result
     *
     * @mbg.generated
     */
    @ApiModelProperty("响应结果")
    private String jsonResult;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.status
     *
     * @mbg.generated
     */
    @ApiModelProperty("响应状态")
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.response_code
     *
     * @mbg.generated
     */
    @ApiModelProperty("响应状态码")
    private String responseCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.error_msg
     *
     * @mbg.generated
     */
    @ApiModelProperty("错误信息")
    private String errorMsg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.oper_time
     *
     * @mbg.generated
     */
    @ApiModelProperty("请求时间")
    private Date operTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_oper_log.cost
     *
     * @mbg.generated
     */
    @ApiModelProperty("请求使用时间")
    private Integer cost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_oper_log
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.id
     *
     * @return the value of sys_oper_log.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.id
     *
     * @param id the value for sys_oper_log.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.request_method
     *
     * @return the value of sys_oper_log.request_method
     *
     * @mbg.generated
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.request_method
     *
     * @param requestMethod the value for sys_oper_log.request_method
     *
     * @mbg.generated
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod == null ? null : requestMethod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_id
     *
     * @return the value of sys_oper_log.oper_id
     *
     * @mbg.generated
     */
    public Long getOperId() {
        return operId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_id
     *
     * @param operId the value for sys_oper_log.oper_id
     *
     * @mbg.generated
     */
    public void setOperId(Long operId) {
        this.operId = operId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_name
     *
     * @return the value of sys_oper_log.oper_name
     *
     * @mbg.generated
     */
    public String getOperName() {
        return operName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_name
     *
     * @param operName the value for sys_oper_log.oper_name
     *
     * @mbg.generated
     */
    public void setOperName(String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_url
     *
     * @return the value of sys_oper_log.oper_url
     *
     * @mbg.generated
     */
    public String getOperUrl() {
        return operUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_url
     *
     * @param operUrl the value for sys_oper_log.oper_url
     *
     * @mbg.generated
     */
    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl == null ? null : operUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_ip
     *
     * @return the value of sys_oper_log.oper_ip
     *
     * @mbg.generated
     */
    public String getOperIp() {
        return operIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_ip
     *
     * @param operIp the value for sys_oper_log.oper_ip
     *
     * @mbg.generated
     */
    public void setOperIp(String operIp) {
        this.operIp = operIp == null ? null : operIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_location
     *
     * @return the value of sys_oper_log.oper_location
     *
     * @mbg.generated
     */
    public String getOperLocation() {
        return operLocation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_location
     *
     * @param operLocation the value for sys_oper_log.oper_location
     *
     * @mbg.generated
     */
    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation == null ? null : operLocation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.method
     *
     * @return the value of sys_oper_log.method
     *
     * @mbg.generated
     */
    public String getMethod() {
        return method;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.method
     *
     * @param method the value for sys_oper_log.method
     *
     * @mbg.generated
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_param
     *
     * @return the value of sys_oper_log.oper_param
     *
     * @mbg.generated
     */
    public String getOperParam() {
        return operParam;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_param
     *
     * @param operParam the value for sys_oper_log.oper_param
     *
     * @mbg.generated
     */
    public void setOperParam(String operParam) {
        this.operParam = operParam == null ? null : operParam.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.json_result
     *
     * @return the value of sys_oper_log.json_result
     *
     * @mbg.generated
     */
    public String getJsonResult() {
        return jsonResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.json_result
     *
     * @param jsonResult the value for sys_oper_log.json_result
     *
     * @mbg.generated
     */
    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult == null ? null : jsonResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.status
     *
     * @return the value of sys_oper_log.status
     *
     * @mbg.generated
     */
    @JsonSerialize
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.status
     *
     * @param status the value for sys_oper_log.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.response_code
     *
     * @return the value of sys_oper_log.response_code
     *
     * @mbg.generated
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.response_code
     *
     * @param responseCode the value for sys_oper_log.response_code
     *
     * @mbg.generated
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode == null ? null : responseCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.error_msg
     *
     * @return the value of sys_oper_log.error_msg
     *
     * @mbg.generated
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.error_msg
     *
     * @param errorMsg the value for sys_oper_log.error_msg
     *
     * @mbg.generated
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.oper_time
     *
     * @return the value of sys_oper_log.oper_time
     *
     * @mbg.generated
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.oper_time
     *
     * @param operTime the value for sys_oper_log.oper_time
     *
     * @mbg.generated
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_oper_log.cost
     *
     * @return the value of sys_oper_log.cost
     *
     * @mbg.generated
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_oper_log.cost
     *
     * @param cost the value for sys_oper_log.cost
     *
     * @mbg.generated
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_oper_log
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", operId=").append(operId);
        sb.append(", operName=").append(operName);
        sb.append(", operUrl=").append(operUrl);
        sb.append(", operIp=").append(operIp);
        sb.append(", operLocation=").append(operLocation);
        sb.append(", method=").append(method);
        sb.append(", operParam=").append(operParam);
        sb.append(", jsonResult=").append(jsonResult);
        sb.append(", status=").append(status);
        sb.append(", responseCode=").append(responseCode);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", operTime=").append(operTime);
        sb.append(", cost=").append(cost);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}