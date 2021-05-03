/**
 * 
 */
package cn.repeatlink.module.law.service;

import cn.repeatlink.module.law.vo.GroupSettingVo;

/**
 * @author LAI
 * @date 2020-10-15 13:55
 */
public interface IGroupService {

	/**
	 * @param groupId
	 * @return
	 */
	GroupSettingVo getGroupInfo(String groupId);

	/**
	 * @param operaUserId
	 * @param groupId
	 * @param vo
	 */
	void saveGroupInfo(String operaUserId, String groupId, GroupSettingVo vo);

}
