package cn.repeatlink.framework.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;
/**
 * 处理共通异常
 * @author tlq20
 *
 */
@ControllerAdvice
public class RLControllerAdvice {

	@ResponseBody
	@ExceptionHandler(value = BaseRuntimeException.class)
	@ResponseStatus(HttpStatus.OK)
	public Object runtimeErrorHandler(BaseRuntimeException ex) {
		return new RestResponseVo<Object>(AjaxResult.failed(null, ex));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object notFountHandler(HttpServletRequest request, NoHandlerFoundException e) {
		return new RestResponseVo<Object>(AjaxResult.notFound(request.getRequestURI()));
		
	}
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object internerError(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		return new RestResponseVo<Object>(AjaxResult.failed(BaseResultCode.INTERNAL_SERVER_ERROR));
		
	}
}
