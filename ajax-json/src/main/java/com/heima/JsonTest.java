/**
 * @ClassName : JsonTest  //类名
 * @Description : 测试java对象转json  //描述
 * @Author : lxy //作者
 * @Date: 2020-11-03 13:48  //时间
 */
package com.heima;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.pojo.Person;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {
    @Test
    public void test1() throws Exception {
        /*java对象转json步骤首先导入jacksonjar包，其次创建jackson核心对象ObjectMapper，最后调用
        * ObjectMapper对象的方法进行转换*/
        //1:创建Jackson的核心对象，ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //2：创建Person对象,并且初始化
        Person person = new Person("李向阳",22,"男");
        /*我们转换方法有两个
        * 1：writeValue(参数1,obj)
        *    参数1：
        *         File:将obj对象转化为JSON字符串，并保存到指定的文件中
        *         write:将obj对象转化为JSON字符串，并且将JSON数据填充到字符输出流当中
        *         OutputStream：将obj对象转化为JSON字符串，并且将JSON数据填充到字节输出流当中
        *  2：writeValueAsString 将对象转为json字符串*/
        //这里我们使用writeValueAsString 直接将对象转为json字符串
        String json = mapper.writeValueAsString(person);
        //将json字符串进行打印输出查看，  {"name":"李向阳","age":22,"gender":"男"}
        System.out.println(json);
        //将数据写到这个d://a.txt即d盘下面的这个a.txt文件中，
        // 文件内容就是{"name":"李向阳","age":22,"gender":"男"}
        //mapper.writeValue(new File("d://a.txt"),person);
        //writeValue，将数据关联到Writer中,这里我们下面方法中的第一个参数传递字节流和字符流都可以，
        //流的目标或者目的地也就是这个d盘下面的某一个文件里面。
        mapper.writeValue(new FileWriter("d://b.txt"),person);

    }
    /*list集合转为json
    * list集合转化出来是一个数组，数组中放的如果是字符串就是json的字符串，
    * 放的是对象就是json对象*/
    @Test
    public void test2() throws Exception {
        //创建一个list集合，集合中的每一个元素都是Person对象
        List<Person> people=new ArrayList<Person>();
        //创建三个person对象
        Person p1 = new Person("李四", 28, "男");
        Person p2 = new Person("王五", 24, "男");
        Person p3 = new Person("小龙女", 18, "女");
        //将person对象放到这个list集合中
        people.add(p1);
        people.add(p2);
        people.add(p3);
        //3:创建Jackson的核心对象，ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //4:使用writeValueAsString 直接将对象转为json字符串
        String json = mapper.writeValueAsString(people);
        /*[
            {"name":"李四","age":28,"gender":"男"},
            {"name":"王五","age":24,"gender":"男"},
            {"name":"小龙女","age":18,"gender":"女"}
          ]*/
        System.out.println(json);
    }
    /*Map集合转为json
    * 结果和对象的格式是一致的*/
    @Test
    public void test3() throws Exception {
        //创建一个map集合，map的键为String类型，value为Object对象类型
        Map<String,Object> map=new HashMap<>();
        //创建person对象
        map.put("name","王五");
        map.put("age","18");
        map.put("gender","女");
        //3:创建Jackson的核心对象，ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //4:使用writeValueAsString 直接将对象转为json字符串
        String json = mapper.writeValueAsString(map);
        /*{"gender":"女","name":"王五","age":"18"}*/
        System.out.println(json);
    }
    /*Map集合转为json
    * 结果和对象的格式是一致的*/
    @Test
    public void test4() throws Exception {
        //创建一个map集合，map的键为String类型，value为Object对象类型
        Map<Integer,Object> map=new HashMap<>();
        //创建两个person对象并将person对象放到这个map集合中
        map.put(1,new Person("王五",18,"女"));
        map.put(2,new Person("黄蓉",23,"女"));
        //3:创建Jackson的核心对象，ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //4:使用writeValueAsString 直接将对象转为json字符串
        String json = mapper.writeValueAsString(map);
        /*这里我们map的键虽然定义的是Integer类型，但是转化仍然会为字符串*/
        /*{
            "1":{"name":"王五","age":18,"gender":"女"},
            "2":{"name":"黄蓉","age":23,"gender":"女"}
          }
         */
        System.out.println(json);
    }
}

