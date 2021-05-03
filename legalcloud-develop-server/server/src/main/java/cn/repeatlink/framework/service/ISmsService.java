/**
 * 
 */
package cn.repeatlink.framework.service;

import java.util.Map;

/**
 * @author LAI
 * @date 2020-12-01 10:53
 */
public interface ISmsService {

	/**
	 * @param tel
	 * @param msg
	 */
	void sendSms(String tel, String msg);

	/**
	 * @param tel
	 * @param msg
	 * @param templateId
	 */
	void sendSms(String tel, String msg, String templateId);

	/**
	 * @param tel
	 * @param msg
	 * @param templateId
	 * @param params
	 */
	void sendSms(String tel, String msg, String templateId, Map<String, String> params);

	/**
	 * @param tel
	 * @param msg
	 * @param templateId
	 * @param params
	 * @param ttl
	 */
	void sendSms(String tel, String msg, String templateId, Map<String, String> params, Integer ttl);

}
