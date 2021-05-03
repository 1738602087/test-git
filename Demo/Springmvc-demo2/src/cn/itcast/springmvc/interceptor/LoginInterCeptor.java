package cn.itcast.springmvc.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterCeptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
        /*
        * 在我们跳转的过程中也有可能会发生拦截，所以我们需要做一个判断，这里我们首先要把这个
        * 请求的url拿出来，如果我们的请求的url中包含这个login，我们就直接放行*/
        String uri = request.getRequestURL().toString();
        if(uri.contains("login"))
            return true;
        //a)	拦截用户请求，判断用户是否登录
        /*
        * 我们要判断这个用户身份我们需要从这个session中获取，首先我们需要获得一个seesion对象通过这
        * 个request.getSession()，*/
        HttpSession session = request.getSession();
        /*
        * 接下来我们就是从这个session中判断是否有这个用户信息，注意这个用户名我们不能够使用String来进行
        * 接收，因为用户名可能为空，所以我们要使用这个object来进行接收*/
        Object username = session.getAttribute("username");
        if(username!=null){
            //b)如果用户已经登录(也就是用户名不为空)。放行
            return true;
        }
        else{
            //c)如果用户未登录，跳转到登录页面(request.getContextPath()获得当前工程的工程名)
            response.sendRedirect(request.getContextPath()+"/user/showlogin");
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, ModelAndView modelAndView) throws Exception {
        System.out.println("InterCeptor1  postHandle........");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, Exception e) throws Exception {
        System.out.println("InterCeptor1 afterCompletion........");
    }
}
