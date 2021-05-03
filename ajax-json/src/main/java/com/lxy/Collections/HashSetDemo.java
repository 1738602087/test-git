/**
 * @ClassName : HashSetDemo  //类名
 * @Description : HashSet集合  //描述
 * @Author : lxy //作者
 * @Date: 2020-09-27 14:04  //时间
 */
package com.lxy.Collections;

import java.util.HashSet;

public class HashSetDemo {
  public static void main(String[] args) {
      /*
       * HashSet保证元素唯一性的原理?
       *
       * 通过查看add方法的源码，我们知道了添加功能的执行过程中，是进行了数据的判断的。
       * 这个判断的流程是：
       * 		首先比较对象的哈希值是否相同，这个哈希值是根据对象的hashCode()计算出来的。
       * 			如果哈希值不同，就直接添加到集合中
       * 			如果哈希值相同，继续执行equals()进行比较，
       * 				返回的是true，说明元素重复，不添加。
       * 				返回的是false，说明元素不重复，就添加。
       *
       * 如果我们使用HashSet集合存储对象，你要想保证元素的唯一性，就必须重写hashCode()和equals()方法。
       */
      //创建集合对象
      HashSet<String> hs = new HashSet<String>();

       //添加元素
      hs.add("hello");
      hs.add("world");
      hs.add("java");
      hs.add("world");

      //遍历集合
       for(String s : hs) {
           System.out.println(s);
            }
       }
}
