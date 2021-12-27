package main;

/**
 * @EnumName: HouseTypeEnum
 * @Description:
 * @Author: xiangyang.li@funi.com
 * @Date: 2021-11-29 16:39
 * @Version: 1.0
 */
public enum HouseTypeEnum {
    /** dic_type表中对应的类型 **/
    REPLACEMENTBUILDING("1","回迁房"),

    REFORMBUILDING("2","房改房"),

    COLLECTBUILDING("3","集资房"),

    COMMODITYBUILDING("4","商品房"),

    SELFBUILDING("5","自建房")
    ;
    private String code;

    private String message;

    HouseTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    /**
     * @Description: 根据code找value
     * @param code 
     * @return: String 
     * @Author: xiangyang.li@funi.com
     * @date: 2021/11/29 17:44
     * @Version: 1.0       
     */
    public static String getMessageByCode(String code) {
        //values():返回enum实例的数组，而且该数组中的元素严格保持在enum中
        // 声明时的顺序保持一致
        HouseTypeEnum[] values = HouseTypeEnum.values();
        for (HouseTypeEnum value : values) {
            System.out.println(value);
            if (value.code.equals(code)) {
                return value.message;
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
