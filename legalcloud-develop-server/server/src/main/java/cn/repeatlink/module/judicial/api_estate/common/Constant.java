/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.common;

/**
 * @author LAI
 * @date 2020-12-28 13:58
 */
public class Constant {
	
	public static class ShozaiType {
		
		/** 土地 */
		public static String TOCHI = "TOCHI";
		/** 建物 */
		public static String TATEMONO = "TATEMONO";
		
		public static String getShozaiType(String type) {
			if("2".equals(type)) {
				return TOCHI;
			} else if("1".equals(type)) {
				return TATEMONO;
			}
			return null;
		}
		
	}

}
