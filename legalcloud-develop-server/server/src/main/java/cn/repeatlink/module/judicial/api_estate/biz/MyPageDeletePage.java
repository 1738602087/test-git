/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

/**
 * MyPage一览删除
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class MyPageDeletePage extends FudosanSeikyuPage {
	
	@Override
	public String url() {
		return URL_MY_PAGE_DELETE_LIST;
	}

	@Override
	public String method() {
		return "POST";
	}

	@Override
	public boolean isForm() {
		return true;
	}

	public MyPageDeletePage param(String teikyoToken, String myPageCheckBox, String myPageStatusCheckBox, String myPageTable_sibori) {
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
	
}
