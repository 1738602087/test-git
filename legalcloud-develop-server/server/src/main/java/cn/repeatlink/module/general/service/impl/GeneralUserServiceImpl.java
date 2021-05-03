/**
 *
 */
package cn.repeatlink.module.general.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.lang.Validator;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.UserGeneral;
import cn.repeatlink.common.mapper.UserGeneralMapper;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.general.common.GeneralRuntimeException;
import cn.repeatlink.module.general.factory.GeneralRegValidateCodeServiceFactory;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.auth.RegVo;
import cn.repeatlink.module.general.vo.user.UserAttributeInfo;
import cn.repeatlink.module.general.vo.user.UserAvatarVo;
import cn.repeatlink.module.general.vo.user.UserInfo;
import cn.repeatlink.module.general.vo.user.UserPwdVo;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.util.PasswordUtil;

/**
 * @author LAI
 * @date 2020-10-22 16:24
 */

@Service
public class GeneralUserServiceImpl implements IGeneralUserService {

    @Autowired
    private UserGeneralMapper userGeneralMapper;

    @Override
    public void checkcode(String target, String verifyCode) {
        GeneralRegValidateCodeServiceFactory.instance().getService().validate(target, verifyCode);
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo info = new UserInfo();
        UserGeneral user = this.userGeneralMapper.selectByPrimaryKey(userId);
        info.setUser_id(userId);
        info.setAddr1(user.getAddr1());
        info.setAddr2(user.getAddr2());
        info.setAddr3(user.getAddr3());
        info.setBirthday(user.getBirthday());
        info.setFamname(user.getFamname());
        info.setFamname_kana(user.getFamnameKana());
        info.setGivename(user.getGivename());
        info.setGivename_kana(user.getGivenameKana());
        info.setFullname(user.getFullname());
        info.setFullname_kana(user.getFullnameKana());
        info.setGender(user.getGender());
        info.setPostcode(user.getPostcode());
        info.setEmail(user.getEmail());
        if (user.getAvatar() != null) {
            info.setAvatar(new String(user.getAvatar()));
        }
        return info;
    }

    @Transactional
    @Override
    public void updateUserInfo(String operUserId, String userId, UserInfo info) {
        UserGeneral user = this.userGeneralMapper.selectByPrimaryKey(userId);
        user.setFamname(StringUtils.trimToNull(info.getFamname()));
        user.setFamnameKana(StringUtils.trimToNull(info.getFamname_kana()));
        user.setGivename(StringUtils.trimToNull(info.getGivename()));
        user.setGivenameKana(StringUtils.trimToNull(info.getGivename_kana()));
        user.setFullname(StringUtils.trimToNull(StringUtils.trimToEmpty(user.getFamname()) + " " + StringUtils.trimToEmpty(user.getGivename())));
        user.setFullnameKana(StringUtils.trimToNull(StringUtils.trimToEmpty(user.getFamnameKana()) + " " + StringUtils.trimToEmpty(user.getGivenameKana())));
        user.setAddr1(info.getAddr1());
        user.setAddr2(info.getAddr2());
        user.setBirthday(info.getBirthday());
        user.setAddr3(info.getAddr3());
        user.setGender(info.getGender());
        user.setPostcode(info.getPostcode());
        user.setUpdateTime(new Date());

        if (this.userGeneralMapper.updateByPrimaryKey(user) <= 0) {
            throw GeneralRuntimeException.build("manager.user.operation.fail");
        }

    }

    @Override
    public void updateAttributeInfo(String userId, UserAttributeInfo info) {
        Record user = Db.findFirst("select user_id from user_general_attribute where user_id=?", userId);
        boolean update = true;
        if(user == null) {
        	user = new Record();
        	user.set("user_id", userId);
        	update = false;
        }
        user.set("position", info.getPosition());
        user.set("marriage", info.getMarriage());
        user.set("children", info.getChildren());
        user.set("phone_number", info.getPhone_number());
        user.set("update_time", new Date());
        if (!update) {
            if (!Db.save("user_general_attribute", user))
                throw GeneralRuntimeException.build("manager.user.saveAttributeInfo.fail");
        } else {
            if (!Db.update("user_general_attribute", "user_id", user))
                throw GeneralRuntimeException.build("manager.user.updateAttributeInfo.fail");
        }
    }

    @Override
    public UserAttributeInfo getAttributeInfo(String userId) {
        Record record = Db.findFirst("select * from user_general_attribute where user_id=?", userId);
        UserAttributeInfo user = new UserAttributeInfo();
        if (record == null) {
            user.setPosition("");
            user.setMarriage("0");
            user.setChildren("0");
            user.setPhone_number("");
        } else {
            user.setPosition(StringUtils.trimToEmpty(record.getStr("position")));
            user.setMarriage(record.getStr("marriage"));
            user.setChildren(record.getStr("children"));
            user.setPhone_number(StringUtils.trimToEmpty(record.getStr("phone_number")));
        }
        return user;
    }

    @Transactional
    @Override
    public void updateAvatar(String userId, UserAvatarVo vo) {
        if (StringUtils.isNotBlank(vo.getAvatar())) {
            UserGeneral user = this.userGeneralMapper.selectByPrimaryKey(userId);
            user.setAvatar(vo.getAvatar().getBytes());
            user.setUpdateTime(new Date());
            if (this.userGeneralMapper.updateByPrimaryKey(user) <= 0) {
                throw GeneralRuntimeException.build("manager.user.operation.fail");
            }
        }
    }

    @Transactional
    @Override
    public void updatePwd(String userId, UserPwdVo vo) {
        UserGeneral user = this.userGeneralMapper.selectByPrimaryKey(userId);
        // 校验旧密码正确性
        String str = PasswordUtil.encrypt(vo.getOld_password(), user.getSalt());
        if (!str.equals(user.getPassword())) {
        	throw new GeneralRuntimeException(BaseResultCode.OLD_PASSWORD_ERROR);
        }
        // 校验新密码合法性
        if (!cn.repeatlink.module.general.util.PasswordUtil.isValid(vo.getNew_password())) {
            throw new GeneralRuntimeException(BaseResultCode.NEW_PASSWORD_ERROR);
        }
        // 设置新密码
        user.setSalt(PasswordUtil.buildSalt());
        user.setPassword(PasswordUtil.encrypt(vo.getNew_password(), user.getSalt()));
        user.setUpdateTime(new Date());
        if (this.userGeneralMapper.updateByPrimaryKey(user) <= 0) {
            throw GeneralRuntimeException.build("judicial.auth.reset.pwd.error.save.fail");
        }
    }

    @Transactional
    @Override
    public void resetPwd(String target, String password) {
        String userId = Db.queryStr("select user_id from user_general where tel = ?", target);
        UserGeneral user = this.userGeneralMapper.selectByPrimaryKey(userId);
        // 校验新密码合法性
        if (!cn.repeatlink.module.general.util.PasswordUtil.isValid(password)) {
            throw GeneralRuntimeException.build("general.user.password.invalid");
        }
        // 设置新密码
        user.setSalt(PasswordUtil.buildSalt());
        user.setPassword(PasswordUtil.encrypt(password, user.getSalt()));
        user.setUpdateTime(new Date());
        if (this.userGeneralMapper.updateByPrimaryKey(user) <= 0) {
            throw GeneralRuntimeException.build("judicial.auth.reset.pwd.error.save.fail");
        }
    }


    @Transactional
    @Override
    public void regUser(RegVo vo) {
        // 验证验证码
        GeneralRegValidateCodeServiceFactory.instance().getService().validate(vo.getTarget(), vo.getVerify_code());
        // 校验新密码合法性
        if (!cn.repeatlink.module.general.util.PasswordUtil.isValid(vo.getPassword())) {
            throw GeneralRuntimeException.build("general.user.password.invalid");
        }
        if (!Validator.isEmail(vo.getEmail())) {
            throw JudicialRuntimeException.build("usercenter.user.auth.reg.error.email.invalid");
        }
        // 邮箱作为登录ID
        vo.setLogin_id(vo.getEmail());
        // 校验登录名
        this.checkLoginId(vo.getLogin_id(), vo.getEmail(), vo.getTarget(), null);

        // 添加记录
        UserGeneral user = new UserGeneral();
        user.setUserId(IDUtil.nextID());
        user.setUserName(StringUtils.left(vo.getLogin_id().substring(0, vo.getLogin_id().indexOf("@")), 20));
        user.setLoginId(vo.getLogin_id());
        user.setTel(vo.getTarget());
        user.setEmail(vo.getEmail());
        user.setGender(Constant.GENDER_UNKOWN);
        user.setSalt(cn.repeatlink.framework.util.PasswordUtil.buildSalt());
        user.setPassword(cn.repeatlink.framework.util.PasswordUtil.encrypt(vo.getPassword(), user.getSalt()));
        user.setStatus(Constant.STATUS_VALID);
        user.setCreateTime(new Date());
        if (this.userGeneralMapper.insert(user) <= 0) {
            throw GeneralRuntimeException.build("general.user.reg.error.save.fail");
        }
    }
    
    @Override
    public void checkRegTarget(String target) {
    	Record record = Db.findFirst("select * from user_general where login_id=? or email = ? or tel = ? ", target, target, target);
    	if(record != null) {
    		throw GeneralRuntimeException.build("general.user.reg.error.loginid.exsit");
    	}
    }
    
    @Override
    public void setOrSkipJudicialAndLawGroup(String userId) {
    	String judicialUserId = Db.queryStr("SELECT ce.user_judicial_id  FROM ( SELECT * FROM estate_user WHERE user_general_id = ? ORDER BY create_time DESC LIMIT 1 ) eu "
    			+ "LEFT JOIN case_estate_record cer ON cer.estate_id = eu.estate_id LEFT JOIN case_estate ce ON ce.case_id = cer.case_id  LIMIT 1", userId);
    	if(StringUtils.isBlank(judicialUserId)) {
    		return;
    	}
    	setOrSkipJudicialAndLawGroup(userId, judicialUserId);
    }
    
	@Override
    public void setOrSkipJudicialAndLawGroup(String userId, String judicialUserId) {
    	UserGeneral general = this.userGeneralMapper.selectByPrimaryKey(userId);
    	if(general == null) return;
    	String groupId = Db.queryStr("select group_id from user_judicial where user_id=? ", judicialUserId);
    	if(StringUtils.isBlank(groupId)) {
    		return;
    	}
    	general.setJudicialId(judicialUserId);
    	general.setLawGroupId(groupId);
    	this.userGeneralMapper.updateByPrimaryKey(general);
    }


    private void checkLoginId(String loginId, String email, String tel, String userId) {
        Record record = Db.findFirst("select * from user_general where login_id=? or email = ? or tel = ?", loginId, email, tel);
        if (record != null && (StringUtils.isBlank(userId) || !userId.equals(record.getStr("user_id")))) {
            throw GeneralRuntimeException.build("general.user.reg.error.loginid.exsit");
        }
    }
}
