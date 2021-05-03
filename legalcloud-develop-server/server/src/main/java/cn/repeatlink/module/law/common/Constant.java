/**
 * 
 */
package cn.repeatlink.module.law.common;

/**
 * @author LAI
 * @date 2020-09-28 14:34
 */
public class Constant {
	
	public static final class OmRecordStatus {
		
		public final static Short INVALID = 0;
		
		public final static Short NOT_REQ = 1;
		
		public final static Short REQ_OM = 2;
		
		public final static Short REQ_OM_AGAIN = 3;
		
		public final static Short FAIL = 4;
		
		public final static Short SUCCESS = 5;
		
	}
	
	public static final class ApplyStatus {
		
		public final static Short INVALID = 0;
		
		public final static Short PASSED = 1;
		
		public final static Short APPLY = 2;
		
		public final static Short REJECT = 3;
		
	}

}
