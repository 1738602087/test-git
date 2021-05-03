/**
 * 
 */
package cn.repeatlink.module.judicial.common;

import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;

/**
 * @author LAI
 * @date 2020-09-17 10:09
 */
public class JudicialRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errocode
	 */
	public JudicialRuntimeException(BaseResultCode errocode) {
		super(errocode);
	}

	/**
	 * @param errocode
	 * @param arg1
	 */
	public JudicialRuntimeException(BaseResultCode errocode, String... arg1) {
		super(errocode, arg1);
	}

	public static JudicialRuntimeException build(String fieldname) {
		return new JudicialRuntimeException(new BaseResultCode("", fieldname));
	}
	
	public static JudicialRuntimeException build(String fieldname, String...args) {
		return new JudicialRuntimeException(new BaseResultCode("", fieldname), args);
	}
	
	public static JudicialRuntimeException build(BaseResultCode resultCode) {
		return new JudicialRuntimeException(resultCode);
	}
}
