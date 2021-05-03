/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.core;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.jfinal.kit.Kv;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author LAI
 * @date 2020-11-17 16:59
 */
public abstract class HtmlPage {
	
	private Document document = null;
	
	private String jsonStr = null;
	
	private Spider spider = null;
	
	protected Kv params = Kv.create();
	
	private int status = 0;
	
	
	public Document getDocument() {
		return this.document;
	}
	
	protected void setDocument(Document document) {
		this.document = document;
	}
	
	public String getJsonStr() {
		return this.jsonStr;
	}
	
	protected void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public HtmlPage addParam(String key, String value) {
		this.params.put(key, value);
		return this;
	}
	
	public HtmlPage addParams(Kv params) {
		this.params.putAll(params);
		return this;
	}
	
	public void loadExtra(String url, String method, Kv params, boolean isForm) {
		spider.urlRequester(new UrlRequester() {
			@Override
			public String url() {
				return url;
			}
			
			@Override
			public Kv params() {
				return params;
			}
			
			@Override
			public String method() {
				return method;
			}
			
			@Override
			public Map<String, List<String>> headers() {
				return null;
			}

			@Override
			public boolean isForm() {
				return isForm;
			}
			
		}).htmlParser(new HtmlParser() {
			
			@Override
			public void exec(String html) {
				// System.out.println(html);
			}
		}).run();
	}
	
	public void loadExtra(HtmlParser htmlParser, String url, String method, Kv params, boolean isForm) {
		spider.urlRequester(new UrlRequester() {
			@Override
			public String url() {
				return url;
			}
			
			@Override
			public Kv params() {
				return params;
			}
			
			@Override
			public String method() {
				return method;
			}
			
			@Override
			public Map<String, List<String>> headers() {
				return null;
			}

			@Override
			public boolean isForm() {
				return isForm;
			}
			
		}).htmlParser(htmlParser).run();
	}

	/**
	 * @return the spider
	 */
	public Spider getSpider() {
		return spider;
	}

	/**
	 * @param spider the spider to set
	 */
	public HtmlPage setSpider(Spider spider) {
		this.spider = spider;
		return this;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	public void run(HtmlParser htmlParser, UrlRequester urlRequester) {
		getSpider().htmlParser(htmlParser).urlRequester(urlRequester).run();
	}
	
	protected void safeSleep(long m) {
		ThreadUtil.safeSleep(m);
	}
}
