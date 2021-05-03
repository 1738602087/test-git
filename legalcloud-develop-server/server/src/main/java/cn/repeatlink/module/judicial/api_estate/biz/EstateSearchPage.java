/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.util.ArrayList;
import java.util.Iterator;
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
 * @date 2020-11-17 15:20
 */
public class EstateSearchPage extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private Kv formData = Kv.create();
	
	@Override
	public String url() {
		return URL_ESTATE_SEARCH;
	}

	@Override
	public String method() {
		return "GET";
	}

	@Override
	public Kv params() {
		params.put("tab", "fu");
		return params;
	}

	@Override
	public Map<String, List<String>> headers() {
		return null;
	}
	
	@Override
	public void exec(String html) {
		Document document = Jsoup.parse(html);
		setDocument(document);
		// 访问额外URL
		this.loadOthers();
		
		this.autoParse();
	}

	/**
	 * 
	 */
	private void autoParse() {
		// TODO Auto-generated method stub
		
		this.formData.set("teikyoToken", getDocument().selectFirst("form#fudosanForm input[name=teikyoToken]").val());
		
	}

	private void loadOthers() {
		safeSleep(1000);
		loadExtra("https://www.touki.or.jp/TeikyoUketsuke/mypage/my-page-list", "POST"
				, Kv.by("sEcho", 1).set("startIndex", 0).set("pageSize", 50).set("searchKey", "NONE").set("sortCol", "SEIKYU_DATE").set("sortDir", "desc")
				, true);
	}
	
	private List<AddrInfo> addrInfos = null;
	
	public List<AddrInfo> getAddrInfos() {
		if(this.addrInfos == null) {
			this.parseAddr();
		}
		return this.addrInfos;
	}
	
	private void parseAddr() {
		this.addrInfos = new ArrayList<>();

		final Document document = getDocument();
		final String SELECTOR_ADDR1_SELECT = "#tabsFudosan #fuTodofukenShozai";
		Element ele_addr1_select = document.selectFirst(SELECTOR_ADDR1_SELECT);
		System.out.println(ele_addr1_select.toString());

		final String SELECTOR_ADDR1_SELECT_OPTION = "option";
		Elements ele_options = ele_addr1_select.select(SELECTOR_ADDR1_SELECT_OPTION);
		if (ele_options.isEmpty()) {
			System.out.println("未找到option元素");
		} else {
			Iterator<Element> iterator = ele_options.iterator();
			final String TYPE = "addr1";
			while (iterator.hasNext()) {
				Element ele = iterator.next();
				String addr_value = ele.attr("value");
				if (StringUtils.isNotBlank(addr_value)) {
					String addr_name = ele.text();
					Element ele_parent = ele.parent();
					String addr_group = null;
					if (ele_parent != null && "optgroup".equalsIgnoreCase(ele_parent.tagName())) {
						addr_group = ele_parent.attr("label");
					}
					AddrInfo info = new AddrInfo().setCode(addr_value).setName(addr_name).setText(addr_name)
							.setFulltext(addr_name).setTag(addr_group).setType(TYPE);

					this.addrInfos.add(info);

				}
				iterator.remove();
			}
		}

	}
	
	public KuikiDialog gotoKuikiDialog(AddrInfo info) {
		if(info == null || StringUtils.isBlank(info.getCode())) {
			System.err.println("AddrInfo信息不完整");
			return null;
		}
		Kv kv = Kv.by("prefCode", info.getCode())
				.set("cityMode", false)
				.set("imageSize", 14)
				.set("heisa", false)
				.set("omit", true);
		KuikiDialog dialog = new KuikiDialog();
		dialog.addParams(kv);
		getSpider().urlRequester(dialog).htmlParser(dialog).run();
		return dialog;
	}
	
	public EstateListPage gotoEstateListPage(String shozaiType, String todofukenShozai, String chibanKuiki, String chibanKaoku) {
		if(StringUtils.isAnyBlank(shozaiType, todofukenShozai, chibanKuiki, chibanKaoku)) {
			System.err.println("参数不完整");
			return null;
		}
		// 请求参数
		Kv params = Kv.by("fuSearchForm.seikyuMethod", "SHOZAI")
			.set("teikyoToken", this.formData.get("teikyoToken"))
			.set("__checkbox_fuSearchForm.heisaTokibo", true)
			.set("fuSearchForm.shozaiType", shozaiType)
			.set("fuSearchForm.todofukenShozai", todofukenShozai)
			.set("fuShozaiChokusetuNyuryoku", true)
			.set("__checkbox_fuShozaiChokusetuNyuryoku", true)
			.set("fuSearchForm.chibanKuikiCode", "")
			.set("fuSearchForm.chibanKuiki", chibanKuiki)
			.set("fuSearchForm.yamaKouUnknown", false)
			.set("fuSearchForm.chibanKaoku", chibanKaoku)
			.set("fuSearchForm.fudosanNo", "")
			.set("fuSearchForm.todofukenFudosan", "")
			.set("fuSearchForm.seikyuTokishoCode:", "")
			.set("actionMode", "open")
			.set("fuSearchForm.todofukenSokochi", "")
			.set("fuSearchForm.sokochiKuiki", "")
			.set("fuSearchForm.sokochiKaoku", "")
			.set("fuSearchForm.seikyuTypeAll", true)
			.set("__checkbox_fuSearchForm.seikyuTypeAll", true)
			.set("__checkbox_fuSearchForm.seikyuTypeShoyusha", true)
			.set("__checkbox_fuSearchForm.seikyuTypeChizu", true)
			.set("__checkbox_fuSearchForm.seikyuTypeShozai", true)
			.set("__checkbox_fuSearchForm.seikyuTypeChieki", true)
			.set("__checkbox_fuSearchForm.seikyuTypeZumen", true)
			.set("fuSearchForm.kyodoTanpo", "NO")
			.set("fuSearchForm.shintaku", "NO")
			;
		
		EstateListPage page = new EstateListPage();
		page.addParams(params);
		getSpider().urlRequester(page).htmlParser(page).run();
		return page;
	}
	
}
