package cn.repeatlink;

import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.judicial.util.PasswordUtil;


class BaseApplicationTests {

	
	void contextLoads() {
	}

	public static void main(String[] args) {
		
		System.out.println(IDUtil.nextID());
		
		String salt = PasswordUtil.buildSalt();
		String password = "123456";
		System.out.println("password：" + password);
		System.out.println("salt：" + salt);
		System.out.println("password_salt：" + PasswordUtil.encrypt(password, salt));
	}
}
