/**
 * 
 */
package cn.repeatlink.module.law.common;

import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;

/**
 * @author LAI
 * @date 2020-09-28 14:53
 */
public class LawRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errocode
	 */
	public LawRuntimeException(BaseResultCode errocode) {
		super(errocode);
	}

	/**
	 * @param errocode
	 * @param arg1
	 */
	public LawRuntimeException(BaseResultCode errocode, String... arg1) {
		super(errocode, arg1);
	}

	public static LawRuntimeException build(String fieldname) {
		return new LawRuntimeException(new BaseResultCode(null, fieldname));
	}
	
}
