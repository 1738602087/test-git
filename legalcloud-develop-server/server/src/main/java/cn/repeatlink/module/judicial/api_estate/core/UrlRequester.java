/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.core;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;

/**
 * @author LAI
 * @date 2020-11-17 14:27
 */
public interface UrlRequester {
	
	String url();
	
	String method();
	
	Kv params();
	
	Map<String, List<String>> headers();
	
	default boolean isForm() {
		return false;
	}
	
	default boolean isDownloadFile() {
		return false;
	}

}
