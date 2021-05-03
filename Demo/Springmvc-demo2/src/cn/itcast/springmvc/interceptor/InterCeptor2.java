package cn.itcast.springmvc.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterCeptor2 implements HandlerInterceptor {
    //在handler执行之前执行此方法，返回值如果是返回true，就是放行，正常执行这个handler，
    // 返回值如果是false，就拦截，这个时候这个handler就不能够进行正常的处理，拦截之后如果我们
    // 想要进行什么操作就看我们自己所定义的。
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler) throws Exception {
        System.out.println("InterCeptor2  preHandle........");
        return true;
    }
    //Handler执行之后，返回ModelAndview之前，这里我们可以对这个modelandview做一些处理
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, ModelAndView modelAndView) throws Exception {
        System.out.println("InterCeptor2 postHandle........");
    }

    //返回这个modelandview之后，并且我们可以在这里对异常做一些处理。
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, Exception e) throws Exception {
        System.out.println("InterCeptor2 afterCompletion........");
    }
}
