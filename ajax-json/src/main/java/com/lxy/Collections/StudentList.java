/**
 * @ClassName : StudentList  //类名
 * @Description : 学生list集合类  //描述
 * @Author : lxy //作者
 * @Date: 2020-09-27 14:50  //时间
 */
package com.lxy.Collections;

import java.util.ArrayList;
import java.util.List;

public class StudentList {
  public static void main(String[] args) {
      List<Student> list=new ArrayList<Student>();
      Student s1 = new Student(1,"张三",20);
      Student s2 = new Student(2,"李四",23);
      Student s3 = new Student(3,"王五",26);
      list.add(s1);
      list.add(s2);
      list.add(s3);
      System.out.println(list);
  }
}

