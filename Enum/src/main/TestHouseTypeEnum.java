package main;

/**
 * @ClassName: TestHouseTypeEnum
 * @Description:
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-11-29 16:51
 * @Version: 1.0
 */
public class TestHouseTypeEnum {
    public static void main(String[] args) {
        String messageByCode = HouseTypeEnum.getMessageByCode("2");
        System.out.println(messageByCode);
        String codeByMessage = ChangeHouseTypeEnum.getCodeByMessage(messageByCode);
        System.out.println(codeByMessage);
       /* String messageByCode1 = HouseTypeEnum.getMessageByCode("2");
        System.out.println(messageByCode1);*/


    }

}
