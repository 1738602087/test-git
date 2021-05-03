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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.lang.Validator;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.LawApply;
import cn.repeatlink.common.entity.LawGroup;
import cn.repeatlink.common.entity.LawGroupUser;
import cn.repeatlink.common.mapper.LawApplyMapper;
import cn.repeatlink.common.mapper.LawGroupMapper;
import cn.repeatlink.common.mapper.LawGroupUserMapper;
import cn.repeatlink.framework.common.Constant.RoleKey;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.law.common.Constant.ApplyStatus;
import cn.repeatlink.module.law.mapper.LawBizMapper;
import cn.repeatlink.module.law.service.ILawBaseService;
import cn.repeatlink.module.law.service.ILawRegService;
import cn.repeatlink.module.law.vo.auth.LawApplyVo;
import cn.repeatlink.module.law.vo.auth.LawRegVo;
import cn.repeatlink.module.manage.dto.UserInfo;
import cn.repeatlink.module.manage.service.ISysRoleService;
import cn.repeatlink.module.manage.service.ISysUserService;

/**
 * 事务所注册服务
 * @author LAI
 * @date 2021-01-14 13:35
 */

@Service
public class LawRegServiceImpl implements ILawRegService, ILawBaseService {
	
	@Autowired
	private LawApplyMapper lawApplyMapper;
	
	@Autowired
	private LawGroupMapper lawGroupMapper;
	
	@Autowired
	private LawGroupUserMapper lawGroupUserMapper;
	
	@Autowired
	private LawBizMapper lawBizMapper;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Transactional
	@Override
	public void apply(LawApplyVo vo) {
		if(StringUtils.isAnyBlank(vo.getCorp_name(), vo.getCorp_rpt_name(), vo.getCorp_rpt_name_kana(), vo.getCorp_addr(), vo.getCorp_phone()
				, vo.getApplicant_name(), vo.getApplicant_name_kana(), vo.getApplicant_dept(), vo.getApplicant_phone(), vo.getApplicant_email())) {
			bizError("law.reg.error.apply.info.error");
		}
		this.checkEmail(vo.getApplicant_email());
		LawApply apply = new LawApply();
		apply.setCorpName(vo.getCorp_name());
		apply.setCorpRptName(vo.getCorp_rpt_name());
		apply.setCorpRptNameKana(vo.getCorp_rpt_name_kana());
		apply.setCorpAddr(vo.getCorp_addr());
		apply.setCorpPhone(vo.getCorp_phone());
		apply.setApplicantName(vo.getApplicant_name());
		apply.setApplicantNameKana(vo.getApplicant_name_kana());
		apply.setApplicantDept(vo.getApplicant_dept());
		apply.setApplicantPhone(vo.getApplicant_phone());
		apply.setApplicantEmail(vo.getApplicant_email());
		apply.setId(IDUtil.nextID());
		apply.setStatus(ApplyStatus.APPLY);
		apply.setCreateTime(new Date());
		this.lawApplyMapper.insert(apply);
	}
	
	@Override
	public void applyCheck(LawApplyVo vo) {
		if(StringUtils.isAnyBlank(vo.getCorp_name(), vo.getCorp_rpt_name(), vo.getCorp_rpt_name_kana(), vo.getCorp_addr(), vo.getCorp_phone()
				, vo.getApplicant_name(), vo.getApplicant_name_kana(), vo.getApplicant_dept(), vo.getApplicant_phone(), vo.getApplicant_email())) {
			bizError("law.reg.error.apply.info.error");
		}
		this.checkEmail(vo.getApplicant_email());
	}
	
	@Override
	public void checkRegCode(String email, String regCode) {
		String regCode2 = Db.queryStr("select reg_code from law_apply where applicant_email=? and reg_code is not null and `status`=? order by create_time desc limit 1 ", email, ApplyStatus.PASSED);
		if(StringUtils.isBlank(regCode2) || !StringUtils.equals(regCode2, regCode)) {
			bizError("law.reg.error.reg.code.error");
		}
	}
	
	@Transactional
	@Override
	public void regGroup(LawRegVo vo) {
		checkRegCode(vo.getEmail(), vo.getReg_code());
		
		String applyId = Db.queryStr("select id from law_apply where applicant_email=? and reg_code is not null and `status`=? order by create_time desc limit 1 ", vo.getEmail(), ApplyStatus.PASSED);
		LawApply apply = this.lawBizMapper.getApplyForUpdate(applyId);
		
		// 事务所信息
		LawGroup group = new LawGroup();
		group.setGroupId(IDUtil.nextID());
		group.setAddr(apply.getCorpAddr());
		group.setApplicantEmail(apply.getApplicantEmail());
		group.setEmail(group.getApplicantEmail());
		group.setGroupName(apply.getCorpName());
		group.setRepresenter(apply.getCorpRptName());
		group.setStaff(apply.getApplicantName());
		group.setTel(apply.getCorpPhone());
		group.setCreateTime(new Date());
		group.setStatus(Constant.STATUS_VALID);
		
		// 事务所担当者信息
		LawGroupUser groupUser = new LawGroupUser();
		groupUser.setId(IDUtil.nextID());
		groupUser.setEmail(apply.getApplicantEmail());
		groupUser.setFullname(apply.getApplicantName());
		groupUser.setFullnameKana(apply.getApplicantNameKana());
		groupUser.setGender(Short.valueOf("2"));
		groupUser.setGroupId(group.getGroupId());
		groupUser.setLoginId(vo.getLogin_id());
		groupUser.setTel(apply.getApplicantPhone());
		groupUser.setSendmailFlag(true);
		groupUser.setCreatedBy(groupUser.getId());
		groupUser.setCreateTime(new Date());
		groupUser.setStatus(Constant.STATUS_VALID);
		
		// 注册系统用户（主要用于登录）
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(group.getEmail());
		userInfo.setName(userInfo.getEmail());
		userInfo.setSex(groupUser.getGender() + "");
		userInfo.setUserName(groupUser.getLoginId());
		userInfo.setPassword(vo.getPassword().trim());
		userInfo.setConfirmPassword(userInfo.getPassword());
		// 注册账号类型为事务所
		userInfo.setUserType(UserType.LAW.typeValue());
		userInfo.setRoleId(this.sysRoleService.getFirstRoleIdByRoleKey(RoleKey.law));
		UserInfo user = this.sysUserService.addUser(userInfo, -1L);
		
		// 关联系统用户ID
		groupUser.setSysUserId(user.getUserId());
		// 保存数据
		this.lawGroupUserMapper.insert(groupUser);
		this.lawGroupMapper.insert(group);
		apply.setStatus(ApplyStatus.INVALID);
		this.lawApplyMapper.updateByPrimaryKey(apply);
		
	}
	
	private void checkEmail(String email) {
		// 邮箱格式合法性
		if(StringUtils.isBlank(email) || !Validator.isEmail(email)) {
			bizError("law.reg.error.apply.email.error");
		}
		// 申请记录重复
		List<Record> list = Db.find("select id from law_apply where applicant_email=? and `status`<>?", email, ApplyStatus.REJECT);
		if(list != null && list.size() > 0) {
			bizError("law.reg.error.apply.email.repeat.error");
		}
		// 已有帐户重复
		this.sysUserService.checkEmailRepeat(new UserInfo().setEmail(email));
		// 司法书士邀请码重复
		list = Db.find("select * from law_group_code where email=? and reg_flag=? and invalid_date>? ", email, false, new Date());
		if(list != null && list.size() > 0) {
			bizError("law.reg.error.apply.email.repeat.error");
		}
	}

}
