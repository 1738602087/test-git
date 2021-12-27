/**
 * @ClassName : TestCerTypeEnum  //类名
 * @Description :   //描述
 * @Author : lxy //作者
 * @Date: 2021-08-08 11:06  //时间
 */
package main;

public class TestCerTypeEnum {
    public static void main(String[] args) {
        String messageByCode = CertTypeEnum.getMessageByCode("1");
        System.out.println(messageByCode);
    }
}
