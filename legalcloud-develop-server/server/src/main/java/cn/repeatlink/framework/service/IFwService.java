/**
 * 
 */
package cn.repeatlink.framework.service;

import java.util.List;

import cn.repeatlink.module.manage.dto.MenuInfo;

/**
 * @author LAI
 * @date 2020-08-25 16:01
 */
public interface IFwService {

	/**
	 * @param userId
	 * @return
	 */
	List<MenuInfo> getMenuListByUserId(Long userId);

}
