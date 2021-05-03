/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

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
import cn.hutool.extra.template.engine.enjoy.EnjoyEngine;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.LawApply;
import cn.repeatlink.common.entity.LawGroup;
import cn.repeatlink.common.entity.LawGroupUser;
import cn.repeatlink.common.mapper.LawApplyMapper;
import cn.repeatlink.common.mapper.LawGroupMapper;
import cn.repeatlink.common.mapper.LawGroupUserMapper;
import cn.repeatlink.framework.service.IMailService;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.general.common.Define.ConfigKeys;
import cn.repeatlink.module.law.common.Constant.ApplyStatus;
import cn.repeatlink.module.law.common.LawRuntimeException;
import cn.repeatlink.module.law.mapper.LawBizMapper;
import cn.repeatlink.module.law.service.ILawBaseService;
import cn.repeatlink.module.law.util.GroupCodeUtil;
import cn.repeatlink.module.usercenter.service.ILawManageService;
import cn.repeatlink.module.usercenter.vo.LawApplyDetailVo;
import cn.repeatlink.module.usercenter.vo.LawApplyItemVo;
import cn.repeatlink.module.usercenter.vo.LawGroupDetailVo;
import cn.repeatlink.module.usercenter.vo.LawGroupItemVo;
import cn.repeatlink.module.usercenter.vo.ReqLawApplySearchVo;
import cn.repeatlink.module.usercenter.vo.ReqLawGroupSearchVo;

/**
 * 事务所帐号管理服务
 * @author LAI
 * @date 2021-01-14 16:14
 */

@Service
public class LawManageServiceImpl implements ILawManageService, ILawBaseService {
	
	@Autowired
	private LawBizMapper lawBizMapper;
	
	@Autowired
	private LawApplyMapper lawApplyMapper;
	
	@Autowired
	private LawGroupMapper lawGroupMapper;
	
	@Autowired
	private LawGroupUserMapper lawGroupUserMapper;
	
	@Autowired
	private IMailService mailService;
	
	
	@Override
	public List<LawApplyItemVo> searchApplyList(ReqLawApplySearchVo vo) {
		List<LawApplyItemVo> list = this.lawBizMapper.searchApplyList(vo);
		return list;
	}
	
	@Override
	public LawApplyDetailVo getApplyDetail(String applyId) {
		LawApplyDetailVo vo = new LawApplyDetailVo();
		LawApply apply = this.lawApplyMapper.selectByPrimaryKey(applyId);
		vo.setCorp_name(apply.getCorpName());
		vo.setCorp_rpt_name(apply.getCorpRptName());
		vo.setCorp_rpt_name_kana(apply.getCorpRptNameKana());
		vo.setCorp_addr(apply.getCorpAddr());
		vo.setCorp_phone(apply.getCorpPhone());
		vo.setApplicant_name(apply.getApplicantName());
		vo.setApplicant_name_kana(apply.getApplicantNameKana());
		vo.setApplicant_dept(apply.getApplicantDept());
		vo.setApplicant_phone(apply.getApplicantPhone());
		vo.setApplicant_email(apply.getApplicantEmail());
		vo.setStatus(apply.getStatus());
		return vo;
	}
	
	@Override
	@Transactional
	public void reviewApply(String operaUserId, String applyId, Short status, String reason) {
		LawApply apply = this.lawBizMapper.getApplyForUpdate(applyId);
		if(!ApplyStatus.APPLY.equals(apply.getStatus())) {
			bizError("law.manage.error.review.apply.status.error");
		}
		if(!ApplyStatus.PASSED.equals(status) && !ApplyStatus.REJECT.equals(status)) {
			bizError("law.manage.error.review.req.status.error");
		}
		if(ApplyStatus.REJECT.equals(status) && StringUtils.isBlank(reason)) {
			bizError("law.manage.error.review.req.reject.reason.empty");
		}
		apply.setStatus(status);
		apply.setRejectReason(reason);
		apply.setUpdateTime(new Date());
		if(ApplyStatus.REJECT.equals(status)) {
			apply.setRejectTime(apply.getUpdateTime());
			apply.setRegCode(null);
		}
		if(ApplyStatus.PASSED.equals(status)) {
			// 生成注册码
			apply.setRegCode(GroupCodeUtil.build());
		}
		apply.setRemark((StringUtils.isBlank(apply.getRemark()) ? "" : "\r\n") + DateUtil.format(apply.getUpdateTime(), "yyyy-MM-dd HH:mm:ss") 
			+ " reviewed by user(" + operaUserId + ") status=" + status + " reason=" + reason);
		// 保存
		this.lawApplyMapper.updateByPrimaryKey(apply);
		// 发送邮件
		this.sendApplyResultMail(apply.getId(), true);
	}
	
	@Transactional
	@Override
	public void deleteApply(String operaUserId, String applyId) {
		LawApply apply = this.lawBizMapper.getApplyForUpdate(applyId);
		if(!ApplyStatus.REJECT.equals(apply.getStatus())) {
			bizError("law.manage.error.review.apply.status.error");
		}
		apply.setStatus(ApplyStatus.INVALID);
		apply.setUpdateTime(new Date());
		apply.setRemark((StringUtils.isBlank(apply.getRemark()) ? "" : "\r\n") + DateUtil.format(apply.getUpdateTime(), "yyyy-MM-dd HH:mm:ss") 
		+ " deleted by user(" + operaUserId + ")");
		this.lawApplyMapper.updateByPrimaryKey(apply);
	}
	
	@Override
	public void sendApplyResultMail(String applyId) {
		this.sendApplyResultMail(applyId, false);
	}
	
	public void sendApplyResultMail(String applyId, boolean includeReject) {
		LawApply apply = this.lawApplyMapper.selectByPrimaryKey(applyId);
		if(!ApplyStatus.PASSED.equals(apply.getStatus()) && !ApplyStatus.REJECT.equals(apply.getStatus())) {
			return;
		}
		String email = apply.getApplicantEmail();
		String subject = null;
		String text = null;
		if(ApplyStatus.REJECT.equals(apply.getStatus())) {
			if(!includeReject) {
				return;
			}
			// 拒否，发送拒否理由邮件
			String reason = apply.getRejectReason();
			subject = MessageUtil.getMessage("law.manage.apply.result.mail.reject.subject");
			Kv params = Kv.by("refuse_reason", reason).set("phone_number", getContactPhoneNumber()).set("sys_name", getLegalName());
			text = IoUtil.read(LawManageServiceImpl.class.getResourceAsStream("_LawApplyRejectEmailTemplate.txt"), "UTF-8");
			text = new EnjoyEngine().getTemplate(text).render(params);
		}
		else if(ApplyStatus.PASSED.equals(apply.getStatus())) {
			// 通过，发送注册码邮件
			String regCode = apply.getRegCode();
			subject = MessageUtil.getMessage("law.manage.apply.result.mail.passed.subject");
			Kv params = Kv.by("invite_code", regCode).set("phone_number", getContactPhoneNumber()).set("sys_name", getLegalName());
			text = IoUtil.read(LawManageServiceImpl.class.getResourceAsStream("_LawApplyPassEmailTemplate.txt"), "UTF-8");
			text = new EnjoyEngine().getTemplate(text).render(params);
		}
		try {
			this.mailService.sendMail(new String[] { email }, subject, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw LawRuntimeException.build("law.account.error.new.user.send.mail.fail");
		}
	}
	
	@Override
	public List<LawGroupItemVo> searchGroupList(ReqLawGroupSearchVo vo) {
		List<LawGroupItemVo> list = this.lawBizMapper.searchGroupList(vo);
		return list;
	}
	
	@Override
	public LawGroupDetailVo getGroupDetail(String groupId) {
		LawGroupDetailVo vo = new LawGroupDetailVo();
		LawGroup group = this.lawGroupMapper.selectByPrimaryKey(groupId);
		Record groupUser = Db.findFirst("select * from law_group_user where group_id=? ", groupId);
		vo.setGroup_id(group.getGroupId());
		vo.setLogin_id(groupUser.getStr("login_id"));
		vo.setStatus(groupUser.getShort("status"));
		vo.setCorp_name(group.getGroupName());
		vo.setCorp_rpt_name(group.getRepresenter());
		vo.setCorp_rpt_name_kana(group.getRepresenterKana());
		vo.setCorp_addr(group.getAddr());
		vo.setCorp_phone(group.getTel());
		Record apply = Db.findFirst("select * from law_apply where applicant_email=? and `status`=? and reject_time is null and reg_code is not null "
				, group.getApplicantEmail(), ApplyStatus.INVALID);
		if(apply != null) {
			vo.setApplicant_name(apply.getStr("applicant_name"));
			vo.setApplicant_name_kana(apply.getStr("applicant_name_kana"));
			vo.setApplicant_dept(apply.getStr("applicant_dept"));
			vo.setApplicant_phone(apply.getStr("applicant_phone"));
			vo.setApplicant_email(apply.getStr("applicant_email"));
		}
		Record user = Db.findFirst("select * from law_group_user where group_id=? ", groupId);
		if(user != null) {
			vo.setApplicant_name(user.getStr("fullname"));
			vo.setApplicant_name_kana(user.getStr("fullname_kana"));
			vo.setApplicant_phone(user.getStr("tel"));
			vo.setApplicant_email(user.getStr("email"));
		}
		return vo;
	}
	
	@Override
	public void updateGroupStatus(String operaUserId, String groupId, Short status) {
		if(!Constant.STATUS_INVALID.equals(status) && !Constant.STATUS_VALID.equals(status)) {
			return;
		}
		LawGroup group = this.lawBizMapper.getGroupForUpdate(groupId);
		String id = Db.queryStr("select id from law_group_user where group_id=? ", group.getGroupId());
		LawGroupUser groupUser = this.lawGroupUserMapper.selectByPrimaryKey(id);
		groupUser.setStatus(status);
		groupUser.setUpdatedBy(operaUserId);
		groupUser.setUpdateTime(new Date());
		groupUser.setRemark((StringUtils.isBlank(groupUser.getRemark()) ? "" : "\r\n") + DateUtil.format(groupUser.getUpdateTime(), "yyyy-MM-dd HH:mm:ss") 
		+ " updated by user(" + operaUserId + ") status=" + status);
		
		this.lawGroupUserMapper.updateByPrimaryKey(groupUser);
	}

	public String getContactPhoneNumber() {
		return SysConfigCacheUtil.instance().getValue(ConfigKeys.SYSTEM_HELP_INFO_CONTACT_TEL, StringUtils.EMPTY);
	}
	
	public String getLegalName() {
		return SysConfigCacheUtil.instance().getValue(ConfigKeys.SYSTEM_HELP_INFO_LEGAL_NAME, StringUtils.EMPTY);
	}
}
