package cn.repeatlink.framework.exception;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 错误code
 * @author tlq20
 *
 */
@AllArgsConstructor
public class BaseResultCode  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter
	/** 错误编码 **/
	private final String code;
	/**错误提示信息key 对应资源文件中的key **/
	@Getter
	@ApiModelProperty("资源文件中的key")
    private  final String fieldname;

    public static BaseResultCode LOGIN_INFO_EMPTY = new BaseResultCode("V3[001]LOGIN_INFO_EMPTY","msg.common.rest.credentials.notexists");
    public static BaseResultCode LOGIN_INFO_INVALID = new BaseResultCode("V3[002]LOGIN_INFO_INVALID","msg.common.rest.credentials.invalid");
    public static BaseResultCode RESOURCE_NOT_FOUND = new BaseResultCode("V3[003]RESOURCE_NOT_FOUND","msg.resource.not.found");
    public static BaseResultCode INTERNAL_SERVER_ERROR = new BaseResultCode("V3[004]SYSTEM_INTERNAL_ERROR","msg.internal.server.error");
    
    public static BaseResultCode NOT_PERMISSION = new BaseResultCode("V3[010]NOT_PERMISSION","msg.common.rest.not.permission");
    
    public static BaseResultCode DB_SAVE_FAIL = new BaseResultCode("V3[005]DB_SAVE_FAIL","msg.db.save.fail");
    
    public static BaseResultCode OLD_PASSWORD_ERROR =new BaseResultCode("V3[400]OLD_PASSWORD_ERROR","judicial.auth.reset.pwd.error.oldpassword");
	public static BaseResultCode NEW_PASSWORD_ERROR =new BaseResultCode("V3[401]NEW_PASSWORD_ERROR","judicial.auth.reg.error.password.error");

}
