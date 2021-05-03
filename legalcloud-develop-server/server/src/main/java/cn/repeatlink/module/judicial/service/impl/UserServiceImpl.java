/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.repeatlink.common.entity.LawGroup;
import cn.repeatlink.common.entity.UserJudicial;
import cn.repeatlink.common.mapper.LawGroupMapper;
import cn.repeatlink.common.mapper.UserJudicialMapper;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IUserService;
import cn.repeatlink.module.judicial.util.PasswordUtil;
import cn.repeatlink.module.judicial.vo.UserInfo;
import cn.repeatlink.module.manage.service.ISysUserService;

/**
 * @author LAI
 * @date 2020-09-24 13:52
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserJudicialMapper userJudicialMapper;
	
	@Autowired
	private LawGroupMapper lawGroupMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Override
	public UserInfo getInfo(String userId) {
		UserInfo info = null;
		UserJudicial user = this.userJudicialMapper.selectByPrimaryKey(userId);
		LawGroup lawGroup = this.lawGroupMapper.selectByPrimaryKey(user.getGroupId());
		
		info = new UserInfo();
		info.setFamname(user.getFamname());
		info.setFamname_kana(user.getFamnameKana());
		info.setGivename(user.getGivename());
		info.setGivename_kana(user.getGivenameKana());
		info.setFullname(user.getFullname());
		info.setFullname_kana(user.getFullnameKana());
		if(user.getBirthday() != null) {
			info.setBirthday(DateUtil.formatDate(user.getBirthday()));
		}
		info.setGender(user.getGender());
		info.setEmail(user.getEmail());
		info.setAddr1(user.getAddr1());
		info.setAddr2(user.getAddr2());
		info.setAddr3(user.getAddr3());
		info.setLogin_id(user.getLoginId());
		if(lawGroup != null) {
			info.setGroup(lawGroup.getGroupName());
		}
		if(user.getAvatar() != null) {
			info.setAvatar(this.convertAvatar(new String(user.getAvatar())));
		}
		
		return info;
	}
	
	@Transactional
	@Override
	public void updatePwd(String userId, String password) {
		this.checkPassword(password);
		UserJudicial user = this.userJudicialMapper.selectByPrimaryKey(userId);
		user.setSalt(PasswordUtil.buildSalt());
		user.setPassword(PasswordUtil.encrypt(password, user.getSalt()));
		user.setUpdateTime(new Date());
		if(this.userJudicialMapper.updateByPrimaryKey(user) <= 0) {
			throw JudicialRuntimeException.build("judicial.auth.reset.pwd.error.save.fail");
		}
		// 更改系统用户密码
		cn.repeatlink.module.manage.dto.UserInfo userInfo = this.sysUserService.getByUserName(user.getLoginId());
		userInfo.setPassword(password);
		this.sysUserService.updateUser(userInfo, -1L);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateName(String userId, String famname, String famnameKana, String givename, String givenameKana) {
		String fullname = StringUtils.trimToNull(StringUtils.trimToEmpty(famname) + " " + StringUtils.trimToEmpty(givename));
		String fullnameKana = StringUtils.trimToNull(StringUtils.trimToEmpty(famnameKana) + " " + StringUtils.trimToEmpty(givenameKana));
		this.checkName(fullname, fullnameKana);
		UserJudicial user = this.userJudicialMapper.selectByPrimaryKey(userId);
		user.setFamname(StringUtils.trimToNull(famname));
		user.setFamnameKana(StringUtils.trimToNull(famnameKana));
		user.setGivename(StringUtils.trimToNull(givename));
		user.setGivenameKana(StringUtils.trimToNull(givenameKana));
		user.setFullname(fullname);
		user.setFullnameKana(fullnameKana);
		user.setUpdateTime(new Date());
		if(this.userJudicialMapper.updateByPrimaryKey(user) <= 0) {
			throw JudicialRuntimeException.build("msg.db.save.fail");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void setAvatar(String userId, String base64data) {
		UserJudicial user = this.userJudicialMapper.selectByPrimaryKey(userId);
		if(base64data != null) {
			user.setAvatar(base64data.getBytes());
			user.setUpdateTime(new Date());
			this.userJudicialMapper.updateByPrimaryKey(user);
		}
	}

	@Override
	public void checkPassword(String password) {
		if(!ReUtil.isMatch("[0-9a-zA-Z]{6,16}", password)) {
			throw JudicialRuntimeException.build(BaseResultCode.NEW_PASSWORD_ERROR);
		}
	}
	
	private void checkName(String fullname, String fullnameKana) {
		if(StringUtils.isBlank(fullname)) {
			throw JudicialRuntimeException.build("judicial.user.error.fullname.invalid");
		}
	}
	
	private String convertAvatar(String base64) {
		if(base64 != null) {
			if(base64.startsWith("data:image/")) {
				int index = base64.indexOf(";base64,");
				if(index >= 0) {
					return base64.substring(index + 8);
				}
			}
		}
		return base64;
	}
}
