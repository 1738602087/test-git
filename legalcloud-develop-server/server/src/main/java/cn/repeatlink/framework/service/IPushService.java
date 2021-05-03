package cn.repeatlink.framework.service;

import cn.repeatlink.framework.bean.ButtonVo;

/**
 * @author Lsq
 * @date 2020-12-3 16:41
 */

public interface IPushService {

    void PushJudicialNotification(String userId, String alert, String type, String title);

    void PushGeneralNotification(String userId, String alert, String type, String title);

    void VerifyRegistrationId(String userId, String registration_id, int type, String os);

    void updateNotificationType(String userId, ButtonVo vo);

    ButtonVo selectNotificationType(String userId);

    boolean judgeNotificationType(String userId, String messType);
}