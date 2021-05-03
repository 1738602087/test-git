package cn.repeatlink.framework.config.ss;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.framework.util.SysOperLogUtil;

/**
 * 登录验证日志记录
 * 
 * @author tlq20
 *
 */
@Component
public class AuthenticationEventPublisherImpl implements AuthenticationEventPublisher {

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		try {

			LoginUser loginUser = (LoginUser) authentication.getPrincipal();
			AjaxResult<String> result = AjaxResult.success("");
			RestResponseVo<String> resultVo = new RestResponseVo<String>(result);
			SysOperLogUtil.getInstance().initbuildOperLog().buildRequestResult(resultVo).updateUserInfo(loginUser)
					.buildEnd().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		try {

			String usename=authentication.getName();
			AjaxResult<String> result = AjaxResult.failed(MessageUtil.getMessage("msg.common.rest.login.fail"));
			RestResponseVo<String> resultVo = new RestResponseVo<String>(result);
			SysOperLogUtil.getInstance().initbuildOperLog().buildRequestResult(resultVo).updateUserInfo(new LoginUser(usename, null))
					.buildEnd().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
