/**
 * 
 */
package cn.repeatlink.framework.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.repeatlink.framework.service.ISmsService;

/**
 * SMS短信服务
 * @author LAI
 * @date 2020-12-01 10:53
 */

@Service
public class SmsServiceImpl implements ISmsService {
	
	@Override
	public void sendSms(String tel, String msg) {
		this.sendSms(tel, msg, null);
	}
	
	@Override
	public void sendSms(String tel, String msg, String templateId) {
		this.sendSms(tel, msg, templateId, null);
	}
	
	@Override
	public void sendSms(String tel, String msg, String templateId, Map<String, String> params) {
		this.sendSms(tel, msg, templateId, params, null);
	}
	
	@Override
	public void sendSms(String tel, String msg, String templateId, Map<String, String> params, Integer ttl) {
		
	}

}
