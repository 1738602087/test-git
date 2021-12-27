/**
 * @ClassName : Gender  //类名
 * @Description :   //描述
 * @Author : lxy //作者
 * @Date: 2021-07-29 22:20  //时间
 */
package main;

public class Gender {
        //public：使得外面可以直接访问
        //static：使得可以使用“类名.”访问
        //final：前调这个对象是不可变
        public static final Gender NAN = new Gender("男");
        public static final Gender NV = new Gender("女");
        public static final Gender YAO = new Gender("妖");

        private String description;

        private Gender(String description){
            this.description = description;
        }

}
