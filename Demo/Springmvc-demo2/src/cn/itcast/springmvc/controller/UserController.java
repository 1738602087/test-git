package cn.itcast.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    /*
    * 现在我们是登录页面做下面的请求处理，我们还要展示这个登录界面，我们需要重新定义一个方法，这个
    * 方法用于显示我们的登录页面，这个方法我们需要返回一个逻辑视图也就是我们登录页面的jsp,
    * 现在我们有两个请求莫我们访问第一个请求打开这个登录界面，我们点击这个
    * 提交就做这个登录处理，我们访问第二个请求，一旦登录成功就跳转到这个商品列表界面，
    * 接下来我们需要写一个拦截器，我们应该在拦截器的前处理，也就是在用户执行handler之前去进行这个处理*/
    @RequestMapping("/user/showlogin")
    public String showlogin(){
      return "login";
    }
    /*
    * 我们的这个用户登录方法虽然要接收参数，参数是从表单中获取的，但是她只有两个参数，
    * 我们没有必要去创建这个pojo，我们可以直接使用这个形参*/
    @RequestMapping("/user/login")
    public String Userlogin(String username, String password, HttpSession session) {
        //判断用户名和密码是否正确
        System.out.println(username);
        System.out.println(password);
        //如果用户名正确向session中写入用户信息，我们需要获得这个session，我
        // 们通过形参的方式，之后我们向这个session中存入这个用户名。
        session.setAttribute("username",username);
        //返回登录成功或者重新跳转到商品列表页面。这里我们没有这个登录成功页面，我们
        // 返回这个商品列表页面，商品列表页面我们需要使用这个redirect重定向。
        return "redirect:/itemlist.action";

    }

}
