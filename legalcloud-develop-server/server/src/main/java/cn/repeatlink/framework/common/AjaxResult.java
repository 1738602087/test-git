package cn.repeatlink.framework.common;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.MessageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

@ApiModel("响应用户实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResult<T> {
    @ApiModelProperty("API版本")
	private String api_version = "1.0";
    @ApiModelProperty("响应code，200代表成功，500 代表失败,401 未登录，403 没有权限")
	private int return_code;
    @ApiModelProperty("错误提示信息")
    private ResultErrorCode error_code;
    @ApiModelProperty("提示信息")
	private String return_msg;
    @ApiModelProperty("响应数据")
	private T result_datas;

	protected AjaxResult() {

	}

	protected AjaxResult(int code, String message, T data) {
		this.return_code = code;
		this.return_msg = message;
		this.result_datas = data;
	}
	
	protected AjaxResult(int code, ResultErrorCode  error_code, T data) {
		this.return_code = code;
		this.error_code = error_code;
		this.result_datas = data;
	}
	
	public static <T> AjaxResult<T> successMsg(String msg) {
		return new AjaxResult<T>(HttpStatus.OK.value(), msg, null);
	}
	
	public static <T> AjaxResult<T> success() {
		return successMsg(MessageUtil.getMessage("manager.user.operation.success"));
	}
	
	public static <T> AjaxResult<T> success(T data) {
		return new AjaxResult<T>(HttpStatus.OK.value(), (String)null, data);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data    获取的数据
	 * @param message 提示信息
	 */
	public static <T> AjaxResult<T> success(T data, String messagge) {
		return new AjaxResult<T>(HttpStatus.OK.value(), messagge, data);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data    获取的数据
	 * @param message 提示信息
	 * @param args    提示消息中占位符信息
	 */
	public static <T> AjaxResult<T> success(T data, BaseResultCode resultCode, String... args) {

		return new AjaxResult<T>(HttpStatus.OK.value(), MessageUtil.getMessage(resultCode.getFieldname(), args), data);
	}

	/**
	 * 失败返回结果
	 * 
	 * @param errorCode 错误码
	 */
	public static AjaxResult<Object> failed(BaseResultCode resultCode, String... args) {
		ResultErrorCode errocode=new ResultErrorCode(resultCode.getCode(), resultCode.getFieldname(),args);
		return new AjaxResult<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				errocode, null);
	}

	/**
	 * 失败返回结果
	 * 
	 * @param errorCode 错误码
	 * @param message   错误信息
	 */
	public static <T> AjaxResult<T> failed(T data, BaseResultCode resultCode, String... args) {
		ResultErrorCode errocode=new ResultErrorCode(resultCode.getCode(), resultCode.getFieldname(),args);
		return new AjaxResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				errocode, data);
	}
	
	/**
	 * 失败返回结果
	 * 
	 * @param errorCode 错误码
	 * @param message   错误信息
	 */
	public static <T> AjaxResult<T> failed(T data, BaseRuntimeException runtimeException) {
		ResultErrorCode errocode=new ResultErrorCode(runtimeException.getErrocode().getCode(), runtimeException.getErrocode().getFieldname(),runtimeException.getParams());
		return new AjaxResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				errocode, data);
	}

	/**
	 * 失败返回结果
	 * 
	 * @param message 提示信息
	 */
	public static <T> AjaxResult<T> failed(String message) {
		ResultErrorCode errocode=new ResultErrorCode();
		errocode.setMsg(message);
		return new AjaxResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errocode, null);
	}

	/**
	 * 未登录返回结果
	 */
	public static <T> AjaxResult<T> unauthentication() {
		return new AjaxResult<T>(HttpStatus.UNAUTHORIZED.value(),new ResultErrorCode(BaseResultCode.LOGIN_INFO_EMPTY.getCode(),BaseResultCode.LOGIN_INFO_EMPTY.getFieldname()), null);
	}

	/**
	 * 未授权返回结果
	 */
	public static <T> AjaxResult<T> forbidden(String resource) {
		return new AjaxResult<T>(HttpStatus.FORBIDDEN.value(), new ResultErrorCode(BaseResultCode.LOGIN_INFO_INVALID.getCode(),BaseResultCode.LOGIN_INFO_INVALID.getFieldname(),resource),null);
			
	}
//	
	/**
	 * 未授权返回结果
	 */
	public static <T> AjaxResult<T> notFound(String resource) {
		return new AjaxResult<T>(HttpStatus.NOT_FOUND.value(), new ResultErrorCode(BaseResultCode.RESOURCE_NOT_FOUND.getCode(),BaseResultCode.RESOURCE_NOT_FOUND.getFieldname(),resource),null);
			
	}
}
