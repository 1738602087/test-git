package cn.repeatlink.framework.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;

/**
 * @author Lsq
 * @date 2020-12-3 17:41
 */

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.repeatlink.framework.common.Constant.PushRegistrationType;

public class NewJGPushUtil {

    private static final Logger log = LoggerFactory.getLogger(NewJGPushUtil.class);
    private static boolean apns_production;//false开发环境，true生产环境（仅对ios有效）
    private static int time_to_live;//默认存活时间一天（ios无效）
    private static JPushClient jpushClient;
    private static JPushClient jpushClientGeneral;

    //初始化参数
    private static void initialize() {
        String masterSecret = SysConfigUtil.instance().getConfigValue("jg.push.masterSecret");
        String appKey = SysConfigUtil.instance().getConfigValue("jg.push.appKey");
        String masterSecretGeneral = SysConfigUtil.instance().getConfigValue("jg.push.general.masterSecret");
        String appKeyGeneral = SysConfigUtil.instance().getConfigValue("jg.push.general.appKey");
        apns_production = SysConfigUtil.instance().getValue("jg.push.apns_production", false);
        time_to_live = SysConfigUtil.instance().getValue("jg.push.time_to_live", 86400);
        jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        jpushClientGeneral = new JPushClient(masterSecretGeneral, appKeyGeneral, null, ClientConfig.getInstance());
    }

    public static String getCids(int type) {
        initialize();
        String cid = "";

        try {
            //获取Cid池
            List<String> cids = null;
            if(type == PushRegistrationType.JUD) {
            	cids = jpushClient.getCidList(1, "push").cidlist;
            } else if(type == PushRegistrationType.GEN) {
            	cids = jpushClientGeneral.getCidList(1, "push").cidlist;
            }
            cid = cids.get(0);
        } catch (Exception e) {
            log.error("Cids is error：", e);
        }
        return cid;
    }

    public static String send(int type, List<String> registration_id, String alert, String title) {

        try {
        	String cid = getCids(type);
            //选择推送构造类型
            PushPayload payload = buildPush_android_ios_registrationId_alert(title, registration_id, alert, apns_production, time_to_live, cid);

            //分应用推送
            PushResult result = null;
            if(type == PushRegistrationType.JUD) {
            	result = jpushClient.sendPush(payload);
            } else if(type == PushRegistrationType.GEN) {
            	result = jpushClientGeneral.sendPush(payload);
            }
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            log.error("Connection error, should retry later", e);
            //重试
            return "APIConnectionException";

        } catch (APIRequestException e) {
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            return "APIRequestException";
        }
        return "successful";
    }


    //构造单个Android、ios通知推送
    private static PushPayload buildPush_android_ios_registrationId_alert(String title, List<String> registration_id, String alert, boolean apns_production, int time_to_live, String cid) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(registration_id))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .setAlert(alert)
                                .setBuilderId(1)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setSound("default")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apns_production)
                        .setTimeToLive(time_to_live)
                        .build())
                .setCid(cid)
                .build();
    }
}
