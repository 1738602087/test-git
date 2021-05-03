/**
 * 
 */
package cn.repeatlink.module.general.common;

/**
 * @author LAI
 * @date 2020-09-27 14:31
 */
public class Define {
	public static final String APP_URL_PREFIX = "/app/general";
	
	
	public static interface ConfigKeys {
		
		// 保护计划
		String ESTATE_PROTECTION_ORDER_DEFAULT_AMOUNT = "estate.protection.order.default.amount";
		String ESTATE_PROTECTION_ORDER_DEFAULT_TITLE = "estate.protection.order.default.title";
		String ESTATE_PROTECTION_ORDER_DEFAULT_REMARK = "estate.protection.order.default.remark";
		String ESTATE_PROTECT_ORDER_PERIOD = "estate.protect.order.period";
		
		String GENERAL_USER_REG_VALIDATECODE_VALID_MINUTE = "general.user.reg.validatecode.valid.minute";
		String GENERAL_USER_REG_VALIDATECODE_TTL_SECOND = "general.user.reg.validatecode.ttl.second";
		String GENERAL_USER_REG_VALIDATECODE_TYPE = "general.user.reg.validatecode.service.type";
		String GENERAL_USER_REG_VALIDATECODE_SMS_JIGUANG_TEMPLATE_ID = "general.user.reg.validatecode.sms.jiguang.template.id";
		String GENERAL_USER_REG_VALIDATECODE_SMS_JP_TEMPLATE_ID = "general.user.reg.validatecode.sms.jp.template.id";
		
		String SYSTEM_HELP_INFO_LEGAL_NAME= "system.help.info.legal.name";
		String SYSTEM_HELP_INFO_CONTACT_TEL= "system.help.info.contact.tel";
		
	}
}
