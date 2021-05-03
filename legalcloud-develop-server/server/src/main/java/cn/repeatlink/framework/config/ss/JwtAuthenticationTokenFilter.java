package cn.repeatlink.framework.config.ss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.util.JwtUtils;
import cn.repeatlink.framework.util.MessageUtil;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {

		
		this.authenticationManager = authenticationManager;
		super.setFilterProcessesUrl("/auth/login");
		
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
			
			String username =null;
			String password =null;
			 if(request.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE)!=-1)
			 {
				 ObjectMapper mapper=new ObjectMapper();
				 try (InputStream is =request.getInputStream()){
					 Map<String,String> authbean=mapper.readValue(is,Map.class);
					 username= authbean.get("username");
					 password=authbean.get("password");
				 }catch(Exception e) {
					 e.printStackTrace();
				 }
			 }
			if (!request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: " + request.getMethod());
			}
			if(username==null||password==null) {
				 username = obtainUsername(request);
				 password = obtainPassword(request);
			}
			if (username == null) {
				username = "";
			}

			if (password == null) {
				password = "";
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			// Allow subclasses to set the "details" property
			setDetails(request, authRequest);
			Authentication authentication=this.authenticationManager.authenticate(authRequest);
			return authentication;

	}

	// 成功验证后调用的方法
	// 如果验证成功，就生成token并返回
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		LoginUser loginUser = (LoginUser) authResult.getPrincipal();
		String token = JwtUtils.generateJsonWebToken(loginUser);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		AjaxResult<String> result = AjaxResult.success(token);
		ObjectMapper objectMapper = new ObjectMapper();
		RestResponseVo<String> resultVo=new RestResponseVo<String>(result);
	//	super.successfulAuthentication(request, response, chain, authResult);
		response.getWriter().write(objectMapper.writeValueAsString(resultVo));
		
	
	}
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		AjaxResult<String> result = AjaxResult.failed(MessageUtil.getMessage("msg.common.rest.login.pwd.name.error"));
		RestResponseVo<String> resultVo=new RestResponseVo<String>(result);
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(resultVo));
		
		
	}
}
