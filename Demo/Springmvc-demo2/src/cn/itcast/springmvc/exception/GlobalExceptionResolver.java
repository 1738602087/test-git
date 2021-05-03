package cn.itcast.springmvc.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/*
* 全局异常处理器
* */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        //这里为什么要搞一个判断，因为我们现在用的是自定义异常， 那么我们的异常就分为两类，如果
        //是这个自定义的异常，我们就可以直接去进行处理，如果是运行时异常我们就可以从exception中取到
        //数据重新赋值给我们的msg,因为我们一会要把这个msg写到我们的这个错误日志里面，这里如果我们
        //不想要使用这个自定义的异常，我们直接写一个运行时的异常进行处理也是可以的，因为这里我们
        //想要我们的异常系显示的更加人性化一点。

        //判断异常的种类，如果是这个自定义的异常，我们可以取出这个错误消息
        String msg=null;
        if(exception instanceof CustomerException){
           CustomerException Csep= (CustomerException) exception;
            msg= Csep.getMsg();
        }
        else{
            //如果是这个运行时异常我们去取错误的堆栈。
            StringWriter s = new StringWriter();
            PrintWriter printWriter = new PrintWriter(s);
            exception.printStackTrace(printWriter);
            msg=s.toString();

        }
        //写日志，发短信或者发送邮件(这些都是给我们程序员进行纠正错误信息的)
        //.........业务处理的操作
        //返回一个错误页面，显示这个错误信息。所以我们需要定义一个错误界面error.jsp
        //因为我们当前的这个方法的返回值是一个modelandview，所以我们可以返回一个逻辑视图
        //经过我们的处理器解析器进行解析之后跳转到我们的物理视图界面
        ModelAndView modelandview = new ModelAndView();
        modelandview.addObject("msg",msg);
        modelandview.setViewName("error");
        return modelandview;
    }
}
