package com.atguigu.shiro.helloworld;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* shiro的HelloWorld并不是我们自己动手写的，而是我们通过这个shiro所提供的一个案例来深入
* 了解到这个shiro的大致使用和工作方式，*/
/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);
    public static void main(String[] args) {

        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        // 获取当前的Subject. 调用方法SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        // 测试使用Session
        // 获取 Session: 调用Subject#getSession()
        Session session = currentUser.getSession();
        //向这个session中放一个somekey,值为这个aValue
        session.setAttribute("someKey", "aValue");
        // 之后对这个key的value值进行取出，判断是否等于这个aValue
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("---> Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        // 测试这个当前的用户是否已经被认证，即是否已经登录。
        // 调用 Subject 的 isAuthenticated()
        if (!currentUser.isAuthenticated()) {
        	//我们这块这个显然是没有经过认证，所以就会把这个用户名和密码封装成 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // rememberme记住我的功能方法
            token.setRememberMe(true);
            try {
            	// 调用subject的login方法执行登录，我们能否登录成功取决于我们的这个配置文件中是否配置了这个
                //用户名和密码和我们的当前的给定用户名密码是否一致
                currentUser.login(token);
            } 
            // 若没有指定的用户，则 shiro 就会抛出 UnknownAccountException异常，即用户不存在(没有在配置文件中找到)
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return; 
            } 
            // 若用户存在但是密码不匹配，则 shiro 就会抛出 IncorrectCredentialsException无效的凭证异常(密码不对)
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return; 
            } 
            //用户被锁定的异常 LockedAccountException(这个我们暂时无法进行测试，等到和spring整合后进行测试理解)
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            //所有认证时异常的父类，也就是上面我们说到的这几个异常都是这个异常的子类
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("----> User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        // 测试是否有某一个角色. 调用 Subject的 hasRole 方法.
        if (currentUser.hasRole("schwartz")) {
            log.info("----> May the Schwartz be with you!");
        } else {
            log.info("----> Hello, mere mortal.");
            return; 
        }

        //test a typed permission (not instance-level)
        // 测试用户是否具备某一个行为. 调用 Subject 的 isPermitted() 方法。
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("----> You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        // 测试用户是否具备某一个行为.
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("----> You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        // 执行登出. 调用 Subject 的 Logout() 方法.
        System.out.println("---->" + currentUser.isAuthenticated());
        
        currentUser.logout();
        
        System.out.println("---->" + currentUser.isAuthenticated());

        System.exit(0);
    }
}
