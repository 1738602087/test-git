package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysUser;
import cn.repeatlink.module.manage.dto.UserInfo;

public interface ISysUserService {

	
	List<SysUser> findAll();
	
	/**
	 * 根据条件查询用户
	 * @param userInfo
	 * @return
	 */
	List<UserInfo> search(UserInfo userInfo);
	
	UserInfo findById(Long userId);
	
	
	void invalidUser(Long userId,Long operUserid);
	
	
	UserInfo addUser(UserInfo userInfo,Long userId);
	
	UserInfo updateUser(UserInfo userInfo,Long userId);
	/**
	 * 修改用户 可用/不可用状态
	 * @param userId
	 * @param status
	 */
	void updateUserEnable(Long userId,short status,Long operUserid);

	/**
	 * @param userId
	 * @return
	 */
	Long getRoleIdByUserId(Long userId);

	/**
	 * @param userName
	 * @return
	 */
	UserInfo getByUserName(String userName);

	/**
	 * @param userInfo
	 */
	void checkEmailRepeat(UserInfo userInfo);
	
	
	
}
