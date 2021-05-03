package cn.repeatlink.module.judicial.common;

import cn.repeatlink.framework.exception.BaseResultCode;

public class JudicialResultCode extends BaseResultCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JudicialResultCode(String code,String fieldname) {
		super(code,fieldname);
	}
	
	public static JudicialResultCode NOT_PERMISSION =new JudicialResultCode("[JC001]NOT_PERMISSION","judicial.auth.not.permission");
	
	public static JudicialResultCode AUTH_REG_CODE_INVALID =new JudicialResultCode("[JC101]AUTH_REG_CODE_INVALID","judicial.auth.reg_code.invalid");
	
	
	public static JudicialResultCode AUTH_REG_GROUP_NOT_FOUND =new JudicialResultCode("[JC103]AUTH_REG_GROUP_NOT_FOUND","judicial.auth.reg.group.not.found");
	
	
	public static JudicialResultCode FACE_CODE_INVALID =new JudicialResultCode("[JC300]FACE_CODE_INVALID","judicial.face.code.invalid");
	
}
