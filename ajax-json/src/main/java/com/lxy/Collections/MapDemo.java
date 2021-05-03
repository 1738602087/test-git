/**
 * @ClassName : MapDemo  //类名
 * @Description :   //描述
 * @Author : lxy //作者
 * @Date: 2020-09-27 13:40  //时间
 */
package com.lxy.Collections;

import java.util.HashMap;
import java.util.Map;

/*
 * Map:
  * 将键映射到值的对象。一个映射不能包含重复的键；每个键最多只能映射到一个值。
   *
   * 举例：学生的学号和姓名
   * 		it001	林青霞
   * 		it002	张曼玉
   * 		it003	王祖贤
   *
   * public interface Map<K,V>
   *
    */
public class MapDemo {
    public static void main(String[] args) {
        //创建集合对象
        Map<String,String> map = new HashMap<String,String>();

        //添加元素
        //put(K key,V value):添加元素。
        map.put("it001", "林青霞");
        map.put("it002", "张曼玉");
        map.put("it003", "王祖贤");

        //输出集合对象
        System.out.println(map);
        }
}

