package main;

public enum CertTypeEnum {
    /*证件类型*/
    MAINlANDIDCARD("1","内地居民身份证"),
    HONGKONGIDCARD("2","香港居民身份证"),
    MACAOIDCARD("3","澳门居民身份证"),
    TAIWANIDCARD("4","台湾居民身份证"),
    PASSPORT("5","护照"),
    MILITARYID("6","军官证"),
    SOLDIERSID("7","士兵证"),
    NATIONALTRAVELID("8","其他有效国籍旅行证件");
    private String code;
    private String message;

    CertTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(String code) {
        //values():返回enum实例的数组，而且该数组中的元素严格保持在enum中
        // 声明时的顺序保持一致
        CertTypeEnum[] values = CertTypeEnum.values();
        for (CertTypeEnum value : values) {
            System.out.println(value);
            System.out.println(value.getMessage());
            if (value.code.equals(code)) {
                return value.message;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CertTypeEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
