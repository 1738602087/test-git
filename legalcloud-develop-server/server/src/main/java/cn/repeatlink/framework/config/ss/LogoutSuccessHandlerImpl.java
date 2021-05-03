package cn.repeatlink.framework.config.ss;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.util.JwtUtils;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
	/**
	 * 退出处理
	 * @return
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		JwtUtils.deleteToken(request);

	}
}
