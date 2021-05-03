package cn.repeatlink.framework.config.ss;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.util.SysOperLogUtil;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		AjaxResult<Object> result=AjaxResult.forbidden(request.getRequestURI());
    	ObjectMapper objectMapper = new ObjectMapper();

		RestResponseVo<Object> resultVo=new RestResponseVo<Object>(result);
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter().write(objectMapper.writeValueAsString(resultVo));
		
		try {
			SysOperLogUtil.getInstance().initbuildOperLog().buildFobbodenLog().buildEnd().save();;
		}catch(Exception e1) {
			e1.printStackTrace();
		}

	}

}
