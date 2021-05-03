package com.lxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lxy.pojo.User;
import com.lxy.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/json")
public class UserController {
    /*
    * @ResponseBody注解作用和原理
       @ResponseBody这个注解通常使用在控制层（controller）的方法上，其作用是
       将方法的返回值以特定的格式写入到response的body区域，进而将数据返回给客户端。
       当方法上面没有写ResponseBody,底层会将方法的返回值封装为ModelAndView对象。

       假如是字符串则直接将字符串写到客户端,假如是一个对象，此时会将对象转化为json串
       然后写到客户端。这里需要注意的是，如果返回对象,按utf-8编码。如果返回String，默
       认按iso8859-1编码，页面可能出现乱码。因此在注解中我们可以手动修改编码格式，
       例如
       @RequestMapping(value="/cat/query",produces="application/json;charset=utf-8")，
       前面是请求的路径，后面是编码格式。

       那么，控制层方法的返回值是如何转化为json格式的字符串的呢？其实是通过
       HttpMessageConverter中的方法实现的，因为它是一个接口，因此由其实现
       类完成转换。如果是bean对象，会调用对象的getXXX（）方法获取属性值并且以键值对
       的形式进行封装，进而转化为json串。如果是map集合，采用get(key)方式获取value
       值，然后进行封装。*/

    /*这里因为我们第一个方法返回值是String类型，所以我们需要通过这个produces属性
    * 指定我们的编码格式，尽管我们已经在这个springmvc的配置文件springmvc.xml中配置了
    * 这个json格式的转换，但是哪一个格式转换作用于我们的这个方法返回值是一个对象集合。
    * ，而这里我们的方法返回值是一个字符串，所以会以iso-88591回显，所以我们要手动指定编码
    * 在一般的开发中，我们这两种方式配合使用就可以很好的解决乱码问题。*/
    @RequestMapping(value="/j1",produces = "application/json; charset=utf-8")
   /*@RequestMapping("/j1")*/
   //将java对象转换为json字符串。这里我们虽然使用了这个 @ResponseBody注解，但是因为我们该方法的
    // 的返回值为一个String类型，所以我们必须要使用这个jackson的核心对象ObjectMapper的
    // writeValueAsString()方法来将我们的java对象转换为json字符串，该方法需要接收一个对象作为
    // 方法参数，这里我们下面的两个方法因为方法的返回值直接就是一个对象，而且我们使用了
    // @ResponseBody注解，所以直接就返回json格式字符串。
    @ResponseBody
    public String json1() throws JsonProcessingException {
        //1.创建jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("lxy666",20,"男");
        //输出创建的user对象.注意这里的user对象一定要重写toString方法，为了结果展示
         System.out.println(user); //User{username='lxy666', age=20, sex='男'}
        //将java对象转换为json字符串。
        String us = mapper.writeValueAsString(user);
        System.out.println(us);//{"username":"lxy666","age":20,"sex":"男"}
        return us;
    }
    @RequestMapping("/j2")
    @ResponseBody
    public User json2(){
        User user = new User("李向阳", 22, "男");
        System.out.println(user); //User{username='李向阳', age=22, sex='男'}
        return user;
    }
    //将List集合(集合中的每一个元素都是java对象)转换为json字符串。*/
    @RequestMapping("/j3")
    @ResponseBody
    public List<User> json3(){
        /*父类引用指向自己的子类接口，多态。*/
        List<User> users = new ArrayList<User>();
        /*这里我们通过这两种方式去创建这个list集合的方式都是可以的，下面的这
        * 个方式是我们直接通过list接口的实现类ArrayList类去进行创建*/
       /* ArrayList<User> users = new ArrayList<User>();*/

       /*模拟从数据库查询得到3个user对象*/
       /*构建集合中的元素对象*/
        User user1 = new User("张三",22,"男");
        User user2 = new User("lisi", 28, "男");
        User user3 = new User("李玉", 18, "女");
        /*向集合中添加元素*/
        users.add(user1);
        users.add(user2);
        users.add(user3);
        System.out.println(users); //输出集合对象
        /*[
           User{username='张三', age=22, sex='男'},
           User{username='lisi', age=28, sex='男'},
           User{username='李玉', age=18, sex='女'}
         ]*/

        /*将集合对象进行返回。和方法的返回值进行匹配*/
        return users;
    }
    //map集合转为json
    @RequestMapping("/j4")
    @ResponseBody
    public Map json4(){
        /*创建一个map集合，map的键为Inter类型，map的值为java对象类型*/
        Map<Integer, User> map = new HashMap<>();
        //向map集合中存入数据，键为Inter类型，值为java对象类型
        map.put(1,new User("王五",23,"男"));
        map.put(2,new User("赵六",18,"男"));
        System.out.println(map);//输出map对象
        /*{
           1=User{username='王五', age=23, sex='男'},
           2=User{username='赵六', age=18, sex='男'}
          }*/
        return map;
    }
    @RequestMapping("/time1")
    /*@ResponseBody*/ //http://localhost:8080/ajax-json/WEB-INF/jsp/1604373641145.jsp
    /*因为这里我们将这个@ResponseBody注解注掉了，所以就会到当前项目的上面路径找一个jsp页面
    * 很明显我们项目中不存在这个页面，所以就会报404*/
    public String json5() throws JsonProcessingException {
        Date date = new Date();
        System.out.println(date);
        //Tue Nov 03 11:17:41 CST 2020
        return new ObjectMapper().writeValueAsString(date);
    }
    @RequestMapping("/time2")
    @ResponseBody//使用该注解将返回值转换为json给客户端。
    public String json6() throws JsonProcessingException {
        /*这里我们向浏览器回写一个系统的当前日期我们可以将这个部分
        * 内容抽成一个工具类，fastjson也可以使用，只不过Jackson最好用*/
        ObjectMapper mapper = new ObjectMapper();
        /*如果我们不想让它返回时间戳，我们就需要关闭它的时间戳功能*/
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        /*时间格式化问题，自定义日期格式对象，这里需要特别注意的一点是
        * 这个日期格式为这个"yyyy-MM-dd HH:mm:ss"，表示月份的M应该大写，表示分钟的
        * m应该小写。所以我们需要注意，否则两个都是大M或者是小m最后结果就会出错*/
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       /*让mapper指定日期时间格式为SimpleDateFormat*/
        mapper.setDateFormat(sdf);
        /*写一个日期对象*/
        Date date = new Date();
        System.out.println(date);
        /*将日期对象转换为json格式的字符串*/
        return mapper.writeValueAsString(date);//"2020-11-03 14:21:17"

    }
    @RequestMapping("/time3")
    @ResponseBody
    public String json7() throws JsonProcessingException {
        /*写一个日期对象*/
        Date date = new Date();
        System.out.println(date);
        return JsonUtil.getJson(date);

    }
}
