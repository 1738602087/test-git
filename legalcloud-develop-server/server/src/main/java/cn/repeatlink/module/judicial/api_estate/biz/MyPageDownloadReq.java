/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.io.FileUtil;

/**
 * MyPage一览删除
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class MyPageDownloadReq extends FudosanSeikyuPage {
	
	private String fileStorePath;
	
	@Override
	public String url() {
		return URL_MY_PAGE_DOWNLOAD_LIST;
	}

	@Override
	public String method() {
		return "POST";
	}

	@Override
	public boolean isForm() {
		return true;
	}
	
	@Override
	public boolean isDownloadFile() {
		return true;
	}
	
	@Override
	public void download(InputStream is, String fileName) {
		if(StringUtils.isBlank(fileStorePath)) {
			
		}
		FileUtil.writeFromStream(is, fileStorePath);
	}
	
	public MyPageDownloadReq fileStorePath(String fileStorePath) {
		this.fileStorePath = fileStorePath;
		return this;
	}

	public MyPageDownloadReq param(String teikyoToken, String myPageCheckBox, String myPageStatusCheckBox, String myPageTable_sibori) {
		super.addParam("teikyoToken", teikyoToken);
		super.addParam("myPageCheckBox", myPageCheckBox);
		super.addParam("myPageCheckBoxTmp", myPageCheckBox);
		super.addParam("myPageStatusCheckBox", myPageStatusCheckBox);
		super.addParam("myPageTable_sibori", myPageTable_sibori);
		
		super.addParam("myPageSearchSelect", "false");
		super.addParam("myPageActionId", "");
		super.addParam("myPageRestoreCheckedIdList", "");
		super.addParam("myPageRestoreActionReturn", "");
		return this;
	}
	
	public String getPdfFilePath() {
		return fileStorePath;
	}
	
	public File getPdfFile() {
		return FileUtil.newFile(getPdfFilePath());
	}
}
