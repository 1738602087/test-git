/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
public class LoginTo extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private boolean signed = false;
	
	private boolean need_force_login = false;
	
	private Results results = new Results();
	
	@Override
	public String url() {
		return URL_LOGIN;
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
	public Map<String, List<String>> headers() {
		return null;
	}
	
	@Override
	public boolean isForm() {
		return true;
	}

	@Override
	public void exec(String html) {
		Document document = Jsoup.parse(html);
		
		Element ele_login = document.selectFirst("form#login");
		if(ele_login != null) {
			String teikyoToken = ele_login.selectFirst("input[name=teikyoToken]").val();
			String from = ele_login.selectFirst("input[name=from]").val();
			if(StringUtils.isNotBlank(from) && StringUtils.isNotBlank(teikyoToken)) {
				signed = false;
				need_force_login = true;
				results.set("teikyoToken", teikyoToken);
				results.set("from", from);
			}
		}
		
		if(!need_force_login) {
		Element logoutForm = document.selectFirst("form#logout-confirm");
			if(logoutForm != null) {
				Element logoutBtn = logoutForm.selectFirst("button#logout-confirm_0[type=submit]");
				String teikyoToken = logoutForm.selectFirst("input[name=teikyoToken]").val();
				results.set("teikyoToken", teikyoToken);
				Results.get().set("teikyoToken", teikyoToken);
				Results.get().set("login_flag", true);
				signed = true;
			}
		}
		
	}
	
	public LoginTo forceLogin() {
		LoginTo loginTo = new LoginTo();
		loginTo.addParam("teikyoToken", results.getStr("teikyoToken"));
		loginTo.addParam("from", results.getStr("from"));
		getSpider().htmlParser(loginTo).urlRequester(loginTo).run();
		return loginTo;
	}

	public EstateSearchPage gotoEstateSearchPage() {
		if(signed) {
			EstateSearchPage page = new EstateSearchPage();
			getSpider().htmlParser(page).urlRequester(page).run();
			return page;
		}
		
		return null;
	}
	
	public MyPagePage gotoMyPage() {
		if(signed) {
			MyPagePage page = new MyPagePage();
			getSpider().htmlParser(page).urlRequester(page).run();
			return page;
		}
		
		return null;
	}

	/**
	 * @return the signed
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * @return the need_force_login
	 */
	public boolean isNeed_force_login() {
		return need_force_login;
	}
	
	public String getTeikyoToken() {
		return results.getStr("teikyoToken");
	}
}
