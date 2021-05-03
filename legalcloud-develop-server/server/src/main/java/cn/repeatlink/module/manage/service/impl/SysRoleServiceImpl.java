package cn.repeatlink.module.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.IdUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysRole;
import cn.repeatlink.common.entity.SysRoleMenu;
import cn.repeatlink.common.entity.SysUser;
import cn.repeatlink.common.mapper.SysRoleMapper;
import cn.repeatlink.common.mapper.SysRoleMenuMapper;
import cn.repeatlink.framework.common.Constant.RoleKey;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.RoleInfo;
import cn.repeatlink.module.manage.dto.RoleSimpleInfo;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysRoleService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public List<SysRole> findAll() {

		return sysRoleMapper.selectAll();
	}


	@Override
	public List<RoleInfo> search(RoleInfo roleInfo) {
		List<RoleInfo> users = this.sysRoleMapper.search(roleInfo);
		if(users!=null) { 
			
		}
		return users;
	}
	
	@Override
	public RoleInfo addRole(RoleInfo roleInfo, Long operUserId) {
		validateRoleInfo(roleInfo);

		SysRole role = new SysRole();
		role.setRoleKey(IdUtil.fastSimpleUUID());
		// role.setDataScope(roleInfo.getDataScope());
		role.setRoleName(roleInfo.getRoleName());
		role.setRoleSort(1);
		role.setStatus(Constant.STATUS_VALID);
		role.setCreateBy(operUserId);
		role.setCreateTime(new Date());
		sysRoleMapper.insert(role);

		// 更新角色菜单
		this.updateRoleMenu(role.getRoleId(), roleInfo.getMenuIds(), operUserId);
		
		roleInfo.setRoleId(role.getRoleId());
		return roleInfo;
	}
	
	@Override
	public RoleInfo updateRole(RoleInfo roleInfo, Long operUserId) {
		validateRoleInfo(roleInfo);
		if(roleInfo.getRoleId() == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NOT_FOUND);
		}
		SysRole role = this.sysRoleMapper.selectByPrimaryKey(roleInfo.getRoleId());
		// 系统管理员不允许更新
		if("admin".equals(role.getRoleKey())) {
			throw new BaseRuntimeException(ManagerResultCode.ROLE_ADMIN_FORBIDDEN_UPDATE);
		}
		// role.setDataScope(roleInfo.getDataScope());
		role.setRoleName(roleInfo.getRoleName());
		if(roleInfo.getStatus() != null) {
			if(!Constant.STATUS_VALID.equals(roleInfo.getStatus()) && "admin".equals(role.getRoleKey())) {
				throw new BaseRuntimeException(ManagerResultCode.ROLE_ADMIN_FORBIDDEN_UPDATE);
			}
			role.setStatus(roleInfo.getStatus());
		}
		role.setUpdateBy(operUserId);
		role.setUpdateTime(new Date());
		sysRoleMapper.updateByPrimaryKey(role);

		// 更新角色菜单
		this.updateRoleMenu(role.getRoleId(), roleInfo.getMenuIds(), operUserId);
		
		return roleInfo;
	}
	
	@Override
	public RoleInfo findById(Long roleId) {
		SysRole sysrole = sysRoleMapper.selectByPrimaryKey(roleId);
		if (sysrole == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NOT_FOUND);
		}
		RoleInfo result = new RoleInfo();
		result.setRoleId(sysrole.getRoleId());
		result.setRoleName(sysrole.getRoleName());
		result.setStatus(sysrole.getStatus());
		// result.setDataScope(sysrole.getDataScope());
		result.setRoleKey(sysrole.getRoleKey());
		
		// 获取角色已有的菜单ID集合
		result.setMenuIds(this.getMenuIdListByRole(roleId));
		return result;
	}
	
	
	public List<Long> getMenuIdListByRole(Long roleId) {
		List<Long> idList = new ArrayList<>();
		List<SysRoleMenu> list = this.sysRoleMenuMapper.selectAllByRole4Menu(roleId);
		if(list != null) {
			for (SysRoleMenu sysRoleMenu : list) {
				if(sysRoleMenu == null || !Constant.STATUS_VALID.equals(sysRoleMenu.getStatus())) {
					continue;
				}
				idList.add(sysRoleMenu.getMenuId());
			}
		}
		return idList;
	}
	
	public void updateRoleMenu(Long roleId, List<Long> menuIdList, Long userId) {
		List<SysRoleMenu> list = this.sysRoleMenuMapper.selectAllByRole(roleId);
		Date now = new Date();
		if(list != null) {
			for (SysRoleMenu sysRoleMenu : list) {
				if(menuIdList.contains(sysRoleMenu.getRoleId()) && menuIdList.remove(sysRoleMenu.getMenuId())) {
					sysRoleMenu.setStatus(Constant.STATUS_VALID);
				} else {
					this.sysRoleMenuMapper.deleteByPrimaryKey(roleId, sysRoleMenu.getMenuId());
					continue;
				}
				sysRoleMenu.setUpdateBy(userId);
				sysRoleMenu.setUpdateTime(now);
				sysRoleMenu.setStatus(Constant.STATUS_VALID);
				this.sysRoleMenuMapper.updateByPrimaryKey(sysRoleMenu);
			}
		}
		for (Long menuId : menuIdList) {
			SysRoleMenu sysRoleMenu = new SysRoleMenu();
			sysRoleMenu.setRoleId(roleId);
			sysRoleMenu.setMenuId(menuId);
			sysRoleMenu.setCreateBy(userId);
			sysRoleMenu.setCreateTime(now);
			sysRoleMenu.setStatus(Constant.STATUS_VALID);
			this.sysRoleMenuMapper.insert(sysRoleMenu);
		}
	}
	
	@Override
	public List<RoleSimpleInfo> selectAllSimpleList() {
		return this.sysRoleMapper.selectAllSimpleList();
	}
	
	@Override
	public List<RoleInfo> getRolesByRoleKey(RoleKey roleKey) {
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleKey(roleKey.name());
		return this.sysRoleMapper.search(roleInfo);
	}
	
	@Override
	public Long getFirstRoleIdByRoleKey(RoleKey roleKey) {
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRoleKey(roleKey.name());
		List<RoleInfo> list = this.sysRoleMapper.search(roleInfo);
		if(list != null && list.size() > 0) {
			return list.get(0).getRoleId();
		}
		return null;
	}
	
	/**
	 * 校验role 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateRoleInfo(RoleInfo roleInfo) {
		if (roleInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.DATA_NULL);
		}

		if (StringUtils.isEmpty(roleInfo.getRoleName())) {
			throw new BaseRuntimeException(ManagerResultCode.ROLE_NAME_NULL);
		}
		if (roleInfo.getRoleName().length() < 2 || roleInfo.getRoleName().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.ROLE_NAME_INVALID);
		}
//		if (StringUtils.isEmpty(roleInfo.getDataScope())) {
//			throw new BaseRuntimeException(ManagerResultCode.USER_USERNAME_NULL);
//		}

	}

	private String getSex(Integer sex) {

		if (sex != null && sex == 1) {
			return "男";
		} else if (sex != null && sex == 0) {
			return "女";
		}
		return "未知";
	}


	private SysUser setUserInfo(SysUser sysuser, UserInfo userInfo) {

		sysuser = sysuser == null ? new SysUser() : sysuser;
		if (userInfo == null) {
			return sysuser;
		}
		sysuser.setUserName(userInfo.getUserName());
		sysuser.setName(userInfo.getName());
		sysuser.setEmail(userInfo.getEmail());
		sysuser.setPhonenumber(userInfo.getPhonenumber());
		sysuser.setRemark(userInfo.getRemark());
		sysuser.setSex(userInfo.getSex());
		return sysuser;
	}
}
