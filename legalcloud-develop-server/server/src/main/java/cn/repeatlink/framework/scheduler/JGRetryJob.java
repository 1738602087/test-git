package cn.repeatlink.framework.scheduler;

import java.util.List;
import java.util.TimerTask;

import cn.repeatlink.framework.util.NewJGPushUtil;

/**
 * @author Lsq
 * @date 2020-12-08 10:41
 * @TODO 推送重试任务
 */

public class JGRetryJob extends TimerTask {

	private int type;
    private List<String> registration_id;
    private String alert;
    private String title;

    public JGRetryJob(int type, List<String> registration_id, String alert, String title) {
    	this.type = type;
        this.registration_id = registration_id;
        this.alert = alert;
        this.title = title;
    }

    public JGRetryJob() {
    }

    @Override
    public void run() {
        NewJGPushUtil.send(type, registration_id, alert, title);
    }
}
