package com.lxy.swagger.controller;

import com.lxy.swagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public String hello(){
      return "hello";
    }
    //只要我们的接口中，返回值中存在实体类，它就会被扫描到swagger,将我们的实体类的信
    // 息展示在我们的swagger-ui的图形化展示界面中，这里注意如果我们的某一个属性设置为
    //private，那么这个实体类中的该属性就不会再页面上进行展示，只有属性为public访问权
    // 限的才会在页面上进行展示。注意我们的这个swagger-ui的图形化界面显示不显示我们的
    // 这个model实体类和我们的这个在实体类中的注解是没有关系的，和我们的这个controller
    // 表现层中的某一个方法的返回值为这个实体类，才能被扫描到。
    @PostMapping(value="/user")
    public User user(){
        return new User();
    }
    //ApiOperation用于给这个controller类中的某一个
    // 方法上面添加注释。
    @ApiOperation("Hello控制类")
    @DeleteMapping(value = "/hello2")
    public String hello2(@ApiParam("用户名") String username){
        return "hello"+username;
    }
    @ApiOperation("Post测试类")
    @PostMapping(value = "/postt")
    public User postt(User user){
        return user;
    }
}
