/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import cn.repeatlink.common.entity.UserTelValidateCode;
import cn.repeatlink.common.mapper.UserTelValidateCodeMapper;
import cn.repeatlink.module.general.common.Constant.ValidateCodeServiceType;
import cn.repeatlink.module.general.factory.GeneralRegValidateCodeServiceFactory;
import cn.repeatlink.module.judicial.common.JudicialResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.util.IDUtil;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;

/**
 * 通用手机号验证码服务类
 * @author LAI
 * @date 2020-09-27 14:13
 */

@Service("telValidateCodeService")
public class TelValidateCodeServiceImpl implements IValidateCodeService {
	
	@Autowired
	private UserTelValidateCodeMapper userTelValidateCodeMapper;
	
	@Override
	public boolean validate(String tel, String code) {
		final String verifyCode = this.getLastVerifyCode(tel);
		if(!StringUtils.equals(code, verifyCode)) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.email.code.invalid");
		}
		return true;
	}
	
	@Override
	public void sendCode(String tel, String subject, String template) {
		this.sendCode(tel, subject, template, GeneralRegValidateCodeServiceFactory.instance().validCodeMinute());
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public void sendCode(String tel, String subject, String template, int minute) {
		if(StringUtils.isBlank(tel)) {
			throw JudicialRuntimeException.build("usercenter.user.auth.reg.error.tel.invalid");
		}
		Record record = Db.findFirst("select * from user_tel_validate_code where tel=? order by create_time desc", tel);
		int ttl = GeneralRegValidateCodeServiceFactory.instance().ttlCodeSecond();
		if(record != null) {
			Date date = record.getDate("create_time");
			if(DateUtil.between(date, new Date(), DateUnit.SECOND) < ttl) {
				throw new JudicialRuntimeException(new JudicialResultCode("", "judicial.auth.reg.send.code.mail.frequently"));
			}
		}
		
		UserTelValidateCode code = new UserTelValidateCode();
		code.setCode(GeneralRegValidateCodeServiceFactory.instance().buildCode());
		code.setTel(tel);
		code.setId(IDUtil.nextID());
		code.setCreateTime(new Date());
		code.setInvalidTime(DateUtil.offsetMinute(code.getCreateTime(), minute));
		code.setSendStatus("REQ");
		this.userTelValidateCodeMapper.insert(code);
		// 发送短信
		Kv ret = this.sendSms(tel, code.getCode(), minute, subject, template);
		if(ret == null) {
			code.setSendStatus("FAIL");
			code.setSendResult("sms send service is null.");
			
		} else {
			if("ok".equals(ret.getStr("status"))) {
				code.setSendStatus("SEND");
			} else {
				code.setSendStatus("FAIL");
				code.setSendResult(StringUtils.left(ret.getStr("errorMsg"), 233));
			}
			code.setLinkId(ret.getStr("link_id"));
		}
		this.userTelValidateCodeMapper.updateByPrimaryKey(code);
		
		if("FAIL".equals(code.getSendStatus())) {
			throw JudicialRuntimeException.build("general.auth.reg.send.code.fail");
		}
	}
	
	private String getLastVerifyCode(String tel) {
		Record record = Db.findFirst("SELECT * from user_tel_validate_code where tel = ? and invalid_time > ? ORDER BY invalid_time DESC limit 1"
				, tel, new Date());
		String code = null;
		if(record != null) {
			code = record.getStr("code");
		}
		return code;
	}
	
	private Kv sendSms(String tel, String code, int minute, String subject, String template) {
		String serviceType = GeneralRegValidateCodeServiceFactory.instance().serviceType();
		if(ValidateCodeServiceType.SMS_ZH.equals(serviceType)) {
			return this.sendSmsZh(tel, code, minute);
		}
		if(ValidateCodeServiceType.SMS_JP.equals(serviceType)) {
			return this.sendSmsJp(tel, code, minute, subject, template);
		}
		
		return null;
	}
	
	// 国内短信发送
	private Kv sendSmsZh(String tel, String code, int minute) {
		Kv ret = Kv.by("status", "fail");
		try {
			String templateId = GeneralRegValidateCodeServiceFactory.instance().smsTemplateId();
			SMSClient smsClient = new SMSClient(GeneralRegValidateCodeServiceFactory.instance().jiguangSmsMasterSecret(), GeneralRegValidateCodeServiceFactory.instance().jiguangSmsAppKey());
			SMSPayload payload = SMSPayload.newBuilder()
					.setMobileNumber(tel)
					.setTempId(Integer.valueOf(templateId))
					.addTempPara("code", code)
					.addTempPara("minute", minute + "")
					.build();
			SendSMSResult result = smsClient.sendTemplateSMS(payload);
			if(result != null && StringUtils.isNotBlank(result.getMessageId())) {
				ret.set("status", "ok");
				ret.set("link_id", result.getMessageId());
			} else {
				ret.set("errorMsg", StringUtils.left(result.getOriginalContent(), 233));
			}
			try {
				smsClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (APIConnectionException e) {
			e.printStackTrace();
			ret.set("errorMsg", "Connection error");
		} catch (APIRequestException e) {
			e.printStackTrace();
			ret.set("errorMsg", e.getMessage());
		}
		
		return ret;
	}
	
	// 日本短信发送
	private Kv sendSmsJp(String tel, String code, int minute, String subject, String template) {
		Kv ret = Kv.by("status", "fail");
		String from = GeneralRegValidateCodeServiceFactory.instance().twilioFrom();
		if(StringUtils.isBlank(from)) {
			ret.set("errorMsg", "twilio from is empty.");
			return ret;
		}
		try {
			// 准备内容
			String body = "【" + subject + "】" + StrUtil.indexedFormat(template, code, minute);
			// 使用Twilio发送短信
			Twilio.init(GeneralRegValidateCodeServiceFactory.instance().twilioAccountSid(), GeneralRegValidateCodeServiceFactory.instance().twilioAuthToken());
			com.twilio.type.PhoneNumber to = new com.twilio.type.PhoneNumber(tel);
			from = from.trim();
			String tempFrom = from.toLowerCase();
			MessageCreator messageCreator = null;
			// 发送人可使用两种方式配置
			if(tempFrom.startsWith("phonenumber_")) {
				String target = from.substring("phonenumber_".length());
				messageCreator = Message.creator(to, new com.twilio.type.PhoneNumber(target) , body);
			} else if(tempFrom.startsWith("servicesid_")) {
				String target = from.substring("servicesid_".length());
				messageCreator = Message.creator(to, target, body);
			}
			// 发送，并处理结果
			Message message = messageCreator.create();
			if(message != null && message.getErrorCode() == null && StringUtils.isNotBlank(message.getSid())) {
				ret.set("status", "ok");
				ret.set("link_id", message.getSid());
			} else {
				ret.set("errorMsg", message.getErrorCode() + ": " + message.getErrorMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.set("errorMsg", e.getMessage());
		}
		
		return ret;
	}

	@Override
	public void sendCodeByEnjoy(String email, String subjectTemplate, String contentTemplate, int minute, Kv attrs) {
		// 暂不支持
		
	}

}
