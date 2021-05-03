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
import cn.repeatlink.framework.bean.LoginUserGeneral;

/**
 * 一般用户APP登录认证服务
 * @author LAI
 * @date 2020-09-17 15:28
 */
@Service
public class GeneralUserDetailServiceImpl implements UserDetailsService, UserDetailsPasswordService {
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUserGeneral loginuser = null;
		 List<Entity> list = null;
		 try {
			 list = Db.use().findBy("user_general", "login_id", username);
			 if(list == null || list.isEmpty()) {
				 list = Db.use().findBy("user_general", "tel", username);
			 }
			 if(list == null || list.isEmpty()) {
				 list = Db.use().findBy("user_general", "email", username);
			 }
		} catch (Exception e) { 
			e.printStackTrace();
		}
		 if(list==null || list.isEmpty()) {
			 // 用户未找到错误
			 return new LoginUserGeneral(null, null); // throw new UsernameNotFoundException(MessageUtil.getMessage("msg.common.rest.login.pwd.name.error"), new BaseRuntimeException(BaseResultCode.LOGIN_INFO_EMPTY));
		 }
		 loginuser = new LoginUserGeneral(list.get(0).getStr("login_id"), list.get(0).getStr("password"));
		 loginuser.setUser_id(list.get(0).getStr("user_id"));
		 loginuser.setSalt(list.get(0).getStr("salt"));
		 return loginuser;
		 
	}
	
	public  static void main(String args[]) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		// 敏感信息清除
		((LoginUserGeneral)user).setSalt(null);
		((LoginUserGeneral)user).setPassword(newPassword);
		return user;
	}

}
