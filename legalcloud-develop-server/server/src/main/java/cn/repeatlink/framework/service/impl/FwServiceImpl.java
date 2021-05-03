/**
 * 
 */
package cn.repeatlink.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.framework.common.Constant.MenuType;
import cn.repeatlink.framework.service.IFwService;
import cn.repeatlink.module.manage.dto.MenuInfo;
import cn.repeatlink.module.manage.service.ISysMenuService;
import cn.repeatlink.module.manage.service.ISysRoleService;
import cn.repeatlink.module.manage.service.ISysUserService;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-08-25 16:01
 */

@Log4j2
@Service
public class FwServiceImpl implements IFwService {
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Autowired
	private ISysMenuService sysMenuService;
	
	@Override
	public List<MenuInfo> getMenuListByUserId(Long userId) {
		Long roleId = this.sysUserService.getRoleIdByUserId(userId);
		 List<MenuInfo> list = this.sysMenuService.getMenuLayerListByRole(roleId);
		 this.excludeEmptyDir(list);
		 return list;
	}
	
	/**
	 * 去掉空目录（没有子目录和子菜单）
	 * @param list
	 */
	private void excludeEmptyDir(List<MenuInfo> list) {
		if(list != null) {
			 for (int i = 0; i < list.size(); i++) {
				MenuInfo info = list.get(i);
				if(info.getSubMenuList() != null && !info.getSubMenuList().isEmpty()) {
					this.excludeEmptyDir(info.getSubMenuList());
				}
				if(info.getSubMenuList() == null || info.getSubMenuList().isEmpty()) {
					if(MenuType.DIR.equalsIgnoreCase(info.getMenuType())) {
						list.remove(i);
						i--;
					}
				}
			}
		 }
	}

}
