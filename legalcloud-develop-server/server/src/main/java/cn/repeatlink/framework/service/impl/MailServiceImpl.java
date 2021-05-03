/**
 * 
 */
package cn.repeatlink.framework.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.util.ArrayUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.mapper.SysConfigMapper;
import cn.repeatlink.framework.common.Constant.MailSenderType;
import cn.repeatlink.framework.service.IMailService;
import cn.repeatlink.module.judicial.util.IDUtil;

/**
 * 系统邮件发送服务
 * @author LAI
 * @date 2020-09-18 10:22
 */

@Service
public class MailServiceImpl implements IMailService {
	
	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	private final Map<MailSenderType, JavaMailSender> senders = new HashMap<>();

	@Override
	public void sendMail(String[] to, String subject, String text) {
		this.sendMail(MailSenderType.DEFAULT, to, null, null, subject, text, null, null);
	}
	
	@Override
	public void sendMail(MailSenderType senderType, String[] to, String subject, String text) {
		this.sendMail(senderType, to, null, null, subject, text, null, null);
	}
	
	public void sendMail(String[] to, String[] bcc, String[] cc, String subject, String text, String from, String replyTo) {
		this.sendMail(MailSenderType.DEFAULT, to, bcc, cc, subject, text, from, replyTo);
	}
	
	public void sendMail(MailSenderType senderType, String[] to, String[] bcc, String[] cc, String subject, String text, String from, String replyTo) {
		JavaMailSender javaMailSender = this.getJavaMailSender(senderType);
		if(javaMailSender == null) {
			// TODO 
		}
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setCc(cc);
		message.setBcc(bcc);
		message.setFrom(((JavaMailSenderImpl)javaMailSender).getUsername());
		message.setReplyTo(replyTo);
		Record record = this.insertSendRecord(senderType, to, bcc, cc, subject, text, from, replyTo);
		javaMailSender.send(message);
		this.updateSendRecord(record, Constant.STATUS_VALID);
	}
	
	private synchronized JavaMailSender getJavaMailSender(MailSenderType type) {
		JavaMailSender javaMailSender = this.senders.get(type);
		if(javaMailSender == null) {
			javaMailSender = this.createJavaMailSender(type);
			this.senders.put(type, javaMailSender);
		}
		return javaMailSender;
	}
	
	private JavaMailSender createJavaMailSender(MailSenderType type) {
		String keyPrefix = "mailsender." + StringUtils.lowerCase(type.name()) + ".config.";
		List<Record> list = Db.find("select * from sys_config where `status`=? and config_key like ?"
				, Constant.STATUS_VALID, "mailsender." + StringUtils.lowerCase(type.name()) + ".config.%");
		if(list == null || list.isEmpty()) return null;
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setDefaultEncoding("UTF-8");
		javaMailSender.setJavaMailProperties(new Properties());
		for (Record r : list) {
			String key = r.getStr("config_key").replaceFirst(keyPrefix, "");
			String value = r.getStr("config_value");
			switch(key) {
				case "host": {
					javaMailSender.setHost(value);
					break;
				}
				case "port": {
					javaMailSender.setPort(Integer.valueOf(value));
					break;
				}
				case "password": {
					javaMailSender.setPassword(value);
					break;
				}
				case "default.encoding": {
					javaMailSender.setDefaultEncoding(value);
					break;
				}
				case "username": {
					javaMailSender.setUsername(value);
					break;
				}
				case "protocol": {
					javaMailSender.setProtocol(value);
					break;
				}
				default: {
					if(key.startsWith("mail.")) {
						javaMailSender.getJavaMailProperties().put(key, value);
					}
				}
			}
		}
		try {
			javaMailSender.testConnection();
		} catch (MessagingException e) {
			e.printStackTrace();
			// 抛出配置异常
		}
		return javaMailSender;
	}
	
	/**
	 * 保存邮件发送记录
	 * @param senderType
	 * @param to
	 * @param bcc
	 * @param cc
	 * @param subject
	 * @param text
	 * @param from
	 * @param replyTo
	 */
	private Record insertSendRecord(MailSenderType senderType, String[] to, String[] bcc, String[] cc, String subject, String text, String from, String replyTo) {
		Record r = new Record();
		r.set("record_id", IDUtil.nextID());
		r.set("sender_type", senderType.name());
		r.set("sender", ((JavaMailSenderImpl)this.senders.get(senderType)).getUsername());
		r.set("to", ArrayUtil.join(to, ";"));
		r.set("bcc", ArrayUtil.join(bcc, ";"));
		r.set("cc", ArrayUtil.join(cc, ";"));
		r.set("subject", subject);
		r.set("text", text);
		r.set("from", from);
		r.set("reply_to", replyTo);
		r.set("send_time", new Date());
		r.set("status", Constant.STATUS_INVALID);
		r.set("created_by", -1);
		r.set("create_time", r.getDate("send_time"));
		Db.save("email_send_record", r);
		return r;
	}
	
	private Record updateSendRecord(Record record, Short status) {
		record.set("status", status);
		Db.update("email_send_record", "record_id", record);
		return record;
	}
	
	
}
