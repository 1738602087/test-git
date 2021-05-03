/**
 * 
 */
package cn.repeatlink.framework.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.repeatlink.framework.common.Constant.UserType;

/**
 * 访问权限指定
 * @author LAI
 * @date 2021-01-15 14:18
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface RLPermission {
	
	UserType[] userType() default {};
	
	boolean noCheck() default false;

}
