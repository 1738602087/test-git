/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.judicial.api_estate.common.Results;
import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;

/**
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class LoginPage extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private boolean isReadyToLogin = false;

	@Override
	public String url() {
		return URL_BASE;
	}

	@Override
	public String method() {
		return "GET";
	}

	@Override
	public Kv params() {
		return null;
	}

	@Override
	public Map<String, List<String>> headers() {
		return null;
	}

	@Override
	public void exec(String html) {
		Document document = Jsoup.parse(html);
		String teikyoToken = document.selectFirst("input[name=teikyoToken]").val();
		String from = document.selectFirst("input[name=from]").val();
		
		Results.get().set("teikyoToken", teikyoToken);
		Results.get().set("from", from);
		
		if(StringUtils.isNotBlank(teikyoToken)) {
			isReadyToLogin = true;
		}
		
	}

	public LoginTo login(String username, String password) {
		if(isReadyToLogin) {
			LoginTo loginTo = new LoginTo();
			loginTo.addParam("userId", username);
			loginTo.addParam("password", password);
			loginTo.addParam("teikyoToken", Results.get().getStr("teikyoToken"));
			loginTo.addParam("from", Results.get().getStr("from"));
			getSpider().htmlParser(loginTo).urlRequester(loginTo).run();
			
			return loginTo;
		}
		return null;
	}

	/**
	 * @return the isReadyToLogin
	 */
	public boolean isReadyToLogin() {
		return isReadyToLogin;
	}
	
	
}
