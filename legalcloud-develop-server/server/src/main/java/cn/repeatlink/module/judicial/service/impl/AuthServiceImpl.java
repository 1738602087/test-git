/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.LawGroupCode;
import cn.repeatlink.common.entity.UserJudicial;
import cn.repeatlink.common.mapper.LawGroupCodeMapper;
import cn.repeatlink.common.mapper.UserJudicialMapper;
import cn.repeatlink.framework.common.Constant.RoleKey;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.module.judicial.common.JudicialResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IAuthService;
import cn.repeatlink.module.judicial.service.IUserService;
import cn.repeatlink.module.judicial.util.IDUtil;
import cn.repeatlink.module.judicial.util.PasswordUtil;
import cn.repeatlink.module.judicial.vo.RegGroupVo;
import cn.repeatlink.module.judicial.vo.RegVo;
import cn.repeatlink.module.judicial.vo.RevocerPwdVo;
import cn.repeatlink.module.judicial.vo.UserBaseInfoVo;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysRoleService;
import cn.repeatlink.module.manage.service.ISysUserService;
import cn.repeatlink.module.usercenter.service.IValidateCodeService;

/**
 * @author LAI
 * @date 2020-09-17 09:57
 */

@Service
@Transactional
public class AuthServiceImpl implements IAuthService {
	
	@Autowired
	private LawGroupCodeMapper lawGroupCodeMapper;
	
	@Autowired
	private UserJudicialMapper userJudicialMapper;
	
	@Autowired
	private IUserService userService;
	
	@Resource(name = "emailValidateCodeService")
	private IValidateCodeService validateCodeService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Override
	public RegGroupVo getRegCodeInfo(String regCode, String email) {
		RegGroupVo vo = new RegGroupVo();
		Record r = Db.findFirst("select g.addr,g.group_name,c.invalid_date,c.email from law_group_code c, law_group g where c.group_id = g.group_id and g.`status`=? and c.reg_code=? and c.email = ?"
				, Constant.STATUS_VALID, regCode, email);
		Date invalidDate = null;
		if(r != null) {
			invalidDate = r.getDate("invalid_date");
		}
		if(invalidDate == null) {
			// 邀请码无效
			throw new JudicialRuntimeException(JudicialResultCode.AUTH_REG_CODE_INVALID);
		}
		if(!r.getStr("email").equals(email)) {
			throw JudicialRuntimeException.build("judicial.auth.reg_code.email.error");
		}
		vo.setAddr(r.getStr("addr"));
		vo.setEmail(r.getStr("email"));
		vo.setGroup_name(r.getStr("group_name"));
		return vo;
	}
	
	@Override
	public void regJudicial(RegVo vo) {
		RegGroupVo groupVo = this.getRegCodeInfo(vo.getReg_code(), vo.getEmail());
		if(groupVo == null || StringUtils.isBlank(groupVo.getEmail())) {
			
		}
		// 检查密码合法性
		this.checkPassword(vo.getPassword());
		// 检查邮箱注册情况
		this.checkEmail(groupVo.getEmail());
		// 邮箱作为登录ID
		vo.setLogin_id(groupVo.getEmail());
		// 检查登录ID存在情况
		this.checkLoginid(vo.getLogin_id());
		//
		String groupId = Db.queryStr("select group_id from law_group_code where reg_code=?", vo.getReg_code());
		if(StringUtils.isBlank(groupId)) {
			// 异常
			throw new JudicialRuntimeException(JudicialResultCode.AUTH_REG_GROUP_NOT_FOUND);
		}
		UserJudicial judicial = new UserJudicial();
		judicial.setRegCode(vo.getReg_code());
		judicial.setGroupId(groupId);
		judicial.setLoginId(vo.getLogin_id());
		judicial.setCreateTime(new Date());
		judicial.setBirthday(new Date());
		judicial.setEmail(groupVo.getEmail());
		judicial.setGender(Short.valueOf("2"));
		// 随机用户名
		judicial.setUserName(IdUtil.fastSimpleUUID().substring(5, 15));
		judicial.setStatus(Constant.STATUS_VALID);
		// 唯一ID
		judicial.setUserId(IDUtil.nextID());
		// 密码加密
		judicial.setSalt(PasswordUtil.buildSalt());
		judicial.setPassword(PasswordUtil.encrypt(vo.getPassword(), judicial.getSalt()));
		
		// 注册系统用户（主要用于登录）
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(judicial.getEmail());
		userInfo.setName(userInfo.getEmail());
		userInfo.setSex(judicial.getGender().toString());
		userInfo.setUserName(judicial.getLoginId());
		userInfo.setPassword(vo.getPassword().trim());
		userInfo.setConfirmPassword(userInfo.getPassword());
		// 注册账号类型为司法书士
		userInfo.setUserType(UserType.JUDICIAL.typeValue());
		userInfo.setRoleId(this.sysRoleService.getFirstRoleIdByRoleKey(RoleKey.judicial));
		UserInfo user = this.sysUserService.addUser(userInfo, -1L);
		
		judicial.setSysUserId(user.getUserId());
		int n = this.userJudicialMapper.insert(judicial);
		if(n <= 0) {
			// 注册失败
			throw JudicialRuntimeException.build("judicial.auth.reg.error.save.fail");
		}
		
		
		// 更新注册码，标记已被注册使用
		String regId = Db.queryStr("select id from law_group_code where reg_code=? and email=? ", vo.getReg_code(), vo.getEmail());
		LawGroupCode lawGroupCode = this.lawGroupCodeMapper.selectByPrimaryKey(regId);
		lawGroupCode.setRegFlag(true);
		lawGroupCode.setUpdatedBy(user.getUserId());
		lawGroupCode.setUpdateTime(new Date());
		this.lawGroupCodeMapper.updateByPrimaryKey(lawGroupCode);
	}
	
	@Override
	public void resetPwd(String operaUserId, RevocerPwdVo vo) {
		final String email = vo.getEmail();
		final String password = vo.getPassword();
		final String verifyCode = vo.getVerify_code();
		this.checkPassword(password);
		// 检查邮箱注册
		Record record = Db.findFirst("select * from user_judicial where email=?", email);
		if(record == null) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.email.not.found");
		}
		// 检查验证码
		this.validateCodeService.validate(email, verifyCode);
		
		final String userId= record.getStr("user_id");
		this.userService.updatePwd(userId, password);
		
	}
	
	@Override
	public void resetPwd(String userId, UserBaseInfoVo vo) {
		if(StringUtils.isBlank(vo.getOld_password())) {
			throw JudicialRuntimeException.build(JudicialResultCode.OLD_PASSWORD_ERROR);
		}
		UserJudicial judicial = this.userJudicialMapper.selectByPrimaryKey(userId);
		if(!PasswordUtil.encrypt(vo.getOld_password(), judicial.getSalt()).equals(judicial.getPassword())) {
			throw JudicialRuntimeException.build(JudicialResultCode.OLD_PASSWORD_ERROR);
		}
		String password = vo.getPassword();
		this.checkPassword(password);
		this.userService.updatePwd(userId, password);
	}
	
	private void checkPassword(String password) {
		this.userService.checkPassword(password);
	}
	
	private void checkEmail(String email) {
		if(!Validator.isEmail(email)) {
			throw JudicialRuntimeException.build("usercenter.user.auth.reg.error.email.invalid");
		}
		Record record = Db.findFirst("select * from user_judicial where email=?", email);
		if(record != null) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.email.exsit");
		}
	}
	
	private void checkLoginid(String loginId) {
		Record record = Db.findFirst("select * from user_judicial where login_id=?", loginId);
		if(record != null) {
			throw JudicialRuntimeException.build("judicial.auth.reg.error.loginid.exsit");
		}
	}
	
	

}
