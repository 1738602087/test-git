/**
 * @ClassName : TestController  //类名
 * @Description : 测试控制器  //描述
 * @Author : lxy //作者
 * @Date: 2021-02-14 14:56  //时间
 */
package com.lxy.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    /**
     * 方法描述
     * @param
     * @return: void
     * @Author: lxy
     * @date 2021/2/14 14:57
     */
    //params是规定请求必须要指定参数名称和参数值
    //代表请求时必须带有name参数，并且值必须是admin
    @RequestMapping(params="name=admin")
    public void test(){
      System.out.println("test方法。。。。。");
    }
    /**
     * 方法描述
     * @param username
     * @param password
     * @return: void
     * @Author: lxy
     * @date 2021/2/14 17:50
     */
    /*@RequestParam注解，规定请求时必须带有指定的参数，但是参数值不规定*/
    @RequestMapping("login")
    public void login(@RequestParam String username,@RequestParam String password){
      System.out.println("login方法。。。。。");
    }
    //headers规定，请求时必须带有指定的头信息
    //注意这里路径相同的话，我们的headers的属性值一定要是互斥的才可以同时存在
    @RequestMapping(value = "headers",headers = "content-type=text/html")
    public void headers(){
      System.out.println("headers方法。。。。。html");
    }
    @RequestMapping(value = "headers1",headers = "content-type=text/xml")
    public void headers1(){
      System.out.println("headers1方法。。。。。xml");
    }
    /*消费的数据类型，相当于配置了headers = "content-type=text/xml"*/
    @RequestMapping(value = "consumes",consumes = "text/html")
    public void consumes(){
        System.out.println("consumes方法。。。。。html");
    }
    @RequestMapping(value = "consumes",consumes = "text/xml")
    public void consumes1(){
        System.out.println("consumes1方法。。。。。xml");
    }

    /*生产的数据类型，相当于配置了headers = "accept=text/html
    还代表了响应的头信息中有这个contentype=text/html",这个是和
    响应头有关， 所以说这里我们配置了一个produces有两个含义，第一个
    也就是前台页面传递过来的请求头信息中有这个accept=text/html,同时我们服务
    器端返回数据响应的时候也会带一个头信息给这个前台页面*/
    @RequestMapping(value = "produces",produces = "text/html")
    public void produces(){
        System.out.println("produces方法。。。。。html");
    }
    @RequestMapping(value = "produces",produces = "text/xml")
    public void produces1(){
        System.out.println("produces1方法。。。。。xml");
    }


}

