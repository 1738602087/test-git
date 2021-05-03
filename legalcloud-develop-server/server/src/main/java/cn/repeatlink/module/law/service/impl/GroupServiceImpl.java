/**
 * 
 */
package cn.repeatlink.module.law.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.common.entity.LawGroup;
import cn.repeatlink.common.mapper.LawGroupMapper;
import cn.repeatlink.module.law.common.LawRuntimeException;
import cn.repeatlink.module.law.service.IGroupService;
import cn.repeatlink.module.law.vo.GroupSettingVo;

/**
 * @author LAI
 * @date 2020-10-15 13:56
 */

@Service
public class GroupServiceImpl implements IGroupService {
	
	@Autowired
	private LawGroupMapper lawGroupMapper;
	
	@Override
	public GroupSettingVo getGroupInfo(String groupId) {
		GroupSettingVo vo = new GroupSettingVo();
		LawGroup lawGroup = this.lawGroupMapper.selectByPrimaryKey(groupId);
		if(lawGroup == null) return vo;
		vo.setGroup_id(lawGroup.getGroupId());
		vo.setGroup_name(lawGroup.getGroupName());
		vo.setAddr(lawGroup.getAddr());
		vo.setEmail(lawGroup.getEmail());
		vo.setTel(lawGroup.getTel());
		vo.setRepresenter(lawGroup.getRepresenter());
		vo.setRepresenter_kana(lawGroup.getRepresenterKana());
		vo.setStaff(lawGroup.getStaff());
		if(lawGroup.getPhoto() != null) {
			vo.setPhoto(new String(lawGroup.getPhoto()));
		}
		return vo;
	}
	
	@Override
	public void saveGroupInfo(String operaUserId, String groupId, GroupSettingVo vo) {
		if(StringUtils.isBlank(vo.getGroup_name())) {
			throw LawRuntimeException.build("law.setting.group.error.grouname.null");
		}
		
		LawGroup lawGroup = this.lawGroupMapper.selectByPrimaryKey(groupId);
		lawGroup.setGroupName(vo.getGroup_name());
		lawGroup.setAddr(vo.getAddr());
		lawGroup.setEmail(vo.getEmail());
		lawGroup.setRepresenter(vo.getRepresenter());
		lawGroup.setRepresenterKana(vo.getRepresenter_kana());
		lawGroup.setStaff(vo.getStaff());
		lawGroup.setTel(vo.getTel());
		if(StringUtils.isNotBlank(vo.getPhoto())) {
			lawGroup.setPhoto(vo.getPhoto().getBytes());
		}
		if(this.lawGroupMapper.updateByPrimaryKey(lawGroup) <= 0) {
			throw LawRuntimeException.build("law.setting.group.save.fail");
		}
	}

}
