package cn.repeatlink.framework.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 提示信息国际化工具
 * 
 * @author tlq20
 *
 */
@Component
public class MessageUtil {

	
	private static MessageSource messageSource;
	
	@Autowired
	public MessageUtil(MessageSource messageSource) {
		if(MessageUtil.messageSource==null) {
			MessageUtil.messageSource=messageSource;
		}
	}
	public static String getMessage(String messagekey) {

		return messageSource.getMessage(messagekey, null, LocaleContextHolder.getLocale());
	}
	
	public static String getMessage(String messagekey, String... args) {

		return messageSource.getMessage(messagekey, args, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String messagekey, Locale locale, String... args) {
		
		return messageSource.getMessage(messagekey, args, locale);
	}
}
