/**
 * 
 */
package cn.repeatlink.module.general.common;

import cn.repeatlink.framework.exception.BaseResultCode;

/**
 * @author LAI
 * @date 2020-09-27 14:32
 */
public class GeneralResultCode extends BaseResultCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param code
	 * @param fieldname
	 */
	public GeneralResultCode(String code, String fieldname) {
		super(code, fieldname);
	}

}
