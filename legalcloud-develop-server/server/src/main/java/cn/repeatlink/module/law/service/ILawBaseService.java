/**
 * 
 */
package cn.repeatlink.module.law.service;

import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.module.law.common.LawRuntimeException;

/**
 * @author LAI
 * @date 2021-01-14 14:00
 */
public interface ILawBaseService {
	
	/**
	 * 抛出业务异常
	 * @param fieldname
	 */
	default void bizError(String fieldname) {
		throw LawRuntimeException.build(fieldname);
	}
	
	/**
	 * 抛出业务异常
	 * @param fieldname
	 */
	default void bizError(BaseResultCode resultCode) {
		throw new LawRuntimeException(resultCode);
	}
	
	/**
	 * 抛出业务异常
	 * @param fieldname
	 */
	default void bizError(BaseResultCode resultCode, String...args) {
		throw new LawRuntimeException(resultCode, args);
	}

}
