/**
 * 
 */
package cn.repeatlink.module.law.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;

import cn.repeatlink.common.entity.LawGroupUser;
import cn.repeatlink.common.entity.UserJudicial;
import cn.repeatlink.common.mapper.LawGroupUserMapper;
import cn.repeatlink.common.mapper.SysUserMapper;
import cn.repeatlink.common.mapper.UserJudicialMapper;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.module.judicial.service.IAuthService;
import cn.repeatlink.module.judicial.vo.UserBaseInfoVo;
import cn.repeatlink.module.law.common.LawRuntimeException;
import cn.repeatlink.module.law.mapper.UserCenterMapper;
import cn.repeatlink.module.law.service.IGroupService;
import cn.repeatlink.module.law.service.IUserCenterService;
import cn.repeatlink.module.law.vo.GroupSettingVo;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.ReqUpdatePwdVo;
import cn.repeatlink.module.law.vo.user.LawUserInfo;
import cn.repeatlink.module.usercenter.service.IAccountService;
import cn.repeatlink.module.usercenter.vo.UpdatePwdVo;

/**
 * @author LAI
 * @date 2020-10-15 14:13
 */

@Service
public class UserCenterServiceImpl implements IUserCenterService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private UserCenterMapper userCenterMapper;
	
	@Autowired
	private IGroupService groupService;
	
	@Autowired
	private LawGroupUserMapper lawGroupUserMapper;
	
	@Autowired
	private UserJudicialMapper userJudicialMapper;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IAuthService authService;
	
	@Override
	public LawUserInfo getUserInfo(LawLoginUserInfo userInfo) {
		LawUserInfo info = new LawUserInfo();
		if(UserType.LAW.equals(userInfo.getUserType())) {
			info = this.userCenterMapper.selectGroupUserInfo(userInfo.getUser_id());
		} else if (UserType.JUDICIAL.equals(userInfo.getUserType())) {
			info = this.userCenterMapper.selectJudicialUserInfo(userInfo.getJudicial_user_id());
		}
		
		// 组织头像
		GroupSettingVo groupInfo = this.groupService.getGroupInfo(userInfo.getGroup_id());
		info.setGroup_photo(groupInfo.getPhoto());
		// 用户头像
		if(info.getAvatar() != null) {
			info.setUser_photo(new String(info.getAvatar()));
		}
		
		return info;
	}
	
	@Transactional
	@Override
	public void saveUserInfo(LawLoginUserInfo userInfo, LawUserInfo vo) {
		if(UserType.LAW.equals(userInfo.getUserType())) {
			String groupUserId = Db.queryStr("select id from law_group_user where sys_user_id=? and group_id=?", userInfo.getUser_id(), userInfo.getGroup_id());
			LawGroupUser groupUser = this.lawGroupUserMapper.selectByPrimaryKey(groupUserId);
			groupUser.setBirthday(vo.getBirthday());
			groupUser.setEmail(vo.getEmail());
			groupUser.setFullname(vo.getFullname());
			groupUser.setFullnameKana(vo.getFullname_kana());
			groupUser.setGender(vo.getGender());
			if(StringUtils.isNotBlank(vo.getLogin_id())) {
				groupUser.setLoginId(vo.getLogin_id());
			}
			groupUser.setTel(vo.getTel());
			groupUser.setSendmailFlag(Boolean.TRUE.equals(vo.getSendmail_flag()));
			groupUser.setUpdatedBy(groupUserId);
			groupUser.setUpdateTime(new Date());
			if(StringUtils.isNotBlank(vo.getUser_photo())) {
				groupUser.setAvatar(vo.getUser_photo().getBytes());
			}
			if(this.lawGroupUserMapper.updateByPrimaryKey(groupUser) <= 0) {
				throw LawRuntimeException.build("manager.user.operation.fail");
			}
		}
		else if(UserType.JUDICIAL.equals(userInfo.getUserType())) {
			UserJudicial user = this.userJudicialMapper.selectByPrimaryKey(userInfo.getJudicial_user_id());
			if(StringUtils.isNotBlank(vo.getLogin_id())) {
				user.setLoginId(vo.getLogin_id());
			}
			user.setBirthday(vo.getBirthday());
			Kv kv = this.splitName(vo.getFullname());
			user.setFamname(kv.getStr("famname"));
			user.setGivename(kv.getStr("givename"));
			user.setFullname(StringUtils.trimToEmpty(user.getFamname()) + " " + StringUtils.trimToEmpty(user.getGivename()));
			kv = this.splitName(vo.getFullname_kana());
			user.setFamnameKana(kv.getStr("famname"));
			user.setGivenameKana(kv.getStr("givename"));
			user.setFullnameKana(StringUtils.trimToEmpty(user.getFamnameKana()) + " " + StringUtils.trimToEmpty(user.getGivenameKana()));
			user.setGender(vo.getGender());
			user.setUpdateTime(new Date());
			user.setTel(vo.getTel());
			user.setSendmailFlag(Boolean.TRUE.equals(vo.getSendmail_flag()));
			if(StringUtils.isNotBlank(vo.getUser_photo())) {
				user.setAvatar(vo.getUser_photo().getBytes());
			}
			if(this.userJudicialMapper.updateByPrimaryKey(user) <= 0) {
				throw LawRuntimeException.build("manager.user.operation.fail");
			}
		}
		
		if(StringUtils.isNotBlank(vo.getLogin_id())) {
			// 同步用户表（登录名）（上面）
			this.accountService.changeUserName(userInfo.getUser_id(), vo.getLogin_id());
		}
		
	}
	
	@Transactional
	@Override
	public void savePwd(LawLoginUserInfo userInfo, ReqUpdatePwdVo vo) {
		if(UserType.SYS.equals(userInfo.getUserType()) || UserType.LAW.equals(userInfo.getUserType())) {
			UpdatePwdVo vo2 = new UpdatePwdVo();
			vo2.setOldpwd(vo.getOld_pwd());
			vo2.setNewpwd(vo.getNew_pwd());
			vo2.setNewpwdc(vo2.getNewpwd());
			this.accountService.updatePwd(userInfo.getUser_id(), vo2);
		}
		else if(UserType.JUDICIAL.equals(userInfo.getUserType())) {
			UserBaseInfoVo vo2 = new UserBaseInfoVo();
			vo2.setOld_password(vo.getOld_pwd());
			vo2.setPassword(vo.getNew_pwd());
			this.authService.resetPwd(userInfo.getJudicial_user_id(), vo2);
		}
	}
	

	private Kv splitName(String name) {
		Kv kv = Kv.create();
		String famname = name;
		String givename = null;
		if(StringUtils.isNotBlank(name)) {
			name = name.trim();
			String[] splitStrs = { " ", "　" };
			for(String splitStr : splitStrs) {
				if(name.contains(splitStr)) {
					int index = name.indexOf(splitStr);
					famname = name.substring(0, index);
					givename = name.substring(index + splitStr.length());
					break;
				}
			}
		}
		famname = StringUtils.trimToNull(famname);
		givename = StringUtils.trimToNull(givename);
		kv.set("famname", famname);
		kv.set("givename", givename);
		return kv;
	}
}
