/**
 * 
 */
package cn.repeatlink.module.usercenter.service;

import com.jfinal.kit.Kv;

/**
 * @author LAI
 * @date 2020-09-27 14:13
 */
public interface IValidateCodeService {

	/**
	 * @param email 目标
	 * @param subject 主题
	 * @param template 内容模版
	 */
	void sendCode(String target, String subject, String template);
	
	/**
	 * @param target 目标
	 * @param subject 主题
	 * @param template 内容模版
	 * @param minute 验证码有效分钟数
	 */
	void sendCode(String target, String subject, String template, int minute);

	/**
	 * @param email
	 * @param code
	 * @return
	 */
	boolean validate(String target, String code);

	/**
	 * 通过Enjoy模版引擎渲染模版发送验证码
	 * @param target 目标
	 * @param subjectTemplate 主题模版
	 * @param contentTemplate 内容模板
	 * @param minute 有效分钟数
	 * @param attrs 额外参数
	 */
	void sendCodeByEnjoy(String target, String subjectTemplate, String contentTemplate, int minute, Kv attrs);

}
