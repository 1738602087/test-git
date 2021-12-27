package main;

import java.util.*;

/**
 * @ClassName: TestString
 * @Description: 测试String的split()方法
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-12-07 09:05
 * @Version: 1.0
 */
public class TestString {

    public static void main(String[] args) {
        //String s="1,2,3";
        String s="2,1";
        String[] split = s.split(",",0);
        System.out.println(split.length);
        Map<Integer,String> map=new HashMap<>();
        for (int j = 0; j < split.length; j++) {
            String s1 = split[j];
            map.put(j+1,s1);
        }
        System.out.println(map.size());
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            System.out.println(key);
            String value = entry.getValue();
            System.out.println(key + ":" + value);
            if (value.equals("1")) {
                System.out.println(key);
            }
        }

    }
}
