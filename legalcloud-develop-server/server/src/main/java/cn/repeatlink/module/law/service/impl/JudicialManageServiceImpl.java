/**
 * 
 */
package cn.repeatlink.module.law.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.extra.template.engine.enjoy.EnjoyEngine;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.LawGroupCode;
import cn.repeatlink.common.entity.UserJudicial;
import cn.repeatlink.common.mapper.LawGroupCodeMapper;
import cn.repeatlink.common.mapper.UserJudicialMapper;
import cn.repeatlink.framework.service.IMailService;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.judicial.service.IUserService;
import cn.repeatlink.module.law.common.Constant.ApplyStatus;
import cn.repeatlink.module.law.common.LawRuntimeException;
import cn.repeatlink.module.law.mapper.JudicialMapper;
import cn.repeatlink.module.law.service.IJudicialManageService;
import cn.repeatlink.module.law.util.GroupCodeUtil;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.user.JudicialUserApplyVo;
import cn.repeatlink.module.law.vo.user.JudicialUserVo;
import cn.repeatlink.module.law.vo.user.ReqJudicialUserUpdateVo;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysUserService;

/**
 * @author LAI
 * @date 2020-10-14 15:00
 */

@Service
public class JudicialManageServiceImpl implements IJudicialManageService {
	
	@Autowired
	private JudicialMapper judicialMapper;
	
	@Autowired
	private UserJudicialMapper userJudicialMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private LawGroupCodeMapper lawGroupCodeMapper;
	
	@Autowired
	private IMailService mailService;
	
	@Autowired		
	private IUserService userService;
	
	@Override
	public List<JudicialUserVo> getNormalUserList(String groupId, JudicialUserVo vo) {
		if(vo == null) {
			vo = new JudicialUserVo();
		}
		vo.setStatus(Constant.STATUS_VALID);
		return this.judicialMapper.search(groupId, vo);
	}
	
	@Override
	public List<JudicialUserVo> getInvalidUserList(String groupId, JudicialUserVo vo) {
		if(vo == null) {
			vo = new JudicialUserVo();
		}
		vo.setStatus(Constant.STATUS_INVALID);
		return this.judicialMapper.search(groupId, vo);
	}
	
	@Override
	public List<JudicialUserApplyVo> getApplyUserList(String groupId, JudicialUserApplyVo vo) {
		if(vo == null) {
			vo = new JudicialUserApplyVo();
		}
		return this.judicialMapper.searchApply(groupId, vo);
	}

	@Transactional
	@Override
	public void invalidUser(LawLoginUserInfo operaUser, String userId) {
		UserJudicial judicial = this.userJudicialMapper.selectByPrimaryKey(userId);
		if(!Constant.STATUS_VALID.equals(judicial.getStatus())) {
			return;
		}
		Date nowtime = new Date();
		judicial.setStatus(Constant.STATUS_INVALID);
		judicial.setUpdateTime(nowtime);
		judicial.setInvalidTime(nowtime);
		if(this.userJudicialMapper.updateByPrimaryKey(judicial) <= 0) {
			throw LawRuntimeException.build("manager.user.operation.fail");
		}
		
		// 同步系统用户表
		UserInfo userInfo = this.sysUserService.getByUserName(judicial.getLoginId());
		this.sysUserService.updateUserEnable(userInfo.getUserId(), Constant.DISABLED, operaUser.getUser_id());
		
	}
	
	@Transactional
	@Override
	public void updateUser(LawLoginUserInfo operaUser, String userId, ReqJudicialUserUpdateVo vo) {
		UserJudicial judicial = this.userJudicialMapper.selectByPrimaryKey(userId);
		if(!Constant.STATUS_VALID.equals(judicial.getStatus())) {
			return;
		}
		// 更新姓名
		this.userService.updateName(userId, vo.getFamname(), vo.getFamname_kana(), vo.getGivename(), vo.getGivename_kana());
		
	}
	
	@Transactional
	@Override
	public void reviveUser(LawLoginUserInfo operaUser, String userId) {
		UserJudicial judicial = this.userJudicialMapper.selectByPrimaryKey(userId);
		if(Constant.STATUS_VALID.equals(judicial.getStatus())) {
			return;
		}
		Date nowtime = new Date();
		judicial.setStatus(Constant.STATUS_VALID);
		judicial.setUpdateTime(nowtime);
		judicial.setInvalidTime(null);
		if(this.userJudicialMapper.updateByPrimaryKey(judicial) <= 0) {
			throw LawRuntimeException.build("manager.user.operation.fail");
		}
		
		// 同步系统用户表
		Long id = Db.queryLong("select user_id from sys_user where user_name=? and enable=? limit 1", judicial.getLoginId(), Constant.DISABLED);
		this.sysUserService.updateUserEnable(id, Constant.ENABLE, operaUser.getUser_id());
	}
	
	@Transactional
	@Override
	public void newUser(LawLoginUserInfo operaUser, String email) {
		if(!Validator.isEmail(email)) {
			throw LawRuntimeException.build("judicial.auth.reg.error.email.invalid");
		}
		// 检查邮箱被使用情况
		List<Record> list = Db.find("select * from law_group_code where email=? and (reg_flag=? or invalid_date>?)", email, true, new Date());
		if(list != null && !list.isEmpty()) {
			throw LawRuntimeException.build("law.account.error.new.user.email.exist");
		}
		// 2021-01-15
		// 事务所帐号申请占用
		list = Db.find("select * from law_apply where applicant_email=? and `status` in (?,?) ", email, ApplyStatus.APPLY, ApplyStatus.PASSED);
		if(list != null && !list.isEmpty()) {
			throw LawRuntimeException.build("law.account.error.new.user.email.exist");
		}
		// 2021-03-26
		// 邮箱被系统使用
		list = Db.find("select * from sys_user where email=? and `status`=? ", email, Constant.STATUS_VALID);
		if(list != null && !list.isEmpty()) {
			throw LawRuntimeException.build("law.account.error.new.user.email.exist");
		}
		
		// 新建帐号申请
		String groupId= operaUser.getGroup_id();
		String regCode = GroupCodeUtil.build();
		
		LawGroupCode entity = new LawGroupCode();
		entity.setId(IDUtil.nextID());
		entity.setGroupId(groupId);
		entity.setEmail(email);
		entity.setRegCode(regCode);
		entity.setCreatedBy(operaUser.getUser_id());
		entity.setCreateTime(new Date());
		entity.setRegFlag(false);
		// 一天后失效
		entity.setInvalidDate(DateUtil.offsetDay(entity.getCreateTime(), 1));
		
		if(this.lawGroupCodeMapper.insert(entity) <= 0) {
			throw LawRuntimeException.build("law.account.error.new.user.save.fail");
		}
		
		// 发送邮件
		this.sendRegCodeEmail(entity.getEmail(), entity.getRegCode());
	}
	
	@Override
	public void sendMailAgain(LawLoginUserInfo operaUser, String email) {
		Record record = Db.findFirst("select * from law_group_code where email=? and invalid_date>?", email, new Date());
		String regCode = record.getStr("reg_code");
		Db.update("update law_group_code set invalid_date=? where id=? ", DateUtil.offsetDay(new Date(), 1), record.get("id"));
		this.sendRegCodeEmail(email, regCode);
	}
	
	private void sendRegCodeEmail(String email, String regCode) {
		String subject = MessageUtil.getMessage("law.account.new.user.mail.subject");
		// String text = MessageUtil.getMessage("law.account.new.user.mail.text", email, regCode, DateUtil.format(invalidDate, "yyyy-MM-dd HH:mm"));
		String template = IoUtil.read(JudicialManageServiceImpl.class.getResourceAsStream("_JudicialRegCodeEmailTemplate.txt"), "UTF-8");
		template = new EnjoyEngine().getTemplate(template).render(Kv.by("email", email).set("code", regCode).set("time", "24時間"));
		try {
			this.mailService.sendMail(new String[] { email }, subject, template);
		} catch (Exception e) {
			e.printStackTrace();
			throw LawRuntimeException.build("law.account.error.new.user.send.mail.fail");
		}
	}
	
}
