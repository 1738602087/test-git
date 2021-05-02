package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    public  void  service(HttpServletRequest req,HttpServletResponse rep) throws ServletException{
      System.out.println("BaseServlet的service方法执行了------");
      //完成方法分发
        //1.获取请求路径
        String requestURI = req.getRequestURI();// /travel//user/add
        System.out.println(requestURI);
        //2.获取方法名称
        String method = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        System.out.println("方法名称 "+method);
       // 3.获取方法对象
        //这里的this代表哪一个对象， 谁调用我，我代表谁。
       System.out.println(this);
        try {
            Method m= this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
            //4.执行方法
            try {
                m.invoke(this,req,rep);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    /**
     * 方法描述  直接将传入的对象序列化为json,并且写会给客户端
     * @param
     * @return: void
     * @Author: lxy
     * @date 2020/11/17 20:38
     */

    /*writeValue（参数，obj）：
       参数1： File： 将对象转为json字符串，保存到指定的文件中
              Writer： 将对象转为json字符串，并将json数据填充到字符输出流中
              outputStream： 将对象转为json字符串，并将json数据填充到字节输出流中
     */
    public void writeValue(Object obj,HttpServletResponse response) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json,charset=UTF-8");
        mapper.writeValue(response.getOutputStream(),obj);

    }
    /*writeValueAsString（obj）：
        将对象转为json字符串
     */

    /**
     * 方法描述  将传入的对象序列化为json.进行返回
     * @param
     * @return: String
     * @Author: lxy
     * @date 2020/11/17 20:40
     */

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);

    }
}
