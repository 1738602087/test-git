package main;

/**
 * @EnumName: ChangeHouseTypeEnum
 * @Description:
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-11-29 17:06
 * @Version: 1.0
 */
public enum ChangeHouseTypeEnum {
    REPLACEMENTBUILDING("945","回迁房"),
    REFORMBUILDING("948","房改房"),
    COLLECTBUILDING("9","集资房"),
    COMMODITYBUILDING("1","商品房"),
    SELFBUILDING("944","自建房");
    private String code;
    private String message;

    ChangeHouseTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    /**
     * @Description: 根据code找message
     * @param code
     * @return: String
     * @Author: xiangyang.li@funi.com
     * @date: 2021/11/29 17:39
     * @Version: 1.0
     */
    public static String getMessageByCode(String code) {
        //values():返回enum实例的数组，而且该数组中的元素严格保持在enum中
        // 声明时的顺序保持一致
        ChangeHouseTypeEnum[] values = ChangeHouseTypeEnum.values();
        for (ChangeHouseTypeEnum value : values) {
            System.out.println(value);
            if (value.code.equals(code)) {
                return value.message;
            }
        }
        return null;
    }
    /**
     * @Description: 根据message找code
     * @param message 
     * @return: String 
     * @Author: xiangyang.li@funi.com
     * @date: 2021/11/29 17:39
     * @Version: 1.0       
     */
    public static String getCodeByMessage(String message) {
        //values():返回enum实例的数组，而且该数组中的元素严格保持在enum中
        // 声明时的顺序保持一致
        ChangeHouseTypeEnum[] values = ChangeHouseTypeEnum.values();
        for (ChangeHouseTypeEnum value : values) {
            System.out.println(value);
            if (value.message.equals(message)) {
                return value.code;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HouseTypeEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
