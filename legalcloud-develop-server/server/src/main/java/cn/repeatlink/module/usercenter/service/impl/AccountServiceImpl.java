/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.repeatlink.common.entity.SysUser;
import cn.repeatlink.common.mapper.SysUserMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysUserService;
import cn.repeatlink.module.usercenter.common.UsercenterResultCode;
import cn.repeatlink.module.usercenter.service.IAccountService;
import cn.repeatlink.module.usercenter.vo.AccountInfo;
import cn.repeatlink.module.usercenter.vo.UpdatePwdVo;

/**
 * @author LAI
 * @date 2020-08-20 15:26
 */

@Service
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Override
	public void updatePwd(Long userId, UpdatePwdVo vo) {
		this.checkUserPwd(vo.getNewpwd(), vo.getNewpwdc());
		SysUser sysUser = this.sysUserMapper.selectByPrimaryKeyForUpdate(userId);
		if(!SecurityUtils.matchesPassword(vo.getOldpwd(), sysUser.getPassword())) {
			// 输入旧密码不正确
			throw new BaseRuntimeException(UsercenterResultCode.USER_PASSWORD_ERROR);
		}
		sysUser.setPassword(SecurityUtils.encryptPassword(vo.getNewpwd()));
		sysUser.setUpdateBy(userId);
		sysUser.setUpdateTime(new Date());
		this.sysUserMapper.updateByPrimaryKey(sysUser);
	}
	
	@Override
	public void updateAccount(Long userId, AccountInfo info) {
		SysUser sysUser = this.sysUserMapper.selectByPrimaryKeyForUpdate(userId);
		sysUser.setEmail(info.getEmail());
		sysUser.setSex(info.getGender());
		sysUser.setName(info.getName());
		sysUser.setPhonenumber(info.getPhonenumber());
		sysUser.setUpdateBy(userId);
		sysUser.setUpdateTime(new Date());
		this.sysUserMapper.updateByPrimaryKey(sysUser);
	}
	
	@Override
	public void changeUserName(Long userId, String userName) {
		UserInfo userInfo = this.sysUserService.findById(userId);
		userInfo.setUserName(userName);
		this.sysUserService.updateUser(userInfo, userId);
	}
	
	private void checkUserPwd(String pwd, String pwdc) {
		if(pwd == null) {
			
		}
		if(!ReUtil.isMatch("^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![a-z\\\\W]+$)(?![A-Z\\\\W]+$)(?![0-9\\\\W]+$)[a-zA-Z0-9\\\\W]{8,16}$", pwd)) {
			throw new BaseRuntimeException(UsercenterResultCode.USER_PASSWORD_INVALID);
		}
		if(!StrUtil.equals(pwd, pwdc)) {
			throw new BaseRuntimeException(UsercenterResultCode.USER_PASSWORDC_NOT);
		}
	}

}
