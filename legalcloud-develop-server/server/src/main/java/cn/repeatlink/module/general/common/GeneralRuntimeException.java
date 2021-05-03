/**
 * 
 */
package cn.repeatlink.module.general.common;

import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;

/**
 * @author LAI
 * @date 2020-09-17 10:09
 */
public class GeneralRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errocode
	 */
	public GeneralRuntimeException(BaseResultCode errocode) {
		super(errocode);
	}

	/**
	 * @param errocode
	 * @param arg1
	 */
	public GeneralRuntimeException(BaseResultCode errocode, String... arg1) {
		super(errocode, arg1);
	}

	public static GeneralRuntimeException build(String fieldname) {
		return new GeneralRuntimeException(new BaseResultCode(null, fieldname));
	}
}
