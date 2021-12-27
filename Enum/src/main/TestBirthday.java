package main;

/**
 * @ClassName: TestBirthday
 * @Description: 获取出生日期
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-10-13 13:34
 * @Version: 1.0
 */
public class TestBirthday {
    public static void main(String[] args) throws Exception {

       /*根据身份证号获取这个出生日期，性别，年龄以及省份我们输入的身份证号必须要是正确有效的
       * 身份证号，否则的话就计算不出来结果。*/
         //IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor("410329199603290518");
       /* IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor("460003198404090622");
        System.out.println("出生日期:"+idcardInfo.getYear()+"-"+idcardInfo.getMonth()+"-"+idcardInfo.getDay());
        System.out.println("性别:"+idcardInfo.getGender());
        System.out.println("年龄:"+idcardInfo.getAge());
        System.out.println("省份:"+idcardInfo.getProvince());*/

        /* String s="460003198404090622";
        System.out.println(s.substring(6,14));*/
        //String s="460003840409062";


        String s="460003841009062";
        System.out.println(s.substring(6,12));
        s="19"+s.substring(6,12);
        System.out.println(s);
        String year=s.substring(0,4);
        String month=s.substring(4,6);
        if("0".equals(month.substring(0,1))){
           month=s.substring(5,6);
        }
        String day=s.substring(6,8);
        if("0".equals(day.substring(0,1))){
            day=s.substring(7,8);
        }
        String birthday=year+"-"+month+"-"+day;
        System.out.println(birthday);
    }
}

