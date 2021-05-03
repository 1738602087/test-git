/**
 * 
 */
package cn.repeatlink.module.manage.common;

/**
 * @author LAI
 * @date 2020-08-18 11:00
 */
public class Constant {
	
	public static class MenuVisible {
		public static final Short SHOW = 0;
		public static final Short HIDE = 1;
	}
	
	public static class ConfigType {
		public final static String SYS_INTERNAL = "Y";
		public final static String EXTERNAL = "N";
	}
	
	public static class JobMisfirePolicy {
		/** 立即执行 */
		public final static String NOW = "1";
		/** 执行一次 */
		public final static String ONE_TIME = "2";
		/** 放弃执行 */
		public final static String DO_NOTHING = "3";
	}
	
	public static class JobGroup {
		/** 默认任务组 */
		public final static String DEFAULT = "DEFAULT";
	}
	
	public static class JobConcurrent {
		/** 允许并发 */
		public final static String ALLOW = "0";
		/** 禁止并发 */
		public final static String FORBID = "1";
	}
	
	public static class DeptDelFlag {
		/** 存在，未删除 */
		public final static Short NOT_DEL = 0;
		/** 已删除 */
		public final static Short DELETED = 1;
	}

}
