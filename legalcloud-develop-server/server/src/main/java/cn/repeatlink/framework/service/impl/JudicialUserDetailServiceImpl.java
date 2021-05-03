package cn.repeatlink.framework.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.repeatlink.common.Constant;
import cn.repeatlink.framework.bean.LoginUserJudicial;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;

/**
 * 司法书士APP登录认证服务
 * @author LAI
 * @date 2020-09-17 15:28
 */
@Service
public class JudicialUserDetailServiceImpl implements UserDetailsService, UserDetailsPasswordService {
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUserJudicial loginuser = null;
		List<Entity> list = null;
		try {
			list = Db.use().findBy("user_judicial", "login_id", username);
			if (list == null || list.isEmpty()) {
				list = Db.use().findBy("user_judicial", "email", username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 无效用户不可登录
		if (list == null || list.isEmpty() || !Constant.STATUS_VALID.equals(list.get(0).getShort("status"))) {
			// 用户未找到错误
			return new LoginUserJudicial(null, null); //throw new UsernameNotFoundException(null, new BaseRuntimeException(BaseResultCode.LOGIN_INFO_EMPTY));
		}
		loginuser = new LoginUserJudicial(list.get(0).getStr("login_id"), list.get(0).getStr("password"));
		loginuser.setUser_id(list.get(0).getStr("user_id"));
		loginuser.setUserId(list.get(0).getLong("sys_user_id"));
		loginuser.setGroup_id(list.get(0).getStr("group_id"));
		loginuser.setSalt(list.get(0).getStr("salt"));
		return loginuser;
	}
	
	public  static void main(String args[]) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		// 敏感信息清除
		((LoginUserJudicial)user).setSalt(null);
		((LoginUserJudicial)user).setPassword(newPassword);
		return user;
	}

}
