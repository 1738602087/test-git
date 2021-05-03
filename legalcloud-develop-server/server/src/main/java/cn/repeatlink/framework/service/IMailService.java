/**
 * 
 */
package cn.repeatlink.framework.service;

import cn.repeatlink.framework.common.Constant.MailSenderType;

/**
 * @author LAI
 * @date 2020-09-18 10:22
 */
public interface IMailService {

	/**
	 * @param to
	 * @param subject
	 * @param text
	 */
	void sendMail(String[] to, String subject, String text);

	/**
	 * @param senderType
	 * @param to
	 * @param subject
	 * @param text
	 */
	void sendMail(MailSenderType senderType, String[] to, String subject, String text);

}
