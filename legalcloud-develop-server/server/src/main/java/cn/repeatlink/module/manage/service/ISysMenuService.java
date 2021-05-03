/**
 * 
 */
package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.module.manage.dto.MenuInfo;

/**
 * @author LAI
 * @date 2020-08-18 10:53
 */
public interface ISysMenuService {

	/**
	 * 获取所有菜单结构
	 * @return
	 */
	List<MenuInfo> getAllMenuLayerList();

	/**
	 * 获取某个角色的菜单结构
	 * @param roleId
	 * @return
	 */
	List<MenuInfo> getMenuLayerListByRole(Long roleId);

}
