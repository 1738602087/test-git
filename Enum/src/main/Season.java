package main;

public enum Season {
    /*我们是可以在Enum中同时定义这个有参的构造和无参的构造换句话来说就是这个枚举类中的常量对象
    列表中的常量对象可以同时包含有值和无值的两种情况。*/
    //常量对象后面没有(值)就是调用无参构造
    //常量对象后面有(值)就是调用有参构造
    SPRING("春暖花开"),SUMMER,FALL,WINTER;

    private String description;

    private Season() {

    }

    private Season(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return name() + ":" + description;
    }

}