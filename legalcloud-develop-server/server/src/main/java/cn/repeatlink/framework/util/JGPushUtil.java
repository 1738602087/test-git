package cn.repeatlink.framework.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * @author Lsq
 * @date 2020-11-24 13:41
 */
@SuppressWarnings({"deprecation", "restriction"})
public class JGPushUtil {
    private static final Logger log = LoggerFactory.getLogger(JGPushUtil.class);
    private static String masterSecret = "";
    private static String appKey = "";
    private static String pushUrl = "";
    private static boolean apns_production = false;//false开发环境，true生产环境（仅对ios有效）
    private static int time_to_live = 86400;//默认存活时间一天（ios无效）

    //初始化参数
    static void initialize() {
        masterSecret = SysConfigUtil.instance().getConfigValue("jg.push.masterSecret");
        appKey = SysConfigUtil.instance().getConfigValue("jg.push.appKey");
        pushUrl = SysConfigUtil.instance().getConfigValue("jg.push.pushUrl");
        apns_production = SysConfigUtil.instance().getValue("jg.push.apns_production", false);
        time_to_live = SysConfigUtil.instance().getValue("jg.push.time_to_live", 86400);
    }

    /**
     * 极光推送
     *
     * @param registration_id
     * @param alert
     */
    public static void jiguangPush(String registration_id, String alert) {
        try {
            initialize();
            String result = push(pushUrl, registration_id, alert, appKey, masterSecret, apns_production, time_to_live);
            JSONObject resData = JSONObject.fromObject(result);
            if (resData.containsKey("error")) {
                JSONObject error = JSONObject.fromObject(resData.get("error"));
                log.info("设备号【" + registration_id + "】的信息推送失败！");
                log.info("错误信息为：" + error.get("message").toString());
                return;
            }
            log.info("设备号为【" + registration_id + "】的信息推送成功！返回参数：" + resData);
        } catch (Exception e) {
            log.error("设备号为【" + registration_id + "】的信息推送失败！", e);
        }
    }

    /**
     * 组装推送Json串
     *
     * @param registration_id
     * @param alert
     * @return json
     */
    public static JSONObject generateJson(String registration_id, String alert, boolean apns_production, int time_to_live) {
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//推送平台
        platform.add("ios");
        platform.add("android");

        JSONObject audience = new JSONObject();//推送目标
        JSONArray registrationId = new JSONArray();
        registrationId.add(registration_id);
        audience.put("registration_id", registrationId);//按设备号推送

        JSONObject notification = new JSONObject();//通知内容

        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);//推送内容
        ios.put("sound", "default");
        ios.put("badge", "+1");
        notification.put("ios", ios);

        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert);
        android.put("builder_id", 1);
        notification.put("android", android);

        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", time_to_live);//过期时间
        options.put("apns_production", apns_production);//apple推送环境

        json.put("platform", platform);
        json.put("audience", audience);
        json.put("notification", notification);
        json.put("options", options);

        return json;

    }

    /**
     * 推送方法-调用极光API
     *
     * @param reqUrl
     * @param registration_id
     * @param alert
     * @return result
     */
    public static String push(String reqUrl, String registration_id, String alert, String appKey, String masterSecret, boolean apns_production, int time_to_live) {
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl, generateJson(registration_id, alert, apns_production, time_to_live).toString(), "UTF-8", authorization);
    }

    /**
     * 发送Post请求（json格式）
     *
     * @param reqURL
     * @param data
     * @param encodeCharset
     * @param authorization
     * @return result
     */
    @SuppressWarnings({"resource"})
    public static String sendPostRequest(String reqURL, String data, String encodeCharset, String authorization) {
        HttpPost httpPost = new HttpPost(reqURL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
            StringEntity entity = new StringEntity(data, encodeCharset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization", authorization.trim());
            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), encodeCharset);
        } catch (Exception e) {
            log.error("请求通信[" + reqURL + "]时遇到异常", e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }

    /**
     * BASE64加密工具
     */
    public static String encryptBASE64(String str) {
        byte[] key = str.getBytes();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encodeBuffer(key);
    }
}
