/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;
import cn.repeatlink.module.judicial.api_estate.vo.AddrInfo;

/**
 * @author LAI
 * @date 2020-11-25 17:52
 */
public class KuikiDialog extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	@Override
	public String url() {
		return URL_KUIKI_DIALOG;
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
		return Kv.by("Accept", Arrays.asList("text/html", "*/*"))
				// .set("X-Requested-With", Arrays.asList("XMLHttpRequest"))
				.set("X-Requested-With", Arrays.asList("XMLHttpRequest"));
	}

	@Override
	public void exec(String html) {
		Document document = Jsoup.parse(html);
		setDocument(document);
	}
	
	private List<AddrInfo> addrInfos = null;
	
	public List<AddrInfo> getAddrInfos() {
		if(this.addrInfos == null) {
			this.parseAddr();
		}
		return this.addrInfos;
	}
	
	private boolean includeOmit = false;
	public boolean includeOmit() {
		return this.includeOmit;
	}
	
	private void parseAddr() {
		this.addrInfos = new ArrayList<>();

		final Document document = getDocument();
		final String SELECTOR_TAG = "#tabs_in_dialog li>a";
		
		Elements ele_tags = document.select(SELECTOR_TAG);
		
		// 拿到所有标签
		Map<String, String> map_tag = new LinkedHashMap<>();
		Iterator<Element> iterator = ele_tags.iterator();
		while(iterator.hasNext()) {
			Element ele = iterator.next();
			String href = ele.attr("href");
			if(href != null) {
				if(!"#tab-all".equals(href)) {
					String tag = ele.text();
					map_tag.put(href, tag);
				}
			}
			iterator.remove();
		}
		
		for(String tab : map_tag.keySet()) {
			Elements ele_addrs = document.select(tab + " table tr td");
			if(ele_addrs.isEmpty()) {
				continue;
			}
			iterator = ele_addrs.iterator();
			while(iterator.hasNext()) {
				Element ele_addr = iterator.next();
				this.hanleImagefont(ele_addr);
				String id = ele_addr.attr("id");
				String name = ele_addr.text();
				String onclick = ele_addr.attr("onclick");
				if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(name) && StringUtils.isNotBlank(onclick)) {
					// 没有下一级
					boolean next = false;
					if(onclick.contains("GKuikiDialogFixed")) {
						onclick = onclick.trim().replace("GKuikiDialogFixed(", "");
					} else if(onclick.contains("GKuikiDialogNext")) {
						next = true;
						onclick = onclick.trim().replace("GKuikiDialogNext(", "");
					}
					onclick = onclick.substring(0, onclick.length() - 1);
					String[] strs = onclick.split(",");
					if(strs.length >= 2) {
						String code = strs[0].trim().replace("'", "");
						String text = strs[1].trim().replace("'", "");
						if("null".equalsIgnoreCase(code)) {
							
						} else {
						
							AddrInfo info = new AddrInfo()
									.setCode(code)
									.setText(text)
									.setName(name)
									.setNext(next)
									// .setPid(pid)
									// .setType(type)
									.setTag(map_tag.get(tab))
									;
							this.addrInfos.add(info);
						}
					}
					else {
						if(onclick.contains("GKuikiYamakouOmit") && !this.includeOmit) {
							this.includeOmit = true;
						}
					}
				}
				
				iterator.remove();
			}
		}
	}
	
	public KuikiDialog gotoNextKuikiDialog(AddrInfo info) {
		if(info == null || StringUtils.isBlank(info.getCode())) {
			System.err.println("AddrInfo信息不完整");
			return null;
		}
		KuikiDialog dialog = new KuikiDialog();
		String findCode = this.params.getStr("findCode");
		String findKanji = this.params.getStr("findKanji");
		if(StringUtils.isBlank(findCode)) {
			findCode = info.getCode();
			findKanji = info.getText();
		} else {
			findCode = findCode + ">" + info.getCode();
			findKanji = findKanji + ">" + info.getText();
		}
		Kv kv = Kv.by("prefCode", this.params.get("prefCode"))
				.set("findCode", findCode)
				.set("findKanji", findKanji)
				.set("cityMode", false)
				.set("imageSize", 14)
				.set("heisa", false)
				.set("omit", true);
		dialog.addParams(kv);
		getSpider().urlRequester(dialog).htmlParser(dialog).run();
		return dialog;
	}
	
	/**
	 * 处理图片字体，填充占位符
	 * @param ele
	 */
	private void hanleImagefont(Element ele) {
		Elements spans = ele.select("span.CGaijiImgBackGround");
		if(spans.size() > 0) {
			for(Element span : spans) {
				if(span != null) {
					String style = span.attr("style");
					final String url = "url('/TeikyoUketsuke/image-font?code=";
					if(style != null && style.contains(url)) {
						String code = style.substring(style.indexOf(url) + url.length());
						code = code.substring(0, code.indexOf("&"));
						if(StringUtils.isNotBlank(code)) {
							span.text("${code=" + code + "}");
						}
					}
				}
			}
		}
	}
}
