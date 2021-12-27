package main;

/**
 * @ClassName: Student
 * @Description: 学生实体类
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-12-27 09:38
 * @Version: 1.0
 */
public class Student {

    /** 姓名 */
    private String name;
    
    /** 年龄 */
    private String age;

   /** 是否成年 */
   private Boolean isAdult;


    public Boolean getIsAdult() {
        return this.isAdult;
    }

    public void setIsAdult(Boolean adult) {
        this.isAdult = adult;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    
}
