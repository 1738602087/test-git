package com.lxy.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {

    public  static String getJson(Object object){
        return getJson(object,"yyyy-MM-dd HH:mm:ss");
    }
    /*因为在我们的UserController中我们的这个获取时间方法的内容太多重复，所以我们可以将
    * 重复代码抽成一个工具类。java的思想就是这种
    *
    * 我们在方法里面抛出异常，这里我们在工具类中使用这个捕获try-catch*/
    public static String getJson(Object object,String dateFormat){
        ObjectMapper mapper = new ObjectMapper();
        /*如果我们不想让它返回时间戳，我们就需要关闭它的时间戳功能*/
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        /*时间格式化问题，自定义日期格式对象，这里需要特别注意的一点是
         * 这个日期格式为这个"yyyy-MM-dd HH:mm:ss"，表示月份的M应该大写，表示分钟的
         * m应该小写。所以我们需要注意，否则两个都是大M或者是小m最后结果就会出错*/
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        /*让mapper指定日期时间格式为SimpleDateFormat*/
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
