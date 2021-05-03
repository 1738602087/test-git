/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.core;

import java.io.InputStream;

/**
 * @author LAI
 * @date 2020-11-17 14:18
 */
public interface HtmlParser {

	void exec(String html);
	
	default void download(InputStream is, String fileName) {
		
	}
	
}
