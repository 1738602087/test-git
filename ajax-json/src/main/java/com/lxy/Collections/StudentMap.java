/**
 * @ClassName : StudentMap  //类名
 * @Description : 测试学生对象  //描述
 * @Author : lxy //作者
 * @Date: 2020-09-27 14:30  //时间
 */
package com.lxy.Collections;

import java.util.HashMap;
import java.util.Map;

public class StudentMap {
  public static void main(String[] args) {
      Map<Integer,Student> map=new HashMap<Integer, Student>();
      Student s1 = new Student(1,"张三",20);
      Student s2 = new Student(2,"李四",23);
      Student s3 = new Student(3,"王五",26);
      map.put(1,s1);
      map.put(2,s2);
      map.put(3,s3);
      System.out.println(map);

  }
}

