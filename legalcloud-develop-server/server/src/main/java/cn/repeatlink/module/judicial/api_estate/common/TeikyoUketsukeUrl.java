/**
 * 
 */
package cn.repeatlink.module.judicial.api_estate.common;

/**
 * @author LAI
 * @date 2020-11-17 13:12
 */
public interface TeikyoUketsukeUrl {
	
	String URL_BASE = "https://www.touki.or.jp/TeikyoUketsuke";
	
	String URL_LOGIN = URL_BASE + "/common/login";
	
	String URL_LOGOUT = URL_BASE + "/common/logout-confirm";
	
	String URL_OPEN = URL_BASE + "/common/open";
	
	String URL_ESTATE_SEARCH = URL_BASE + "/mypage/my-page";
	
	String URL_ESTATE_LIST = URL_BASE + "/reqf/fudosan-list";
	
	String URL_KUIKI_DIALOG = URL_BASE + "/kuiki-dialog";
	
	String URL_SEIKYU_DELAY_CONFIRM = URL_BASE + "/common/seikyu-delay-confirm";
	
	String URL_FUDOSAN_SEIKYU = URL_BASE + "/reqf/fudosan-seikyu";
	
	String URL_MY_PAGE_LIST = URL_BASE + "/mypage/my-page-list";
	
	String URL_MY_PAGE_DELETE_LIST = URL_BASE + "/mypage/my-page-delete";
	
	String URL_MY_PAGE_DOWNLOAD_LIST = URL_BASE + "/mypage/my-page-download";

}
