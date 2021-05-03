package com.lxy.controller;

import com.lxy.pojo.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
    /*第一种方式，服务器要返回一个字符串，直接使用Response，这里我们需要注意的是
    * 这个我们的这个Response对象和这个request对象可以直接进行参数的注入，因为我们的
    * web项目都是基于这个请求和响应的，所以我们只要是客户端浏览器向我们的服务器端发送了
    * ajax异步请求我们都可以直接使用这个Response对象和这个request对象，这里我们的name
    * 也就是我们前端发送ajax异步请求需要给后端传递的参数*/
    @RequestMapping("/a1")
    public void sendAjax(String name,HttpServletResponse response) throws IOException {
        System.out.println(111111111);
        if("admin".equals(name)){
            response.getWriter().print("Ok");
        }else{
            response.getWriter().print("error");
        }


    }
    @RequestMapping("/a2")
    @ResponseBody
    public List<User> ajax2(){
        System.out.println(66666666);
        /*父类引用指向自己的子类接口，多态。*/
        List<User> list=new ArrayList<User>();
        /*这里我们通过这两种方式去创建这个list集合的方式都是可以的，下面的这
         * 个方式是我们直接通过list接口的实现类ArrayList类去进行创建*/
        /* ArrayList<User> users = new ArrayList<User>();*/

        /*模拟从数据库查询得到3个user对象*/
        /*构建集合中的元素对象*/
        User user1=new User("张三",20,"男");
        User user2 = new User("李四", 18, "女");
        User user3 = new User("王五", 25, "男");
        /*向集合中添加对象元素*/
        list.add(user1);
        list.add(user2);
        list.add(user3);
        /*将list集合进行返回，集合中的每一个元素都是对象*/
        return list;

    }
   /* @RequestMapping(value="/a3",produces = {"application/json;charset=UTF-8"})*/
    @RequestMapping("/a3")
    @ResponseBody
    public String ajax3(String name, String pwd){
        System.out.println(8888888);
        String msg="";
        if(name!=null){
           if("admin".equals(name)){
               msg="ok";
           }else{
               msg="用户名有误";
           }
        }
        if(pwd!=null){
            if("123456".equals(pwd)){
                msg="ok";
            }else{
                msg="密码有误";
            }
        }
    System.out.println(msg);
      return msg;
    }
}
