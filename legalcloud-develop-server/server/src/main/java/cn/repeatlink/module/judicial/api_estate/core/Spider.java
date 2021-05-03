/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.core;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-11-17 14:15
 */

@Log4j2
public class Spider {
	
	private HtmlParser htmlParser = null;
	
	private UrlRequester urlRequester = null;
	
	private Map<String, List<String>> headers = null;
	
	private HttpRequest request = null;
	
	public static Spider build() {
		return new Spider();
	}
	
	public Spider htmlParser(HtmlParser htmlParser) {
		this.htmlParser = htmlParser;
		return this;
	}
	
	public Spider urlRequester(UrlRequester urlRequester) {
		this.urlRequester = urlRequester;
		return this;
	}
	
	public final void run() {
		Assert.notNull(htmlParser);
		Assert.notNull(urlRequester);
		HtmlPage htmlPage = null;
		if(htmlParser instanceof HtmlPage) {
			htmlPage = ((HtmlPage)htmlParser).setSpider(this);
		} else if(urlRequester instanceof HtmlPage) {
			htmlPage = ((HtmlPage)urlRequester).setSpider(this);
		}
		
		String url = urlRequester.url();
		String method = urlRequester.method();
		Map<String, Object> params = urlRequester.params();
		Map<String, List<String>> headers2 = urlRequester.headers();
		boolean isForm = urlRequester.isForm();
		
		if(request == null) {
			request = HttpUtil.createRequest(Method.valueOf(method.toUpperCase()), url);
		} else {
			request.setMethod(Method.valueOf(method.toUpperCase()));
			request.setUrl(url);
		}
		if(params != null && !params.isEmpty()) {
			if(method.equalsIgnoreCase("POST")) {
				if(isForm) {
					request.form(params);
				} else {
					request.body(JSONUtil.toJsonStr(params));
				}
			} else if(method.equalsIgnoreCase("GET")) {
				request.body(URLUtil.buildQuery(params, null));
				
			}
		}
		
		if(this.headers == null) {
			this.setDefaultHeaders();
		}
		if(headers2 != null) {
			this.headers.putAll(headers2);
		}
		
		request.header(this.headers, true);
		
		// request.enableDefaultCookie();
		
		HttpResponse response = null;
		
		// 重定向
		while((response = request.execute()) != null) {
			System.out.println(">>> 已加载 " + response.getStatus() + " " + request.getUrl());
			if(response.getCookies() != null) {
				request.cookie(response.getCookies().toArray(new HttpCookie[0]));
			}
			if(response.getStatus() == 301 || response.getStatus() == 302) {
				String location = response.header("Location");
				request.setUrl(location);
				request.setMethod(Method.GET);
				if(request.form() != null ) {
					request.form().clear();
				}
				continue;
			}
			break;
		}
		
		if(htmlPage != null) {
			htmlPage.setStatus(response.getStatus());
		}
		// 400以上状态码，打印出来
		if(response.getStatus() >= 400) {
			log.error(method + " " + url + " " + response.getStatus());
		}
		
		if(urlRequester.isDownloadFile()) {
			String fileName = null;
			// response.header("");
			htmlParser.download(response.bodyStream(), fileName);
		} else {
			String body = response.body();
			htmlParser.exec(body);
		}
	}

	/**
	 * 
	 */
	public Spider setDefaultHeaders() {
		this.headers = new HashMap<>();
		headers.put("User-Agent", Arrays.asList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"));
		headers.put("Cache-Control", Arrays.asList("no-cache"));
		headers.put("Accept-Language", Arrays.asList("zh-CN,zh;q=0.9"));
		headers.put("Connection", Arrays.asList("keep-alive"));
		headers.put("Pragma", Arrays.asList("no-cache"));
		
		return this;
	}
	
	

}
