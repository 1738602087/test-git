package cn.repeatlink.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.repeatlink.framework.bean.ButtonVo;
import cn.repeatlink.framework.bean.Notification;
import cn.repeatlink.framework.common.Constant.PushRegistrationType;
import cn.repeatlink.framework.scheduler.JGRetryJob;
import cn.repeatlink.framework.service.IPushService;
import cn.repeatlink.framework.util.NewJGPushUtil;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;

@Service
public class PushServiceImpl implements IPushService {

    //身份类别码：1司法书士，2一般用户
    public static final int RegistrationJudType = PushRegistrationType.JUD;
    public static final int RegistrationGenType = PushRegistrationType.GEN;

    public static final Timer timer = new Timer();
    Map<String, String> hashMap = new HashMap<>();

    /*{
        hashMap.put("all", "false");
        hashMap.put("sys", "false");
    }*/

    @Override
    public void PushJudicialNotification(String userId, String alert, String type, String title) {

        //本地查询该用户设备码
        List<Record> records = Db.find("select registration_id from user_registration where user_id=? and type=?", userId, RegistrationJudType);

        //判断该用户是否已本地注册
        if (records == null)
            return;
//            throw JudicialRuntimeException.build("judicial.user1.registration_id.notRegistered.fail");

        List<String> registration_ids = new ArrayList<>();

        //判断设备码是否为空
        for (Record record : records) {
            String registration_id = record.getStr("registration_id");
            if (StrUtil.isNotBlank(registration_id))
                registration_ids.add(registration_id);
        }
        if (registration_ids.size() <= 0)
            throw JudicialRuntimeException.build("judicial.user1.registration_id.push.notFind");

        //校验推送权限字段
        if (judgeNotificationType(userId, type))
            throw JudicialRuntimeException.build("judicial.user1.NotificationType.push.notPermissions");

        //向该设备推送消息
        sendAndRetry(RegistrationJudType, registration_ids, alert, title);
    }

    @Override
    public void PushGeneralNotification(String userId, String alert, String type, String title) {

        //本地查询该用户设备码
        List<Record> records = Db.find("select registration_id from user_registration where user_id=? and type=?", userId, RegistrationGenType);

        //判断该用户是否已本地注册
        if (records == null)
            return;
//            throw JudicialRuntimeException.build("judicial.user2.registration_id.notRegistered.fail");

        List<String> registration_ids = new ArrayList<>();

        //判断设备码是否为空
        for (Record record : records) {
            String registration_id = record.getStr("registration_id");
            if (StrUtil.isNotBlank(registration_id))
                registration_ids.add(registration_id);
        }
        if (registration_ids.size() <= 0)
            throw JudicialRuntimeException.build("judicial.user2.registration_id.push.notFind");

        //校验推送权限字段
        if (judgeNotificationType(userId, type))
            throw JudicialRuntimeException.build("judicial.user2.NotificationType.push.notPermissions");

        //向该设备推送消息
        sendAndRetry(RegistrationGenType, registration_ids, alert, title);
    }

    @Override
    public void VerifyRegistrationId(String userId, String registration_id, int type, String os) {

        //去除前后空格
        String str = registration_id.trim();

        //判断设备码参数是否为空
        if (StrUtil.isBlank(registration_id))
            throw JudicialRuntimeException.build("judicial.user.registration_id.NullParameter.VerifyFail");

        try {
        	List<Record> list = Db.find("select user_id,id from user_registration where registration_id=? and user_id=? and type=? ", registration_id, userId, type);
        	if(list == null || list.isEmpty()) {
        		//存储新设备码
                SaveRegistrationId(userId, str, type, os);
        	} else if(list.size() > 1) {
        		// 去除重复设备号
        		for(int i = 1; i < list.size(); i++) {
        			repeatRegistrationId(list.get(i).getStr("id"));
        		}
        	}
            // 一个用户一个最新设备，一个设备一个最新用户
        	Db.update("delete from user_registration where (registration_id=? and type=? and user_id<>?) or (user_id=? and type=? and registration_id<>?)"
        			, registration_id, type, userId, userId, type, registration_id);
        } catch (Exception e) {
            throw JudicialRuntimeException.build("judicial.user.registration_id.verify.fail");
        }
    }

    @Override
    public void updateNotificationType(String userId, ButtonVo vo) {
        StrBuilder strBuilder = new StrBuilder();
        if (StrUtil.equals(vo.getAll(), "true"))
            strBuilder.append("all,");
        if (StrUtil.equals(vo.getSys(), "true"))
            strBuilder.append("sys");
        String mess_type = strBuilder.toString();

        //修改推送通知权限字段
        if (Db.update("update user_registration set mess_type=? where user_id=?", mess_type, userId) == 0) {
            throw JudicialRuntimeException.build("judicial.user.NotificationType.update.fail");
        }
    }

    @Override
    public ButtonVo selectNotificationType(String userId) {

        //查询推送通知权限
        try {
            Record record = Db.findFirst("select DISTINCT(mess_type) from user_registration where user_id=?", userId);
            String str = record.getStr("mess_type");
            String[] split = str.trim().split(",");
            ButtonVo buttonVo = new ButtonVo("false", "false");
            if (!str.contains("all"))
                return buttonVo;
            if (str.contains("all"))
                buttonVo.setAll("true");
            if (str.contains("sys"))
                buttonVo.setSys("true");
            if (StrUtil.isBlank(str))
                return buttonVo;
            return buttonVo;
            /*for (String key : hashMap.keySet()) {
                hashMap.put(key, "false");
            }
            if (str.contains("all")) {
                for (String key : hashMap.keySet()) {
                    hashMap.put(key, "true");
                }
            } else {
                for (String s : split) {
                    if (StrUtil.isNotBlank(s))
                        hashMap.put(s, "true");
                }
            }
            return hashMap;*/
        } catch (NullPointerException e) {
            throw JudicialRuntimeException.build("judicial.user.registration_id.notRegistered.fail");
        }
    }

    @Override
    public boolean judgeNotificationType(String userId, String messType) {

        //查询用户推送权限字段
        Record record = Db.findFirst("select DISTINCT(mess_type) from user_registration where user_id=?", userId);
        String messTypes = record.getStr("mess_type");

        //判断是否为空,是否无权限
        if (StrUtil.isBlank(messTypes) || StrUtil.equals(Notification.Not_NotificationType, messTypes)) return true;

        //判断是否拥有对应推送权限
        if (messTypes.contains(Notification.All_NotificationType) || messTypes.contains(messType)) return false;

        return true;
    }


    public void SaveRegistrationId(String userId, String registration_id, int type, String os) {

        //存储用户设备码
        Record record = new Record();
        record.set("user_id", userId);
        record.set("registration_id", registration_id);
        record.set("type", type);
        record.set("mess_type", Notification.All_NotificationType);
        record.set("last_time", new Date());
        record.set("os", os);

        if (!Db.save("user_registration", record)) {
            throw JudicialRuntimeException.build("judicial.user.registration_id.save.fail");
        }
    }

    public void SaveRegistrationIdMore(String userId, String registration_id, int type, String os) {

        Record record1 = Db.findFirst("select DISTINCT(mess_type) from user_registration where user_id=?", userId);
        String notificationType = record1.getStr("mess_type");

        //存储用户设备码
        Record record = new Record();
        record.set("user_id", userId);
        record.set("registration_id", registration_id);
        record.set("type", type);
        record.set("mess_type", notificationType);
        record.set("last_time", new Date());
        record.set("os", os);

        if (!Db.save("user_registration", record)) {
            throw JudicialRuntimeException.build("judicial.user.registration_id.save.fail");
        }
    }

    private void repeatRegistrationId(String id) {
        if (!Db.deleteById("user_registration", id))
            throw JudicialRuntimeException.build("judicial.user.registration_id.delete.fail");
    }

    //推送
    public void sendAndRetry(int type, List<String> registration_id, String alert, String title) {
        String result = NewJGPushUtil.send(type, registration_id, alert, title);
        if (StrUtil.equals(result, "APIConnectionException")) {
            //定时一分钟重试推送
            timer.schedule(new JGRetryJob(type, registration_id, alert, title), 1000 * 60);
        }
    }
}
