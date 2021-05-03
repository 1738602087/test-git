package cn.repeatlink.framework.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.repeatlink.framework.bean.PageDomain;

/**
 * 分页处理
 * @author tlq20
 *
 */
public class RLPageHelper extends PageHelper {
	
	public static <E,T> Page<E> startPage(PageDomain<T> pageDomain) {
		return startPage(pageDomain, null);
	}

	  public static <E,T> Page<E> startPage(PageDomain<T> pageDomain, Map<String, String> alias) {
		  	
		  	int pageNum=pageDomain.getCurrent_page()==null?1:pageDomain.getCurrent_page();
		  	int pageSize=pageDomain.getPage_size()==null?PageDomain.DEFALUT_PAGE_SIZE:pageDomain.getPage_size();
		  	
	        Page<E> page = null;
	        if(StringUtils.isNoneEmpty(pageDomain.getField())) {
	        	String orderBy=pageDomain.getField();
	        	if(StringUtils.isNoneEmpty(pageDomain.getOrder())&&"desc".equalsIgnoreCase(pageDomain.getOrder())) {
	        		orderBy= alias(orderBy, alias) +" desc";
	        	} else {
	        		orderBy = alias(orderBy, alias);
	        	}
	        	page=startPage(pageNum,pageSize,orderBy);
	        }else {
	        	page=startPage(pageNum,pageSize);
	        }
	        
//	        startPage(pageDomain.gt, pageSize);
//	        page.setOrderBy(orderBy);
	        return page;
	    }

	  private static String alias(String orderBy, Map<String, String> alias) {
		  if(alias == null || !alias.containsKey(orderBy)) {
			  return orderBy;
		  }
		  return alias.get(orderBy);
	  }
}
