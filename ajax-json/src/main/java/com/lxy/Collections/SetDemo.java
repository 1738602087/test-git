/**
 * @ClassName : SetDemo  //类名
 * @Description : Set集合特点  //描述
 * @Author : lxy //作者
 * @Date: 2020-09-27 13:54  //时间
 */
package com.lxy.Collections;

import java.util.HashSet;
import java.util.Set;

public class SetDemo {
  public static void main(String[] args) {
      /*
       * Set:
       * 		一个不包含重复元素的 collection
       * HashSet:
       * 		它不保证 set 的迭代顺序；特别是它不保证该顺序恒久不变
       */
       //创建集合对象
      Set<String> set = new HashSet<String>();

     //添加元素
      set.add("hello");
      set.add("world");
      set.add("java");
      //唯一
      set.add("world");

      //遍历集合
     for(String s : set) {
           System.out.println(s);
        }

      }
}



