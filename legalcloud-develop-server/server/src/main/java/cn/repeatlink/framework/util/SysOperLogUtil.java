package cn.repeatlink.framework.util;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.repeatlink.common.entity.SysOperLog;
import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.service.IFwSysOperLogService;

public class SysOperLogUtil {

	private SysOperLog operlog;
	private ObjectMapper mapper;

	private SysOperLogUtil() {
		this.operlog = new SysOperLog();
		this.mapper = new ObjectMapper();
	}

	public static SysOperLogUtil getInstance() {
		return new SysOperLogUtil();
	}

	/**
	 * 初始化日志相关信息
	 * 
	 * @return
	 */
	public SysOperLogUtil initbuildOperLog() {
		operlog.setOperUrl(ServletUtils.getRequest().getRequestURI());
		operlog.setRequestMethod(ServletUtils.getRequest().getMethod());
		operlog.setOperIp(IpUtils.getIpAddr());
		operlog.setOperTime(new Date());
		LoginUser loginUse = SecurityUtils.getLoginUser();
		if (loginUse != null) {
			operlog.setOperId(loginUse.getUserId());
			operlog.setOperName(loginUse.getUsername());
		} else {
			operlog.setOperId(-1l);
			operlog.setOperName("");
		}

		return this;
	}

	/**
	 * 保存参数
	 * 
	 * @param joinPoint
	 * @return
	 */
	public SysOperLogUtil buildRequestParam(JoinPoint joinPoint) {

		// 设置操作人类别
		String requestMethod = operlog.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params = argsArrayToString(joinPoint.getArgs());
			operlog.setOperParam(StringUtils.substring(params, 0, 2000));
		} else {
			Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest()
					.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			operlog.setOperParam(paramsMap.toString());
		}
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		operlog.setMethod(className + "." + methodName);
		return this;
	}

	/**
	 * 保存响应结果
	 * 
	 * @param object
	 * @return
	 */
	public SysOperLogUtil buildRequestResult(Object object) {

		if (object != null) {
			String resultJson = null;
			try {
				resultJson = mapper.writeValueAsString(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (object instanceof RestResponseVo) {
				AjaxResult<?> ajaxresult = ((RestResponseVo<?>) object).getResults();
				operlog.setResponseCode(ajaxresult.getReturn_code() + "");
				if (ajaxresult.getReturn_code() == 500) {
					// 正常返回，业务异常
					operlog.setStatus(Logstatus.BUSINESSEXCEPTION.getCode());
				} else {
					// 正常返回
					operlog.setStatus(Logstatus.SUCCESS.getCode());
				}
			} else {
				operlog.setStatus(Logstatus.SUCCESS.code);
				operlog.setResponseCode(HttpStatus.OK.value() + "");
			}
			if (resultJson == null) {
				resultJson = "";
			}
			if (resultJson.length() > 2000) {
				resultJson = resultJson.substring(0, 2000);
			}
			operlog.setJsonResult(resultJson);
		}
		return this;

	}
	public SysOperLogUtil updateUserInfo(UserDetails userdetail) {

		if(userdetail!=null) {
			if(StringUtils.isEmpty(this.operlog.getOperName())) {
				this.operlog.setOperName(userdetail.getUsername());
			}
		}
		return this;

	}

	/**
	 * 保存未登录的日志信息
	 * 
	 * @param object
	 * @return
	 */
	public SysOperLogUtil buildNoAuthencationLog() {

		this.operlog.setStatus(Logstatus.UNAUTHORIZED.getCode());
		return this;
	}

	/**
	 * 保存方位未授权资源的日志信息
	 * 
	 * @param object
	 * @return
	 */
	public SysOperLogUtil buildFobbodenLog() {

		this.operlog.setStatus(Logstatus.FORBIDDEN.getCode());
		return this;

	}

	/**
	 * 保存日志信息
	 * 
	 * @param object
	 * @return
	 */
	public void save() {
		try {
			insertSysOperLog(this.operlog);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static enum Logstatus {
		SUCCESS(1), BUSINESSEXCEPTION(2), INTERNALERROR(3), UNAUTHORIZED(4), FORBIDDEN(5);
		private int code;

		Logstatus(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}

	/**
	 * 保存异常处理结果
	 * 
	 * @param t
	 * @return
	 */
	public SysOperLogUtil buildRequestResultWithExeption(Throwable t) {
		if (t != null) {
			if (t instanceof BaseRuntimeException) {
				AjaxResult<Object> ajaxresult = AjaxResult.failed(null, (BaseRuntimeException) t);
				String resultJson = null;
				try {
					resultJson = mapper.writeValueAsString(ajaxresult);

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (resultJson == null) {
					resultJson = "";
				}
				if (resultJson.length() > 2000) {
					resultJson = resultJson.substring(0, 2000);
				}
				operlog.setJsonResult(resultJson);
				operlog.setStatus(Logstatus.BUSINESSEXCEPTION.code);
			} else {
				operlog.setStatus(Logstatus.INTERNALERROR.code);
				String resultJson = null;
				try {
					resultJson = mapper.writeValueAsString(t.getMessage());

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (resultJson == null) {
					resultJson = "";
				}
				if (resultJson.length() > 2000) {
					resultJson = resultJson.substring(0, 2000);
				}
				// 非业务异常记录到异常信息
				operlog.setErrorMsg(resultJson);
			}
		}
		return this;

	}

	/**
	 * 日志内容设置终了
	 * 
	 * @return
	 */
	public SysOperLogUtil buildEnd() {
		Date date = new Date();
		Long cost = date.getTime() - this.operlog.getOperTime().getTime();
		this.operlog.setCost(cost.intValue());
		return this;
	}

	public SysOperLog get() {
		return this.operlog;
	}

	/**
	 * 插入日志
	 * 
	 * @param operLog
	 */
	public static void insertSysOperLog(final SysOperLog operLog) {
		AsyncUtil.getInstance().execute(new TimerTask() {
			@Override
			public void run() {
				SpringBeanFactory.getBean(IFwSysOperLogService.class).inserLog(operLog);
			}
		});
	}

	public static TimerTask recordOper(final SysOperLog operLog) {
		
		return new TimerTask() {
			@Override
			public void run() {
				
				SpringBeanFactory.getBean(IFwSysOperLogService.class).inserLog(operLog);
			}
		};
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "[";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				if (!isFilterObject(paramsArray[i])) {

					try {
						params = params + mapper.writeValueAsString(paramsArray[i]) + ",";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (params.length() > 1) {
			params = params.substring(0, params.length() - 1) + "]";
		} else {
			params = "";
		}
		return params;
	}

	/**
	 * 参数是文件类型或者 request/response对象不保存
	 * 
	 * @param o
	 * @return
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}

}
