/**
 * 
 */
package cn.repeatlink.module.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.common.entity.SysMenu;
import cn.repeatlink.common.mapper.SysMenuMapper;
import cn.repeatlink.module.manage.common.Constant.MenuVisible;
import cn.repeatlink.module.manage.dto.MenuInfo;
import cn.repeatlink.module.manage.service.ISysMenuService;

/**
 * @author LAI
 * @date 2020-08-18 10:53
 */

@Service
public class SysMenuServiceImpl implements ISysMenuService {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Override
	public List<MenuInfo> getAllMenuLayerList() {
		List<SysMenu> list = this.sysMenuMapper.selectAllBySort();
		List<MenuInfo> infoList = this.getMenuLayerList(list);
		return infoList;
	}
	
	@Override
	public List<MenuInfo> getMenuLayerListByRole(Long roleId) {
		List<SysMenu> list = this.sysMenuMapper.selectAllByRole(roleId);
		List<MenuInfo> infoList = this.getMenuLayerList(list);
		return infoList;
	}
	
	private List<MenuInfo> getMenuLayerList(List<SysMenu> menuList) {
		List<MenuInfo> infoList = new ArrayList<>();
		if(menuList != null && menuList.size() > 0) {
			Map<Long, MenuInfo> infoMap = new HashMap<>();
			for(SysMenu menu : menuList) {
				if(menu == null || !MenuVisible.SHOW.equals(menu.getVisible())) {
					continue;
				}
				MenuInfo info = new MenuInfo();
				info.setMenuId(menu.getMenuId());
				info.setMenuName(menu.getMenuName());
				info.setCompoment(menu.getComponent());
				info.setParentId(menu.getParentId());
				info.setPath(menu.getPath());
				info.setIcon(menu.getIcon());
				info.setMenuType(menu.getMenuType());
				
				infoMap.put(info.getMenuId(), info);
				if(info.getParentId() == null) {
					infoList.add(info);
				} else {
					MenuInfo parent = infoMap.get(info.getParentId());
					parent.getSubMenuList().add(info);
				}
			}
		}
		return infoList;
	}

}
