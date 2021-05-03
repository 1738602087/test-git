package cn.repeatlink.framework.config.ss;


import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.util.ServletUtils;
import cn.repeatlink.framework.util.SysOperLogUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 认证失败处理类 返回未授权
 * 
 *
 */
@Log4j2
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
    	
    	
    	
    	AjaxResult<Object> result=AjaxResult.unauthentication();
    	ObjectMapper objectMapper = new ObjectMapper();

		RestResponseVo<Object> resultVo=new RestResponseVo<Object>(result);
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(objectMapper.writeValueAsString(resultVo));
		
		try {
			SysOperLogUtil.getInstance().initbuildOperLog().buildNoAuthencationLog().buildEnd().save();;
		}catch(Exception e1) {
			if(ServletUtils.getRequestAttributes() == null || ServletUtils.getRequest() == null) {
				if(ServletUtils.getRequestAttributes() == null) {
					log.error("RequestAttributes is null.");
				} else {
					log.error("Request is null.");
				}
			} else {
				e1.printStackTrace();
			}
		}
		
    	
    	
       
    }
}
