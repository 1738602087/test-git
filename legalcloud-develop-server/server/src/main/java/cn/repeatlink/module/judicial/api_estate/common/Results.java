/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.common;

import com.jfinal.kit.Kv;

/**
 * @author LAI
 * @date 2020-11-17 15:58
 */
public class Results extends Kv {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final ThreadLocal<Results> TL = new ThreadLocal<>();
	
	public static Results get() {
		Results results = TL.get();
		if(results == null) {
			results = new Results();
			TL.set(results);
		}
		return results;
	}

}
