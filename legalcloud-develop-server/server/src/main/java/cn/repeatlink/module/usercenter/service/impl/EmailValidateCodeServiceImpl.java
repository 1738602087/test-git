/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.engine.enjoy.EnjoyEngine;
import cn.repeatlink.common.entity.EmailValidateCode;
import cn.repeatlink.common.mapper.EmailValidateCodeMapper;
import cn.repeatlink.framework.service.IMailService;
import cn.repeatlink.module.general.factory.GeneralRegValidateCodeServiceFactory;
import cn.repeatlink.module.judicial.common.JudicialResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.util.IDUtil;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;

/**
 * 通用邮箱验证码服务类
 * @author LAI
 * @date 2020-09-27 14:13
 */

@Service("emailValidateCodeService")
public class EmailValidateCodeServiceImpl implements IValidateCodeService {
	
	@Autowired
	private EmailValidateCodeMapper emailValidateCodeMapper;
	
	@Autowired
	private IMailService mailService;
	
	@Override
	public boolean validate(String email, String code) {
		final String verifyCode = this.getLastVerifyCode(email);
		if(!StringUtils.equals(code, verifyCode)) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.email.code.invalid");
		}
		return true;
	}
	
	@Override
	public void sendCode(String email, String subject, String template) {
		this.sendCode(email, subject, template, GeneralRegValidateCodeServiceFactory.instance().validCodeMinute());
	}
	
	@Override
	public void sendCode(String email, String subject, String template, int minute) {
		// 检查发送可行性
		this.checkSendCode(email);
		
		EmailValidateCode code = new EmailValidateCode();
		code.setCode(GeneralRegValidateCodeServiceFactory.instance().buildCode());
		code.setEmail(email);
		code.setId(IDUtil.nextID());
		code.setCreateTime(new Date());
		code.setInvalidTime(DateUtil.offsetMinute(code.getCreateTime(), minute));
		this.emailValidateCodeMapper.insert(code);
		// 发送邮件
		this.mailService.sendMail(new String[]{ email }, subject, StrUtil.indexedFormat(template, code.getCode(), minute));
	}
	
	@Override
	public void sendCodeByEnjoy(String email, String subjectTemplate, String contentTemplate, int minute, Kv attrs) {
		// 检查发送可行性
		this.checkSendCode(email);
		
		EmailValidateCode code = new EmailValidateCode();
		code.setCode(GeneralRegValidateCodeServiceFactory.instance().buildCode());
		code.setEmail(email);
		code.setId(IDUtil.nextID());
		code.setCreateTime(new Date());
		code.setInvalidTime(DateUtil.offsetMinute(code.getCreateTime(), minute));
		this.emailValidateCodeMapper.insert(code);
		
		// 使用Enjoy模版渲染
		if(attrs == null) {
			attrs = Kv.create();
		}
		attrs.set("email", email).set("code", code.getCode()).set("minute", minute);
		EnjoyEngine engine = new EnjoyEngine();
		String subject = engine.getTemplate(subjectTemplate).render(attrs);
		String content = engine.getTemplate(contentTemplate).render(attrs);
		this.mailService.sendMail(new String[] { email }, subject, content);
	}
	
	private void checkSendCode(String email) {
		if(StringUtils.isBlank(email) || !Validator.isEmail(email)) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.email.invalid");
		}
		Record record = Db.findFirst("select * from email_validate_code where email=? order by create_time desc", email);
		int ttl = GeneralRegValidateCodeServiceFactory.instance().ttlCodeSecond();
		if(record != null) {
			Date date = record.getDate("create_time");
			if(DateUtil.between(date, new Date(), DateUnit.SECOND) < ttl) {
				throw new JudicialRuntimeException(new JudicialResultCode("", "judicial.auth.reg.send.code.mail.frequently"));
			}
		}
	}
	
	private String getLastVerifyCode(String email) {
		Record record = Db.findFirst("SELECT * from email_validate_code where email = ? and invalid_time > ? ORDER BY invalid_time DESC limit 1"
				, email, new Date());
		String code = null;
		if(record != null) {
			code = record.getStr("code");
		}
		return code;
	}

}
