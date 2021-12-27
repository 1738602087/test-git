/**
 * @ClassName : Test  //类名
 * @Description :   //描述
 * @Author : lxy //作者
 * @Date: 2021-08-13 15:31  //时间
 */
package main;


import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
       /* String a="a";
        String b="b";
        String c="c";
        List<String> list=new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        List<String> list2=new ArrayList<>();
        for (String s : list) {
            System.out.println(s);
            list2.add(s);
        }
        System.out.println(list2);*/

        String s = new String();
        s="abs52010";
        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println(s1);
        }


    }
}
