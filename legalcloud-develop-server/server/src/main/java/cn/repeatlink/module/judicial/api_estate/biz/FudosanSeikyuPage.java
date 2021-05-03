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

import cn.hutool.json.JSONArray;
import cn.repeatlink.module.judicial.api_estate.common.TeikyoUketsukeUrl;
import cn.repeatlink.module.judicial.api_estate.core.HtmlPage;
import cn.repeatlink.module.judicial.api_estate.core.HtmlParser;
import cn.repeatlink.module.judicial.api_estate.core.UrlRequester;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class FudosanSeikyuPage extends HtmlPage implements HtmlParser, UrlRequester, TeikyoUketsukeUrl {
	
	private List<JSONArray> aaData = null;
	
	private Kv formData = Kv.create();
	
	@Override
	public String url() {
		return URL_FUDOSAN_SEIKYU;
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
		
		try {
			this.formData.set("teikyoToken", getDocument().selectFirst("form#myPageForm input[name=teikyoToken]").val());
		} catch (Exception e) {
			throw e;
		}
		// 访问额外URL
		this.loadOthers();
		
		if(StringUtils.isBlank(this.formData.getStr("teikyoToken"))) {
			return;
		}
		// ajax获取登記情報列表
		this.getMyPageList();
	}

	private void loadOthers() {
		
	}
	
	private void getMyPageList() {
		safeSleep(2000);
		MyPageListReq req = new MyPageListReq();
		run(req, req);
		Kv result = req.getResult();
		if(result != null) {
			aaData = result.getAs("aaData");
		}
		
	}
	
	public boolean isReg() {
		if(aaData != null && !aaData.isEmpty()) {
			MyPageDataItem dataItem = getDataItem(0);
			// 判断情报登记与否
			String status = dataItem.getStatus();
			if("請求済".equals(status)) {
				return true;
			}
		}
		return false;
	}
	
	public int aaDataSize() {
		return aaData == null ? 0 : aaData.size();
	}
	
	public MyPageDataItem getDataItem(int index) {
		JSONArray obj = aaData.get(index);
		MyPageDataItem item = new MyPageDataItem();
		item.setEstate_no(obj.getInt(0));
		try {
			item.setOrder(StringUtils.isBlank(obj.getStr(1)) ? 0 : Integer.valueOf(obj.getStr(1)));
		} catch (Exception e) {
			item.setOrder(-1);
		}
		item.setReq_type(obj.getJSONArray(2).getStr(0));
		
		String str = obj.getJSONArray(4).getStr(0);
		String[] ss = str.split("・");
		item.setType(ss[0].trim());
		item.setAddr(ss[1].trim());
		
		item.setStatus(obj.getStr(5));
		
		item.setReq_time(obj.getJSONArray(6).getStr(0));
		item.setReq_no(obj.getJSONArray(6).getStr(1));
		try {
		item.setMoney(StringUtils.isBlank(obj.getStr(7)) ? 0 : Integer.valueOf(obj.getStr(7)));
		} catch (Exception e) {
			item.setMoney(-1);
		}
		item.setPdf_size(obj.getStr(8));
		
		return item;
	}
	
	public MyPageDownloadReq downloadPdf(int index, String filePath) {
		MyPageDataItem item = getDataItem(index);
		MyPageDownloadReq req = new MyPageDownloadReq();
		req.param(this.formData.getStr("teikyoToken"), "" + item.getEstate_no(), item.getStatus(), "NONE");
		req.fileStorePath(filePath);
		run(req, req);
		return req;
	}
	
	public String teikyoToken() {
		return this.formData.getStr("teikyoToken");
	}
	
	@Data
	public static class MyPageDataItem {
		
		private Integer estate_no;
		
		private Integer order;
		
		private String req_type;
		
		private Kv req_detail;
		
		private String addr;
		
		private String type;
		
		private String status;
		
		private String req_time;
		
		private String req_no;
		
		private Integer money;
		
		private String pdf_size;
		
	}
}
