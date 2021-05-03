package cn.itcast.springmvc.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterCeptor1 implements HandlerInterceptor {
    //在handler执行之前执行此方法，返回值如果是返回true，就是放行，正常执行这个handler，
    // 返回值如果是false，就拦截，这个时候这个handler就不能够进行正常的处理，拦截之后如果我们
    // 想要进行什么操作就看我们自己所定义的。
    //   controller执行前调用此方法
    //	 * 返回true表示继续执行，返回false中止执行
    //	 * 这里可以加入登录校验、权限拦截等
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler) throws Exception {
        System.out.println("InterCeptor1  preHandle........");
        return true;
    }
    //Handler执行之后，返回ModelAndview之前，这里我们可以对这个modelandview做一些处理
    /**
     * controller执行后但未返回视图前调用此方法
     * 这里可在返回用户前对模型数据进行加工处理，比如这里加入公用信息以便页面显示
     */

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, ModelAndView modelAndView) throws Exception {
        System.out.println("InterCeptor1  postHandle........");
    }

    //返回这个modelandview之后，并且我们可以在这里对异常做一些处理。
    /**
     * controller执行后且视图返回后调用此方法
     * 这里可得到执行controller时的异常信息
     * 这里可记录操作日志，资源清理等
     */

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object Handler, Exception e) throws Exception {
        System.out.println("InterCeptor1 afterCompletion........");
    }
}
