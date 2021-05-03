/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;

/**
 * @author LAI
 * @date 2020-11-26 10:42
 */
public class LogoutPage extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {

	@Override
	public String url() {
		return URL_LOGOUT;
	}

	@Override
	public String method() {
		return "POST";
	}

	@Override
	public Kv params() {
		return params;
	}
	
	@Override
	public boolean isForm() {
		return true;
	}

	@Override
	public Map<String, List<String>> headers() {
		return null;
	}

	@Override
	public void exec(String html) {
		
		// System.out.println(html);
		
	}
	

}
