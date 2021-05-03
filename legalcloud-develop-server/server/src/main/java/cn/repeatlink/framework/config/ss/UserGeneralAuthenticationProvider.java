/**
 * 
 */
package cn.repeatlink.framework.config.ss;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import cn.repeatlink.framework.bean.LoginUserGeneral;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.judicial.util.PasswordUtil;

/**
 * 一般用户APP登录验证器<br>
 * 自定义加密密码验证 
 * @author LAI
 * @date 2020-09-17 15:09
 */
public class UserGeneralAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		String presentedPassword = authentication.getCredentials().toString();
		LoginUserGeneral user = (LoginUserGeneral)userDetails;
		if(user.getUsername() == null || !StringUtils.equals(PasswordUtil.encrypt(presentedPassword, user.getSalt()), user.getPassword())) {
			throw new BadCredentialsException(MessageUtil.getMessage("msg.common.rest.login.pwd.name.error"));
		}
	}
	
	

}
