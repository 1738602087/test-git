package com.atguigu.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		//因为这里我们上一步说到我们的这个方法中的参数列表中的AuthenticationToken对象也就是我们
		//shiroHandler中的这个UsernamePasswordToken，所以我们就需要首先把这个AuthenticationToken对象
		//转换为 UsernamePasswordToken。
		//1. 把 AuthenticationToken 转换为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		//2. 从 UsernamePasswordToken 中来获取 username
		String username = upToken.getUsername();

		//3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录，这里
		//我们就不从数据库中进行查询比较麻烦，这里我们采用一些静态数据
		//根据用户名获取到对应的信息，不需要密码。
		System.out.println("从数据库中获取username: " + username + "所对应的用户信息");

		//4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在!");
		}

		//5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定");
		}

		//6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
		//以下信息是从数据库中获取的.
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
		Object principal = username;
		//2). credentials: 密码.从数据表中获取， 密码的比对是由shiro帮助我们完成的。
        //这里我们需要注意的是我们的这个密码的比对
		Object credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
        //因为我们给MD5进行盐值加密之后，那么每两个人的密码都是不一致的。所以我们这里就不能够
        // 随便写,这里我们使用这个if条件进行判断，如果我们传入的用户名是admin，我们的密码是第一种类型
        //否则就是第二种类型
		if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}

		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4). 盐值.盐值是一个ByteSource类型，ByteSource是一个接口，接口里面有一个内部类
        //内部类中有一个方法可以把这个盐加密成我们需要的值，一般情况下我们使用什么来做盐呢，我们
        //都是通常使用的都是一个随机字符串，我们这里面使用这个用户名来做，因为这个用户名是唯一的，
        //所以这里我们使用用户民来作为这个原始值。
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);

		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
     /*这里如果我们想要知道MD5加密之后的这个字符串的值是多少，我们就需要通过下面的这个方法*/
	public static void main(String[] args) {
	    //加密方式
		String hashAlgorithmName = "MD5";
         //	原密码明文123456
		Object credentials = "123456";
		//盐值
		Object salt = ByteSource.Util.bytes("user");;
		//加密次数
		int hashIterations = 1024;
        //最终结果
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//授权会被 shiro 回调的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//1. 从 PrincipalCollection 中来获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();

		//2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
		//这个时候有两个办法，一个就是用户的登录信息中已经包含权限信息，另外一个也就是
		//可能需要查询数据库。
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}

		//3. 创建 SimpleAuthorizationInfo, 并设置其 roles 属性.
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

		//4. 返回 SimpleAuthorizationInfo 对象.
		return info;
	}
}
