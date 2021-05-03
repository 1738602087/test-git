/**
 * 
 */
package cn.repeatlink.framework.sms;

/**
 * @author LAI
 * @date 2020-12-23 11:49
 */
public class SmsConfig {
	
	public static interface Keys {
		
		String SMS_SERVICE_JIGUANG_MASTER_SECRET = "sms.service.jiguang.master.secret";
		String SMS_SERVICE_JIGUANG_APP_KEY = "sms.service.jiguang.app.key";
		
		String SMS_SERVICE_TWILIO_ACCOUNT_SID = "sms.service.twilio.account.sid";
		String SMS_SERVICE_TWILIO_AUTH_TOKEN = "sms.service.twilio.auth.token";
		String SMS_SERVICE_TWILIO_FROM = "sms.service.twilio.from";
		String SMS_SERVICE_TWILIO_BODY_TEMPLATE = "sms.service.twilio.body.template";
	}

}
