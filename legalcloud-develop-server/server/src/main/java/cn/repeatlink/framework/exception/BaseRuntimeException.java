package cn.repeatlink.framework.exception;

import cn.repeatlink.framework.util.MessageUtil;
import lombok.Getter;

/**
 * RL业务异常封装
 * @author tlq
 *  *
 */
public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	private final BaseResultCode  errocode;
	@Getter
	private final String[] params;
	
	public BaseRuntimeException(BaseResultCode errocode) {
	    this(errocode, null);
	}
	
	public BaseRuntimeException(BaseResultCode errocode,String...  arg1) {
		super(errocode == null ? null : ("[" + errocode.getCode() + "]" + MessageUtil.getMessage(errocode.getFieldname(), arg1)));
	    this.errocode=errocode;
	    this.params=arg1;
	}
	
}
