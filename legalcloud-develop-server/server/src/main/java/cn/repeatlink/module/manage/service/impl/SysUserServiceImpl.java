package cn.repeatlink.module.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysUser;
import cn.repeatlink.common.entity.SysUserRole;
import cn.repeatlink.common.mapper.SysUserMapper;
import cn.repeatlink.common.mapper.SysUserRoleMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.CommonUtil;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysUserService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<SysUser> findAll() {

		return sysUserMapper.selectAll();
	}

	@Override
	public UserInfo findById(Long userId) {
		SysUser sysuser = sysUserMapper.selectByPrimaryKey(userId);
		if (sysuser == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}
		UserInfo result = new UserInfo();
		result.setUserId(sysuser.getUserId());
		result.setName(sysuser.getName());
		result.setUserName(sysuser.getUserName());
		result.setEmail(sysuser.getEmail());
		result.setPhonenumber(sysuser.getPhonenumber());
		result.setSex(sysuser.getSex());
		result.setStatus(sysuser.getStatus());
		result.setEnable(sysuser.getEnable()!=null&&Constant.ENABLE.equals(sysuser.getEnable()));
		result.setRemark(sysuser.getRemark());
		result.setRoleId(this.getRoleIdByUserId(userId));
		result.setUserType(sysuser.getUserType());
		return result;
	}

	@Override
	public void invalidUser(Long userId, Long operUserid) {

		SysUser sysuser = sysUserMapper.selectByPrimaryKeyForUpdate(userId);
		if (sysuser == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}
		if (sysuser != null && sysuser.getStatus() != null && Constant.STATUS_VALID.equals(sysuser.getStatus())) {
			sysuser.setStatus(Constant.STATUS_INVALID);
			sysuser.setUpdateTime(new Date());
			sysuser.setUpdateBy(operUserid);
			sysUserMapper.updateByPrimaryKey(sysuser);
		}

	}
	
	@Override
	public UserInfo getByUserName(String userName) {
		SysUser sysUser = this.sysUserMapper.selectByUserName(userName);
		if(sysUser == null) return null;
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(sysUser.getUserId());
		userInfo.setName(sysUser.getName());
		userInfo.setUserName(sysUser.getUserName());
		userInfo.setEmail(sysUser.getEmail());
		userInfo.setRoleId(this.getRoleIdByUserId(userInfo.getUserId()));
		userInfo.setSex(sysUser.getSex());
		userInfo.setEnable(Constant.ENABLE.equals(sysUser.getEnable()));
		userInfo.setPhonenumber(sysUser.getPhonenumber());
		return userInfo;
	}

	@Override
	public List<UserInfo> search(UserInfo userInfo) {
		List<UserInfo> users = this.sysUserMapper.search(userInfo);
		if(users!=null) { 
			for(UserInfo user:users) {
				user.setPassword(null);
				user.setSex(getSex(Integer.parseInt(user.getSex())));
			}
		}
//		List<UserInfo> results = new ArrayList<>();
//		if (users != null && users.size() > 0) {
//			for (int index = 0; index < users.size(); index++) {
//				SysUser user = users.get(index);
//				UserInfo result = new UserInfo();
//				result.setUserId(user.getUserId());
//				result.setName(user.getName());
//				result.setUserName(user.getUserName());
//				result.setEmail(user.getEmail());
//				result.setPhonenumber(user.getPhonenumber());
//				result.setSex(getSex(Integer.parseInt(user.getSex())));
//				result.setStatus(user.getStatus());
//				result.setEnable(user.getEnable());
//				results.add(result);
//			}
//		}
		return users;
	}
	
	@Override
	public Long getRoleIdByUserId(Long userId) {
		SysUserRole userRole = this.sysUserRoleMapper.selectByUserId(userId);
		if(userRole != null && Constant.STATUS_VALID.equals(userRole.getStatus())) {
			return userRole.getRoleId();
		}
		return null;
	}
	
	public void updateUserRole(Long userId, Long roleId, Long operUserId) {
		SysUserRole userRole = this.sysUserRoleMapper.selectByUserId(userId);
		if(roleId != null) {
			if(userRole == null) {
				userRole = new SysUserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRole.setCreateBy(operUserId);
				userRole.setCreateTime(new Date());
				userRole.setStatus(Constant.STATUS_VALID);
				this.sysUserRoleMapper.insert(userRole);
			} else {
				userRole.setStatus(Constant.STATUS_VALID);
				userRole.setUpdateBy(operUserId);
				userRole.setUpdateTime(new Date());
				this.sysUserRoleMapper.updateByUserId(userRole);
			}
		} else if(userRole != null) {
			userRole.setStatus(Constant.STATUS_INVALID);
			userRole.setUpdateBy(operUserId);
			userRole.setUpdateTime(new Date());
			this.sysUserRoleMapper.updateByPrimaryKey(userRole);
		}
	}

	private String getSex(Integer sex) {

		if (sex != null && sex == 1) {
			return "男";
		} else if (sex != null && sex == 0) {
			return "女";
		}
		return "未知";
	}

	@Override
	public void updateUserEnable(Long userId, short status, Long operUserid) {
		SysUser sysuser = this.sysUserMapper.selectByPrimaryKeyForUpdate(userId);
		if (sysuser == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}
		if (!sysuser.getEnable().equals(status)) {
			sysuser.setEnable(status);
			sysuser.setUpdateTime(new Date());
			sysuser.setUpdateBy(operUserid);
			this.sysUserMapper.updateByPrimaryKey(sysuser);
		}

	}

	@Override
	public UserInfo addUser(UserInfo userInfo, Long operUserId) {
		validateUserInfo(userInfo, true);
		checkUserNameRepeat(userInfo);
		checkEmailRepeat(userInfo);

		SysUser sysuser = setUserInfo(null, userInfo);
		sysuser.setPassword(SecurityUtils.encryptPassword(userInfo.getPassword()));
		sysuser.setStatus(Constant.STATUS_VALID);
		sysuser.setEnable(Constant.ENABLE);
		sysuser.setCreateBy(operUserId);
		sysuser.setCreateTime(new Date());
		sysUserMapper.insert(sysuser);

		userInfo.setUserId(sysuser.getUserId());
		// 更新用户角色
		this.updateUserRole(userInfo.getUserId(), userInfo.getRoleId(), operUserId);
		return userInfo;
	}

	@Override
	public UserInfo updateUser(UserInfo userInfo, Long operUserId) {
		validateUserInfo(userInfo, false);
		checkUserNameRepeat(userInfo);
		checkEmailRepeat(userInfo);

		SysUser sysuser = this.sysUserMapper.selectByPrimaryKeyForUpdate(userInfo.getUserId());
		if (sysuser == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}

		sysuser = setUserInfo(sysuser, userInfo);
		sysuser.setEnable(Boolean.TRUE.equals(userInfo.getEnable()) ? Constant.ENABLE : Constant.DISABLED);
		sysuser.setUpdateBy(operUserId);
		sysuser.setUpdateTime(new Date());
		sysUserMapper.updateByPrimaryKey(sysuser);
		// 更新用户角色
		this.updateUserRole(userInfo.getUserId(), userInfo.getRoleId(), operUserId);
		return null;
	}

	/**
	 * 校验user 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateUserInfo(UserInfo userInfo, boolean checkPasswod) {
		if (userInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_INFO_NULL);
		}

		if (StringUtils.isEmpty(userInfo.getName())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_NULL);
		}
		if (userInfo.getName().length() < 2 || userInfo.getName().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_INVALID);
		}
		if (StringUtils.isEmpty(userInfo.getUserName())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USERNAME_NULL);
		} else if (!userInfo.getUserName().matches("^[A-Za-z0-9]{2,16}") && !Validator.isEmail(userInfo.getUserName())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USERNAME_INVALID);
		}
		if (StringUtils.isEmpty(userInfo.getEmail())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_EMAIL_NULl);
		}
		if (!CommonUtil.validateEmail(userInfo.getEmail())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_EMAIL_INVALID);
		}
		if (StringUtils.isNotEmpty(userInfo.getPhonenumber())
				&& !CommonUtil.validatePhoneNum(userInfo.getPhonenumber())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_PHONENUM_INVALID);
		}
		if(checkPasswod || StringUtils.isNotBlank(userInfo.getPassword())) {
			if(checkPasswod && StringUtils.isBlank(userInfo.getPassword())) {
				throw new BaseRuntimeException(ManagerResultCode.USER_PASSWORD_NULL);
			}
			if(!ReUtil.isMatch("[0-9a-zA-Z]{6,16}", userInfo.getPassword())) {
				throw new BaseRuntimeException(ManagerResultCode.USER_PASSWORD_INVALID);
			}
		}

	}

	/**
	 * 检查UserInfo的用户账号名是否重复
	 * 
	 * @param userInfo
	 */
	private void checkUserNameRepeat(UserInfo userInfo) {

		SysUser sysUser = this.sysUserMapper.checkUserNameUnique(userInfo.getUserName());
		if (sysUser == null) {
			return;
		}
		if (sysUser != null) {
			if (userInfo.getUserId() == null) {
				throw new BaseRuntimeException(ManagerResultCode.USER_USERNAME_REPEAT);
			} else if (!userInfo.getUserId().equals(sysUser.getUserId())) {
				throw new BaseRuntimeException(ManagerResultCode.USER_USERNAME_REPEAT);
			}
		}
	}

	/**
	 * 检查用户的email 地址是否重复
	 * 
	 * @param
	 */
	@Override
	public void checkEmailRepeat(UserInfo userInfo) {
		SysUser sysUser = this.sysUserMapper.checkEmailUnique(userInfo.getEmail());
		if (sysUser != null) {
			if (userInfo.getUserId() == null) {
				throw new BaseRuntimeException(ManagerResultCode.USER_EMAIL_REPEAT);
			} else if (!userInfo.getUserId().equals(sysUser.getUserId())) {
				throw new BaseRuntimeException(ManagerResultCode.USER_EMAIL_REPEAT);
			}
		}
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
		
		// 用户类型
		if(userInfo.getUserType() != null) {
			sysuser.setUserType(userInfo.getUserType());
		}
		
		if(StringUtils.isNotBlank(userInfo.getPassword())) {
			sysuser.setPassword(SecurityUtils.encryptPassword(userInfo.getPassword().trim()));
		}
		return sysuser;
	}
}
