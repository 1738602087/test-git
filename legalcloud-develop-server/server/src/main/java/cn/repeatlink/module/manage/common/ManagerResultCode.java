package cn.repeatlink.module.manage.common;

import cn.repeatlink.framework.exception.BaseResultCode;

public class ManagerResultCode extends BaseResultCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagerResultCode(String code,String fieldname) {
		super(code,fieldname);
	}
	
	public static ManagerResultCode DATA_NULL =new ManagerResultCode("[MA000]DATA_NULL","manager.data.null");
	public static ManagerResultCode DATA_NOT_FOUND =new ManagerResultCode("[MA000]DATA_NOT_FOUND","manager.data.not.found");
	
	public static ManagerResultCode USER_INFO_NULL=new ManagerResultCode("[MA001]USER_INFO_NULL","manager.user.info.null");
	
	public static ManagerResultCode USER_NAME_NULL=new ManagerResultCode("[MA002]USER_NAME_NULL","manager.user.name.null");
	
	public static ManagerResultCode USER_NAME_INVALID=new ManagerResultCode("[MA003]USER_NAME_INVALID","manager.user.name.invalid");

	public static ManagerResultCode USER_USERNAME_NULL=new ManagerResultCode("[MA004]USER_USERNAME_NULL","manager.user.username.null");
	
	public static ManagerResultCode USER_USERNAME_INVALID=new ManagerResultCode("[MA005]USER_USERNAME_INVALID","manager.user.username.invalid");
	
	public static ManagerResultCode USER_USERNAME_REPEAT=new ManagerResultCode("[MA006]USER_USERNAME_REPEAT","manager.user.username.repeat");
	
	public static ManagerResultCode USER_EMAIL_NULl=new ManagerResultCode("[MA007]USER_EMAIL_NULl","manager.user.email.null");
	
	public static ManagerResultCode USER_EMAIL_INVALID=new ManagerResultCode("[MA008]USER_EMAIL_INVALID","manager.user.email.invalid");
	
	public static ManagerResultCode USER_EMAIL_REPEAT=new ManagerResultCode("[MA009]USER_EMAIL_REPEAT","manager.user.email.repeat");
	
	public static ManagerResultCode USER_PASSWORD_NULL=new ManagerResultCode("[MA010]USER_PASSWORD_NULL","manager.user.password.null");
	
	public static ManagerResultCode USER_PASSWORD_INVALID=new ManagerResultCode("[MA011]USER_PASSWORD_INVALID","manager.user.password.invalid");
	
	public static ManagerResultCode USER_PHONENUM_INVALID=new ManagerResultCode("[MA012]USER_PHONENUM_INVALID","manager.user.phonenum.invalid");

	public static ManagerResultCode USER_USER_ID_INVALID=new ManagerResultCode("[MA013]USER_USER_ID_INVALID","manager.user.user_id_invalid");
	
	public static ManagerResultCode USER_USER_OPERATION_SUCCESS=new ManagerResultCode("[MA014]USER_USER_OPERATION_SUCCESS","manager.user.operation.success");
	
	public static ManagerResultCode USER_USER_OPERATION_FAIL=new ManagerResultCode("[MA015]USER_USER_OPERATION_FAIL","manager.user.operation.fail");


	
	public static ManagerResultCode ROLE_INFO_NULL=new ManagerResultCode("[MA050]ROLE_INFO_NULL","manager.role.info.null");
	public static ManagerResultCode ROLE_NAME_NULL=new ManagerResultCode("[MA051]ROLE_NAME_NULL","manager.role.name.null");
	public static ManagerResultCode ROLE_NAME_INVALID=new ManagerResultCode("[MA052]ROLE_NAME_INVALID","manager.role.name.invalid");
	public static ManagerResultCode ROLE_ADMIN_FORBIDDEN_UPDATE=new ManagerResultCode("[MA052]ROLE_ADMIN_FORBIDDEN_UPDATE","manager.role.admin.forbidden.update");
	
	public static ManagerResultCode JOB_NAME_NULL =new ManagerResultCode("[MA200]JOB_NAME_NULL","manager.job.name.null");
	public static ManagerResultCode JOB_NAME_INVALID =new ManagerResultCode("[MA200]JOB_NAME_INVALID","manager.job.name.invalid");
	public static ManagerResultCode JOB_INVOKE_TARGET_INVALID =new ManagerResultCode("[MA200]JOB_INVOKE_TARGET_INVALID","manager.job.invoke.target.invalid");
	public static ManagerResultCode JOB_CRON_INVALID =new ManagerResultCode("[MA201]JOB_CRON_INVALID","manager.job.cron.invalid");
	public static ManagerResultCode JOB_REGISTER_FAIL =new ManagerResultCode("[MA204]JOB_REGISTER_FAIL","manager.job.register.fail");
	public static ManagerResultCode JOB_LOGOUT_FAIL =new ManagerResultCode("[MA205]JOB_LOGOUT_FAIL","manager.job.logout.fail");
	
	public static ManagerResultCode CONFIG_NOT_FOUND =new ManagerResultCode("[MA300]CONFIG_NOT_FOUND","manager.config.not.found");
	public static ManagerResultCode CONFIG_KEY_NULL =new ManagerResultCode("[MA300]CONFIG_KEY_NULL","manager.config.key.null");
	public static ManagerResultCode CONFIG_KEY_EXIST =new ManagerResultCode("[MA301]CONFIG_KEY_EXIST","manager.config.key.exist");
	public static ManagerResultCode CONFIG_NAME_NULL =new ManagerResultCode("[MA302]CONFIG_NAME_NULL","manager.config.name.null");
	public static ManagerResultCode CONFIG_NAME_INVALID =new ManagerResultCode("[MA303]CONFIG_NAME_INVALID","manager.config.name.invalid");

}
