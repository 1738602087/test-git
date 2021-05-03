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
 * 不动产API fetch服务
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
		// 目前只能一个一个的获取（一个账号只能同时存在一个有效token，刷新时间间隔频繁将导致503错误）
		synchronized (EstateSpiderServiceImpl.class) {
			EstateSpiderResultVo vo = null;
			try {
				info = buildInfo(baseVo);
				fixBaseVo(baseVo);
				
				if(spider == null || !stepCheckToken()) {
					boolean login = stepLogin();
					if(!login) {
						// 登录失败？重试？返回错误信息？
						safeSleep(3000);
						log("尝试重试登录");
						login = stepLogin();
						// 
						if(!login) {
							throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.login.error");
						}
					}
				}
				// 已登录
				// 跳转到不动产检索页面
				stepGotoEstateSearchPage();
				// 进行检索
				stepGotoEstateSearchResultPage(baseVo.getType(), baseVo.getAddr(), baseVo.getHouse_id());
				// 进入不动产登记情报检索结果页面
				stepGotoEstateRegResultPage();
				// 检查情报登记情况
				stepCheckEstateInfoRegResult();
				// 下载不动产登记情报文件
				stepDownloadEstatePdf();
				// 检查PDF文件
				stepCheckPdfFile();
				// 读取PDF文件
				vo = stepReadPdfFile();
				
			} catch (Throwable e) {
				// 异常处理
				log(e.getMessage());
				e.printStackTrace();
				vo = new EstateSpiderResultVo();
				vo.setErrorMsg(StringUtils.isBlank(e.getMessage()) ? "error" : e.getMessage());
			} finally {
				// 清空页面
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
		log("检查是否登录或登录状态是否可用");
		try {
			page = new MyPagePage();
			spider.htmlParser(page).urlRequester(page).run();
		} catch (Exception e) {
			log("进入my-page异常: " + e.getMessage());
		}
		if(page == null || StringUtils.isBlank(page.teikyoToken())) {
			return false;
		}
		return true;
	}
	
	private boolean stepLogin() {
		log("打开登录页面");
		LoginPage loginPage = new LoginPage();
		spider = Spider.build().htmlParser(loginPage).urlRequester(loginPage);
		spider.run();
		safeSleep(2000);
		log("使用帐号密码进行登录");
		Kv user = getLoginUserInfo();
		pageLoginTo = loginPage.login(user.getStr("username"), user.getStr("password"));
		if(!pageLoginTo.isSigned() && pageLoginTo.isNeed_force_login()) {
			log("需要强制登录");
			safeSleep(2000);
			log("进行强制登录");
			pageLoginTo = pageLoginTo.forceLogin();
		}
		if(pageLoginTo.isSigned()) {
			log("已成功登录, teikyoToken=" + pageLoginTo.getTeikyoToken());
			return true;
		} else {
			log("登录失败");
			return false;
		}
	}
	
	private void stepGotoEstateSearchPage() {
		safeSleep(2000);
		log("进入不动产入力检索页面");
		pageEstateSearchPage = pageLoginTo.gotoEstateSearchPage();
	}
	
	private void stepGotoEstateSearchResultPage(String type, String addr, String houseId) {
		safeSleep(2000);
		log("检索不动产情报");
		String shozaiType = ShozaiType.getShozaiType(type);
		Kv addrKv = split(addr);
		String todofukenShozai = addrKv.getStr("addr1code");
		String chibanKuiki = addrKv.getStr("addr2");
		String chibanKaoku = houseId;
		pageEstateListPage = pageEstateSearchPage.gotoEstateListPage(shozaiType, todofukenShozai, chibanKuiki, chibanKaoku);
	}

	private void stepGotoEstateRegResultPage() {
		safeSleep(3000);
		log("请求不动产登记情报");
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
		log("下载不动产登记情报PDF文件");
		// 取第一个条目
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
		log("读取不动产登记情报PDF文件");
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
		// 解析pdf内容
		Kv kv = EstateInfoUtil.parse(text);
		if(kv == null || kv.isEmpty()) {
			throw JudicialRuntimeException.build("judicial.case.estate.fetch.error.estate.file.pdf.parse.error");
		}
		log("不动产登记情报PDF文件解析：" + JSONUtil.toJsonStr(kv));
		EstateSpiderResultVo resultVo = new EstateSpiderResultVo();
		for(Object key : kv.keySet()) {
			if(key != null) {
				String k = key.toString();
				if("不動産番号".equals(k)) {
					resultVo.setEstateNo(kv.getStr(key));
				}
				else if("構造".equals(k) || "地番".equals(k)) {
					resultVo.setStruct(kv.getStr(key));
				}
				else if("種類".equals(k) || "地目".equals(k)) {
					resultVo.setCategory(kv.getStr(key));
				}
				else if(k.contains("床面積") || "地積".equals(k)) {
					resultVo.setArea(kv.getStr(key));
				}
				else if(k.contains("原因及びその日付")) {
					resultVo.setRecord(kv.getStr(key));
				}
				else if(k.contains("権利者その他の事項")) {
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
		return DateUtil.format(info.getCaseStartDate(), "yyyyMMdd") + "/case_" + info.getCaseId() + "/" + dataItem.getAddr() + "（" + dataItem.getType() + "）" + dataItem.getReq_no() + ".pdf";
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
		log("等待"+m+"毫秒");
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
