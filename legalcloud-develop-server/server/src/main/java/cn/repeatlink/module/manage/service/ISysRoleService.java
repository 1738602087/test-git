package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysRole;
import cn.repeatlink.framework.common.Constant.RoleKey;
import cn.repeatlink.module.manage.dto.RoleInfo;
import cn.repeatlink.module.manage.dto.RoleSimpleInfo;

public interface ISysRoleService {

	
	List<SysRole> findAll();
	
	/**
	 * 根据条件查询角色
	 * @param userInfo
	 * @return
	 */
	List<RoleInfo> search(RoleInfo userInfo);

	/**
	 * 添加角色
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	RoleInfo addRole(RoleInfo roleInfo, Long operUserId);

	/**
	 * 根据ID查找角色信息
	 * @param roleId
	 * @return
	 */
	RoleInfo findById(Long roleId);

	/**
	 * 更新角色信息
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	RoleInfo updateRole(RoleInfo roleInfo, Long operUserId);

	/**
	 * @return
	 */
	List<RoleSimpleInfo> selectAllSimpleList();

	/**
	 * @param roleKey
	 * @return
	 */
	Long getFirstRoleIdByRoleKey(RoleKey roleKey);

	/**
	 * @param roleKey
	 * @return
	 */
	List<RoleInfo> getRolesByRoleKey(RoleKey roleKey);
	
	
	
}
