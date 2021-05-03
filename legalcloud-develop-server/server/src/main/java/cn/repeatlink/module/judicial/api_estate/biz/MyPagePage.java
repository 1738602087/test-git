/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.biz;

/**
 * MyPage一览删除
 * @author LAI
 * @date 2020-11-17 15:20
 */
public class MyPagePage extends FudosanSeikyuPage {
	
	@Override
	public String url() {
		return URL_ESTATE_SEARCH;
	}

	@Override
	public String method() {
		return "GET";
	}

	@Override
	public boolean isForm() {
		return false;
	}

}
