package main;

/*
 * 枚举是一种类，是一种特殊的类，特殊在它的对象是有限的几个常量对象(
 * 既然是类，那么我们就可以写类的属性和方法)。
 *
 * JDK1.5之后枚举的语法：
 * 1、如何声明枚举类型？
 * 【修饰符】  enum 枚举类型名{
 * 		常量对象列表
 * }
 *
 * 【修饰符】  enum 枚举类型名{
 * 		常量对象列表;
 * 		其他的成员列表
 * }
 * 说明：如果常量对象列表后面还有其他的成员，那么需要在常量对象列表后面加;进行分割
 *
 * 2、枚举类型不能继承其他类型，因为枚举类型有一个隐含的父类
 * java.lang.Enum(注意这里是大写，java8是严格区分大小写的)
 * 即Enum是所有 Java 语言枚举类型的公共基本类。
 *
 * （1）Enum类中有一个唯一的构造器
 * protected  Enum(String name, int ordinal)
 * 这个构造器不是程序员手动调用的，是编译器自动调用，在所有枚举类型的构造器的首行，
 * 帮我们自动调用，并且自动传入name和ordinal的值。
 *
 * name：就是常量对象名称
 * ordinal：就是常量对象的序号，其中初始常量序数为零
 *
 * （2）String name()
 * 		int ordinal()
 * （3）String toString()
 * 	在Enum类中重写了Object类的toString方法，返回常量对象名
 *
 * 自定义的枚举类型，在eclipse工具类中，不能自动生成toString方法。手动重写。
 * 因为eclipse认为，父类重写的toString方法的实现已经很完美了，就是返回常量对象名，已经能代表一切。
 *
 * 3、API中没有的方法，是编译器帮我们生成的方法
 * （1）枚举类型[] values()
 * （2）枚举类型   valueOf(String name)
 */
public class TestEnum2 {
    public static void main(String[] args) {
        Season s = Season.SPRING;
        //SPRING:春暖花开
        System.out.println(s);//自动调用toString
        //SPRING  name() 返回此枚举常量的名称，在其枚举声明中对其进行声明。返回值为String类型
        System.out.println(s.name());
        //0 ordinal() 返回枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零）。返回值为Int类型
        System.out.println(s.ordinal());
        System.out.println("-----------------");

        Season[] all = Season.values();
        for (int i = 0; i < all.length; i++) {
            System.out.println(all[i]);
        }

        System.out.println("-----------------");
        //枚举类型   valueOf(String name) 得到枚举类型的对象
        Season spring = Season.valueOf("SPRING");
        System.out.println(spring);
 }
}