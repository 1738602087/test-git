/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;

import cn.hutool.json.JSONUtil;
import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;

/**
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class SeikyuDelayConfirmReq extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private String resultStatus = null;
	
	@Override
	public String url() {
		return URL_SEIKYU_DELAY_CONFIRM;
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
		Map headers = new HashMap<>();
		headers.put("Accept", Arrays.asList("application/json", "text/javascript", "*/*"));
		headers.put("X-Requested-With", Arrays.asList("XMLHttpRequest"));
		return headers;
	}
	
	public SeikyuDelayConfirmReq setSiteCodes(String siteCodes) {
		this.params.set("siteCodes", siteCodes);
		return this;
	}
	
	@Override
	public void exec(String html) {
		Kv json = JSONUtil.toBean(html, Kv.class);
		String status = json.getStr("resultStatus");
		this.resultStatus = status;
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	
}
