package cn.repeatlink.framework.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

/**
 * 获取servlet相关信息
 * 
 * @author tlq20
 *
 */
public class ServletUtils {
	/**
	 * 获取String参数
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 获取String参数
	 */
	public static String getParameter(String name, String defaultValue) {
		String result = getRequest().getParameter(name);
		if (result == null) {
			return defaultValue;
		}else {
			return result;
		}
	

	}

	/**
	 * 获取Integer参数
	 */
	public static Integer getParameterToInt(String name) {
		String result=getRequest().getParameter(name);
		if(StringUtils.isNumeric(result)) {
			return Integer.parseInt(result);
		}
		return null;
	}

	/**
	 * 获取Integer参数
	 */
	public static Integer getParameterToInt(String name, Integer defaultValue) {
		Integer result=getParameterToInt(name);
		return result==null? defaultValue:result;
	}
	
	/**
	 * 获取UserAgent
	 */
	public static String getUserAgent() {
		return getRequestAttributes().getRequest().getHeader("USER-AGENT");
	}

	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * 获取response
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * 获取session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * 将字符串渲染到客户端
	 * 
	 * @param response 渲染对象
	 * @param string   待渲染的字符串
	 * @return null
	 */
	public static String renderString(HttpServletResponse response, String string) {
		try {
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 是否是Ajax异步请求
	 * 
	 * @param request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		String uri = request.getRequestURI();
		if (StringUtils.containsIgnoreCase(uri, ".json")) {
			return true;
		}

		String ajax = request.getParameter("__ajax");
		if (StringUtils.containsIgnoreCase(ajax, "json")) {
			return true;
		}
		return false;
	}
	
	/**
	 * @doc 获取文件下载名字符串（解决乱码问题）
	 * @param fileName
	 * @return
	 */
	public static String getDownloadFileName(String fileName) {
		String finalFileName = fileName;
		String userAgent = getUserAgent();
		if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器  
            finalFileName = URLUtil.encode(fileName, "UTF-8");
        }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器  
            finalFileName = URLUtil.encode(fileName, "UTF-8"); // StrUtil.str(fileName.getBytes(), "ISO8859-1");
        }else{  
            finalFileName = URLUtil.encode(fileName,"UTF-8");//其他浏览器  
        }
		return finalFileName;
	}
}
