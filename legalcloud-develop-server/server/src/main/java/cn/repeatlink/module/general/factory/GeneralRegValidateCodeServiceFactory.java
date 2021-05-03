/**
 * 
 */
package cn.repeatlink.module.general.factory;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.hutool.core.util.RandomUtil;
import cn.repeatlink.framework.sms.SmsConfig;
import cn.repeatlink.framework.util.SpringBeanFactory;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.general.common.Define.ConfigKeys;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;

/**
 * @author LAI
 * @date 2020-12-01 11:17
 */

@Component
public class GeneralRegValidateCodeServiceFactory {
	
	@Resource(name = "emailValidateCodeService")
	private IValidateCodeService emailValidateCodeService;
	
	@Resource(name = "telValidateCodeService")
	private IValidateCodeService telValidateCodeService;
	
	
	private static GeneralRegValidateCodeServiceFactory factory = null;
	
	public static GeneralRegValidateCodeServiceFactory instance() {
		if(factory == null) {
			synchronized (GeneralRegValidateCodeServiceFactory.class) {
				if(factory == null) {
					factory = SpringBeanFactory.getBean(GeneralRegValidateCodeServiceFactory.class);
				}
			}
		}
		return factory;
	}
	
	public IValidateCodeService getService() {
		String serviceType = this.serviceType();
		if("email".equalsIgnoreCase(serviceType)) {
			return emailValidateCodeService;
		} else if(serviceType !=  null && serviceType.trim().startsWith("sms_")) {
			return telValidateCodeService;
		}
		
		return null;
	}

	public String serviceType() {
		String value = SysConfigCacheUtil.instance().getValue(ConfigKeys.GENERAL_USER_REG_VALIDATECODE_TYPE);
		if(value != null) {
			value = value.trim().toLowerCase();
		}
		return value;
	}
	
	public String smsTemplateId() {
		String serviceType = this.serviceType();
		if(serviceType != null) {
			serviceType = serviceType.trim();
			if(serviceType.startsWith("sms_")) {
				if(serviceType.equals("sms_zh")) {
					return SysConfigCacheUtil.instance().getValue(ConfigKeys.GENERAL_USER_REG_VALIDATECODE_SMS_JIGUANG_TEMPLATE_ID);
				}
				if(serviceType.equals("sms_jp")) {
					return SysConfigCacheUtil.instance().getValue(ConfigKeys.GENERAL_USER_REG_VALIDATECODE_SMS_JP_TEMPLATE_ID);
				}
			}
		}
		return null;
	}
	
	public String buildCode() {
		return RandomUtil.randomInt(1001, 9999) + "";
	}
	
	public int validCodeMinute() {
		return SysConfigCacheUtil.instance().getValue(ConfigKeys.GENERAL_USER_REG_VALIDATECODE_VALID_MINUTE, 5);
	}
	
	public int ttlCodeSecond() {
		return SysConfigCacheUtil.instance().getValue(ConfigKeys.GENERAL_USER_REG_VALIDATECODE_TTL_SECOND, 60);
	}
	
	public String jiguangSmsMasterSecret() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_JIGUANG_MASTER_SECRET);
	}
	
	public String jiguangSmsAppKey() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_JIGUANG_APP_KEY);
	}
	
	public String twilioAccountSid() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_TWILIO_ACCOUNT_SID);
	}
	
	public String twilioAuthToken() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_TWILIO_AUTH_TOKEN);
	}
	
	public String twilioFrom() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_TWILIO_FROM);
	}
	
	public String twilioBodyTemplate() {
		return SysConfigCacheUtil.instance().getValue(SmsConfig.Keys.SMS_SERVICE_TWILIO_BODY_TEMPLATE);
	}
	
}
