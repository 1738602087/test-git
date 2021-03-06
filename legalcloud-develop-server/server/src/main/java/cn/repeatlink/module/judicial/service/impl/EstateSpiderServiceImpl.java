/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import cn.repeatlink.common.entity.CaseEstateRecord;
import cn.repeatlink.common.mapper.CaseEstateRecordMapper;
import cn.repeatlink.common.service.IDownloadService;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.judicial.api_estate.biz.EstateListPage;
import cn.repeatlink.module.judicial.api_estate.biz.EstateSearchPage;
import cn.repeatlink.module.judicial.api_estate.biz.FudosanSeikyuPage;
import cn.repeatlink.module.judicial.api_estate.biz.FudosanSeikyuPage.MyPageDataItem;
import cn.repeatlink.module.judicial.api_estate.biz.LoginPage;
import cn.repeatlink.module.judicial.api_estate.biz.LoginTo;
import cn.repeatlink.module.judicial.api_estate.biz.LogoutPage;
import cn.repeatlink.module.judicial.api_estate.biz.MyPageDownloadReq;
import cn.repeatlink.module.judicial.api_estate.biz.MyPagePage;
import cn.repeatlink.module.judicial.api_estate.common.Constant.ShozaiType;
import cn.repeatlink.module.judicial.api_estate.core.Spider;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.Define.ConfigKeys;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IEstateSpiderService;
import cn.repeatlink.module.judicial.util.EstateInfoUtil;
import cn.repeatlink.module.judicial.util.PdfUtil;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateSpiderResultVo;
import lombok.Data;

/**
 * ?????????API fetch??????
 * @author LAI
 * @date 2020-12-30 10:33
 */

@Service
public class EstateSpiderServiceImpl implements IEstateSpiderService {
	
	@Autowired
	private CaseEstateRecordMapper caseEstateRecordMapper;
	
	@Autowired
	private IDownloadService downloadService;
	
	private Spider spider = null;
	
	private LoginTo pageLoginTo = null;
	
	private EstateSearchPage pageEstateSearchPage = null;
	
	private EstateListPage pageEstateListPage = null;
	
	private FudosanSeikyuPage pageFudosanSeikyuPage = null;
	
	private MyPageDownloadReq reqMyPageDownloadReq = null;
	
	private Kv addr1Kv = null;
	
	private EsateCaseInfo info = null;
	
	@Override
	public EstateSpiderResultVo fetch(EstateBaseVo baseVo) {
		// ??????????????????????????????????????????????????????????????????????????????token????????????????????????????????????503?????????
		synchronized (EstateSpiderServiceImpl.class) {
			EstateSpiderResultVo vo = null;
			try {
				info = buildInfo(baseVo);
				fixBaseVo(baseVo);
				
				if(spider == null || !stepCheckToken()) {
					boolean login = stepLogin();
					if(!login) {
						// ?????????????????????????????????????????????
						safeSleep(3000);
						log("??????????????????");
						login = stepLogin();
						// 
						if(!login) {
							throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.login.error");
						}
					}
				}
				// ?????????
				// ??????????????????????????????
				stepGotoEstateSearchPage();
				// ????????????
				stepGotoEstateSearchResultPage(baseVo.getType(), baseVo.getAddr(), baseVo.getHouse_id());
				// ?????????????????????????????????????????????
				stepGotoEstateRegResultPage();
				// ????????????????????????
				stepCheckEstateInfoRegResult();
				// ?????????????????????????????????
				stepDownloadEstatePdf();
				// ??????PDF??????
				stepCheckPdfFile();
				// ??????PDF??????
				vo = stepReadPdfFile();
				
			} catch (Throwable e) {
				// ????????????
				log(e.getMessage());
				e.printStackTrace();
				vo = new EstateSpiderResultVo();
				vo.setErrorMsg(StringUtils.isBlank(e.getMessage()) ? "error" : e.getMessage());
			} finally {
				// ????????????
				pageEstateSearchPage = null;
				pageEstateListPage = null;
				pageFudosanSeikyuPage = null;
				reqMyPageDownloadReq= null;
				info = null;
			}
			return vo;
		}
	}
	
	/**
	 * @param baseVo
	 */
	private void fixBaseVo(EstateBaseVo baseVo) {
		baseVo.setAddr(Convert.toSBC(baseVo.getAddr()));
		baseVo.setHouse_id(Convert.toSBC(baseVo.getHouse_id()));
	}

	@Override
	public EsateCaseInfo buildInfo(EstateBaseVo baseVo) {
		EsateCaseInfo info = new EsateCaseInfo();
		CaseEstateRecord record = caseEstateRecordMapper.selectByPrimaryKey(baseVo.getRecord_id());
		info.setCaseId(record.getCaseId());
		info.setEstateId(record.getEstateId());
		info.setType(record.getType());
		info.setRecordId(record.getRecordId());
		info.setCaseStartDate(Db.queryDate("select start_date from case_estate where case_id=?", info.getCaseId()));
		return info;
	}
	
	private boolean stepCheckToken() {
		if(spider == null) {
			return false;
		}
		MyPagePage page = null;
		log("?????????????????????????????????????????????");
		try {
			page = new MyPagePage();
			spider.htmlParser(page).urlRequester(page).run();
		} catch (Exception e) {
			log("??????my-page??????: " + e.getMessage());
		}
		if(page == null || StringUtils.isBlank(page.teikyoToken())) {
			return false;
		}
		return true;
	}
	
	private boolean stepLogin() {
		log("??????????????????");
		LoginPage loginPage = new LoginPage();
		spider = Spider.build().htmlParser(loginPage).urlRequester(loginPage);
		spider.run();
		safeSleep(2000);
		log("??????????????????????????????");
		Kv user = getLoginUserInfo();
		pageLoginTo = loginPage.login(user.getStr("username"), user.getStr("password"));
		if(!pageLoginTo.isSigned() && pageLoginTo.isNeed_force_login()) {
			log("??????????????????");
			safeSleep(2000);
			log("??????????????????");
			pageLoginTo = pageLoginTo.forceLogin();
		}
		if(pageLoginTo.isSigned()) {
			log("???????????????, teikyoToken=" + pageLoginTo.getTeikyoToken());
			return true;
		} else {
			log("????????????");
			return false;
		}
	}
	
	private void stepGotoEstateSearchPage() {
		safeSleep(2000);
		log("?????????????????????????????????");
		pageEstateSearchPage = pageLoginTo.gotoEstateSearchPage();
	}
	
	private void stepGotoEstateSearchResultPage(String type, String addr, String houseId) {
		safeSleep(2000);
		log("?????????????????????");
		String shozaiType = ShozaiType.getShozaiType(type);
		Kv addrKv = split(addr);
		String todofukenShozai = addrKv.getStr("addr1code");
		String chibanKuiki = addrKv.getStr("addr2");
		String chibanKaoku = houseId;
		pageEstateListPage = pageEstateSearchPage.gotoEstateListPage(shozaiType, todofukenShozai, chibanKuiki, chibanKaoku);
	}

	private void stepGotoEstateRegResultPage() {
		safeSleep(3000);
		log("???????????????????????????");
		pageFudosanSeikyuPage = pageEstateListPage.makeInfoReq();
	}
	
	private void stepCheckEstateInfoRegResult() {
		if(pageFudosanSeikyuPage.isReg()) {
			return;
		}
		throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.reg.info.null");
	}
	
	private void stepDownloadEstatePdf() {
		safeSleep(2000);
		log("???????????????????????????PDF??????");
		// ??????????????????
		MyPageDataItem dataItem = pageFudosanSeikyuPage.getDataItem(0);
		String path = FileUtil.normalize(getPdfStoreRootFolder() + "/" + buildPdfRelativePath(dataItem));
		reqMyPageDownloadReq = pageFudosanSeikyuPage.downloadPdf(0, path);
	}
	
	private void stepCheckPdfFile() {
		File pdfFile = reqMyPageDownloadReq.getPdfFile();
		if(pdfFile.exists() && pdfFile.isFile() && pdfFile.length() > 0) {
			
		} else {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.file.pdf.error");
		}
	}
	
	private EstateSpiderResultVo stepReadPdfFile() {
		log("???????????????????????????PDF??????");
		File pdfFile = reqMyPageDownloadReq.getPdfFile();
		return parseEstatePdf(pdfFile).setPdfFile(pdfFile);
	}
	
	@Override
	public EstateSpiderResultVo parseEstatePdf(File pdfFile) {
		String text = PdfUtil.getFullText(pdfFile);
		return parseEstatePdfText(text);
	}
	
	@Override
	public EstateSpiderResultVo parseEstatePdf(InputStream pdfInputStream) {
		String text = PdfUtil.getFullText(pdfInputStream);
		return parseEstatePdfText(text);
	}
	
	private EstateSpiderResultVo parseEstatePdfText(String text) {
		if(StringUtils.isBlank(text)) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.file.pdf.text.null");
		}
		// ??????pdf??????
		Kv kv = EstateInfoUtil.parse(text);
		if(kv == null || kv.isEmpty()) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.file.pdf.parse.error");
		}
		log("?????????????????????PDF???????????????" + JSONUtil.toJsonStr(kv));
		EstateSpiderResultVo resultVo = new EstateSpiderResultVo();
		for(Object key : kv.keySet()) {
			if(key != null) {
				String k = key.toString();
				if("???????????????".equals(k)) {
					resultVo.setEstateNo(kv.getStr(key));
				}
				else if("??????".equals(k) || "??????".equals(k)) {
					resultVo.setStruct(kv.getStr(key));
				}
				else if("??????".equals(k) || "??????".equals(k)) {
					resultVo.setCategory(kv.getStr(key));
				}
				else if(k.contains("?????????") || "??????".equals(k)) {
					resultVo.setArea(kv.getStr(key));
				}
				else if(k.contains("????????????????????????")) {
					resultVo.setRecord(kv.getStr(key));
				}
				else if(k.contains("???????????????????????????")) {
					resultVo.setHolder(kv.getStr(key));
				}
			}
		}
		return resultVo;
	}
	
	private void stepLogout() {
		safeSleep(1500);
		String teikyoToken = pageLoginTo.getTeikyoToken();
		if(StringUtils.isNotBlank(teikyoToken)) {
			LogoutPage logoutPage = new LogoutPage();
			logoutPage.addParam("teikyoToken", teikyoToken);
			spider.urlRequester(logoutPage).htmlParser(logoutPage).run();
		}
	}
	
	private String buildPdfRelativePath(MyPageDataItem dataItem) {
		return DateUtil.format(info.getCaseStartDate(), "yyyyMMdd") + "/case_" + info.getCaseId() + "/" + dataItem.getAddr() + "???" + dataItem.getType() + "???" + dataItem.getReq_no() + ".pdf";
	}
	
	@Override
	public String getPdfStoreRootFolder() {
		String path = SysConfigCacheUtil.instance().getValue(Define.ConfigKeys.ESTATE_SPIDER_FETCH_PDF_STORE_PATH);
		if(StringUtils.isBlank(path)) {
			path = downloadService.getDownloadBasePath() + "/estate_pdf";
		}
		return path;
	}
	
	private void safeSleep(long m) {
		log("??????"+m+"??????");
		ThreadUtil.safeSleep(m);
	}
	
	private Kv getLoginUserInfo() {
		// "ABFC1296", "Z3#dj8Rm"
		return Kv.by("username", SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_SPIDER_FETCH_LOGIN_USERNAME))
				.set("password", SysConfigCacheUtil.instance().getValue(ConfigKeys.ESTATE_SPIDER_FETCH_LOGIN_PASSWORD));
	}
	
	private Kv split(String addr) {
		Kv kv = Kv.create();
		Kv addr1KvCopy = getAddr1Kv();
		for(Object key : addr1KvCopy.keySet()) {
			String addr1 = key.toString();
			if(addr.startsWith(addr1)) {
				kv.set("addr1", addr1).set("addr1code", addr1KvCopy.getStr(addr1));
				kv.set("addr2", addr.substring(addr1.length()));
				break;
			}
		}
		return kv;
	}
	
	private Kv getAddr1Kv() {
		if(addr1Kv == null) {
			addr1Kv = Kv.create();
			List<Record> list = Db.find("select text, code from estate_addr where type = ?", "addr1");
			if(list != null) {
				for (Record r : list) {
					addr1Kv.set(r.getStr("text"), r.getStr("code"));
				}
			}
		}
		return addr1Kv;
	}
	
	private void log(String msg) {
		System.out.println(DateUtil.formatDateTime(new Date()) + ": EstateSpider>>> " + msg);
	}
	
	@Data
	public static class EsateCaseInfo {
		
		private String caseId;
		
		private Date caseStartDate;
		
		private String recordId;
		
		private String estateId;
		
		private String addr;
		
		private String type;
		
	}
}
