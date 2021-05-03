/**
 * 
 */
package cn.repeatlink.framework.common;

/**
 * @author LAI
 * @date 2020-08-25 10:38
 */
public class Constant {
	
	public static class MenuType {
		public final static String DIR = "M";
		public final static String MENU = "C";
		public final static String BTN = "F";
	}
	
	public static class JobLogStatus {
		public final static Short FAIL = 0;
		public final static Short SUCCESS = 1;
		public final static Short EXEC = 2;
	}
	
	public static enum MailSenderType {
		DEFAULT
	}
	
	public static enum RoleKey {
		admin, judicial, law
	}
	
	public static enum UserType {
		/** 系统用户（默认）：00 */
		SYS("00"),
		
		/** 组织（事务所）：10 */
		LAW("10"),
		
		/** 司法书士：20 */
		JUDICIAL("20"),
		
		/** 一般用户：30 */
		GENERAL("30"),
		
		;
		
		private String user_type;
		
		UserType(String user_type) {
			this.user_type = user_type;
		}
		
		public String typeValue() {
			return this.user_type;
		}
		
		public static UserType userType(String userType) {
			for(UserType type : UserType.values()) {
				if(type.user_type.equals(userType)) {
					return type;
				}
			}
			return null;
		}
		
	}
	
	public static interface PushRegistrationType {
		int JUD = 1;
		int GEN = 2;
	}
}
