package cn.repeatlink.framework.util;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author tangliqiang
 *
 */
@Component
public class SpringBeanFactory {

	static ConfigurableListableBeanFactory beanFactory;

	@Autowired
	public SpringBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		if (SpringBeanFactory.beanFactory == null) {
			SpringBeanFactory.beanFactory = beanFactory;
		}
	}

	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return beanFactory.getBean(name, requiredType);
	}


	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		T result = (T) beanFactory.getBean(requiredType);
		return result;
	}

	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	public static boolean isSingleton(String name)  {
		return beanFactory.isSingleton(name);
	}

	
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}


	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

}
