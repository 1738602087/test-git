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
import org.jsoup.select.Elements;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;

/**
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class EstateListPage extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private Kv formData = Kv.create();
	
	@Override
	public String url() {
		return URL_ESTATE_LIST;
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
		Document document = Jsoup.parse(html);
		setDocument(document);
		// 访问额外URL
		this.loadOthers();
		
		this.parseEstateList();
	}

	private void loadOthers() {
		
	}
	
	
	private void parseEstateList() {
		final Document document = getDocument();

		Element form = document.selectFirst("#fudosanIchiranForm");
		
		if(form == null) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.list.form.null");
		}
		String teikyoToken = form.selectFirst("input[name=teikyoToken]").val();
		formData.set("teikyoToken", teikyoToken);

		Element tbl = form.selectFirst("#fudosanIchiranTbl");
		
		Elements trs = tbl.select("tbody tr");
		int size = trs.size();
		
		if(size == 0) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.list.table.null");
		}
		if(size > 1) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.list.table.tr.size.limit");
		}
		
		String siteCode = trs.get(0).selectFirst("input[name=siteCode]").val();
		formData.set("siteCode", siteCode);
		
		// 其他表单参数
		formData.set("seikyu", form.selectFirst("input[name=seikyu]").val());
		formData.set("detail", form.selectFirst("input[name=detail]").val());
		formData.set("seikyuMethod", form.selectFirst("input[name=seikyuMethod]").val());
		formData.set("formReturn", form.selectFirst("input[name=formReturn]").val());
		formData.set("__checkbox_shokaiBango", form.selectFirst("input[name=__checkbox_shokaiBango]").val());
		
		formData.set("sentaku", trs.get(0).selectFirst("input[name=sentaku]").val());
		formData.set("__checkbox_sentaku", trs.get(0).selectFirst("input[name=__checkbox_sentaku]").val());
		formData.set("checkedList", trs.get(0).selectFirst("input[name=checkedList]").val());
		formData.set("tsusuList", trs.get(0).selectFirst("input[name=tsusuList]").val());
		formData.set("barCodeList", trs.get(0).selectFirst("input[name=barCodeList]").val());
		formData.set("ryokin", trs.get(0).selectFirst("input[name=ryokin]").val());
		formData.set("heisaDate", trs.get(0).selectFirst("input[name=heisaDate]").val());
		formData.set("heisaJiken", trs.get(0).selectFirst("input[name=heisaJiken]").val());
		formData.set("heisaWareki", trs.get(0).selectFirst("input[name=heisaWareki]").val());
		formData.set("heisaSentakuZumi", trs.get(0).selectFirst("input[name=heisaSentakuZumi]").val());
		formData.set("seikyuType", trs.get(0).selectFirst("input[name=seikyuType]").val());
		formData.set("zumenTokishoCode", trs.get(0).selectFirst("input[name=zumenTokishoCode]").val());
		formData.set("zumenJikenId", trs.get(0).selectFirst("input[name=zumenJikenId]").val());
		formData.set("zumenRegistDate", trs.get(0).selectFirst("input[name=zumenRegistDate]").val());
		formData.set("zumenIdFlg", trs.get(0).selectFirst("input[name=zumenIdFlg]").val());
		formData.set("zumenId", trs.get(0).selectFirst("input[name=zumenId]").val());
		formData.set("chibanKuiki", trs.get(0).selectFirst("input[name=chibanKuiki]").val());
		formData.set("chiban", trs.get(0).selectFirst("input[name=chiban]").val());
		formData.set("seikyuzumi", trs.get(0).selectFirst("input[name=seikyuzumi]").val());
		formData.set("mypageId", trs.get(0).selectFirst("input[name=mypageId]").val());
		formData.set("seikyuzumiHashKeys", trs.get(0).selectFirst("input[name=seikyuzumiHashKeys]").val());
		formData.set("selectSeikyuHashKey", trs.get(0).selectFirst("input[name=selectSeikyuHashKey]").val());
		
	}
	
	public FudosanSeikyuPage makeInfoReq() {
		String siteCode = formData.getStr("siteCode");
		if(StringUtils.isBlank(siteCode)) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.list.sitecode.null");
		}
		
		SeikyuDelayConfirmReq req = new SeikyuDelayConfirmReq();
		req.setSiteCodes(siteCode);
		run(req, req);
		
		safeSleep(2000);
		String resultStatus = req.getResultStatus();
		if("false".equals(resultStatus)) {
			FudosanSeikyuPage page = new FudosanSeikyuPage();
			Kv params = Kv.by("teikyoToken", formData.getStr("teikyoToken"))
				.set("seikyu", "true")
				.set("detail", formData.getStr("detail"))
				.set("seikyuMethod", formData.getStr("seikyuMethod"))
				.set("formReturn", formData.getStr("formReturn"))
				.set("sentaku", formData.getStr("sentaku"))
				.set("__checkbox_shokaiBango", formData.getStr("__checkbox_shokaiBango"))
				.set("__checkbox_sentaku", formData.getStr("__checkbox_sentaku"))
				.set("checkedList", "true")
				.set("tsusuList", "0")
				.set("barCodeList", "1")
				.set("ryokin", formData.getStr("ryokin"))
				.set("heisaDate", formData.getStr("heisaDate"))
				.set("heisaJiken", formData.getStr("heisaJiken"))
				.set("heisaWareki", formData.getStr("heisaWareki"))
				.set("heisaSentakuZumi", formData.getStr("heisaSentakuZumi"))
				.set("seikyuType", formData.getStr("seikyuType"))
				.set("zumenTokishoCode", formData.getStr("zumenTokishoCode"))
				.set("zumenJikenId", formData.getStr("zumenJikenId"))
				.set("zumenRegistDate", formData.getStr("zumenRegistDate"))
				.set("zumenIdFlg", formData.getStr("zumenIdFlg"))
				.set("zumenId", formData.getStr("zumenId"))
				.set("chibanKuiki", formData.getStr("chibanKuiki"))
				.set("chiban", formData.getStr("chiban"))
				.set("seikyuzumi", formData.getStr("seikyuzumi"))
				.set("mypageId", formData.getStr("mypageId"))
				.set("seikyuzumiHashKeys", formData.getStr("seikyuzumiHashKeys"))
				.set("selectSeikyuHashKey", formData.getStr("selectSeikyuHashKey"))
				.set("siteCode", formData.getStr("siteCode"))
				;
			page.addParams(params);
			run(page, page);
			return page;
		}
		return null;
	}
	
	
}
