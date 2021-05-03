package cn.repeatlink.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysUser;
import cn.repeatlink.common.mapper.SysUserMapper;
import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.common.Constant.UserType;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 LoginUser loginuser = null;
		 SysUser sysuser = sysUserMapper.selectByUserName(username);
		 if(sysuser == null) {
			 throw new UsernameNotFoundException(null);
		 }
		 loginuser=new LoginUser(sysuser.getUserName(),sysuser.getPassword());
		 loginuser.setUserId(sysuser.getUserId());
		 loginuser.setUserType(sysuser.getUserType());
		 return checkAuth(loginuser);
		 
	}
	
	private LoginUser checkAuth(LoginUser loginuser) {
		Long userId = loginuser.getUserId();
		String userType = loginuser.getUserType();
		if(UserType.LAW.typeValue().equals(userType)) {
			Record groupUser = Db.findFirst("select * from law_group_user where sys_user_id=? and `status`=? ", userId, Constant.STATUS_VALID);
			if(groupUser == null) {
				throw new UsernameNotFoundException(null);
			}
		} 
//		else if(UserType.JUDICIAL.typeValue().equals(userType)) {
//			Record judicial = Db.findFirst("select * from user_judicial where sys_user_id=? and `status`=? ", userId, Constant.STATUS_VALID);
//			if(judicial == null) {
//				throw new UsernameNotFoundException(null);
//			}
//			Record groupUser = Db.findFirst("select * from law_group_user where group_id=? and `status`=? ", judicial.getStr("group_id"), Constant.STATUS_VALID);
//			if(groupUser == null) {
//				throw new UsernameNotFoundException(null);
//			}
//		}
		
		return loginuser;
	}
	
	public  static void main(String args[]) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
