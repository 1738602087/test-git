/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import cn.repeatlink.module.law.vo.user.LawUserInfo;

/**
 * @author LAI
 * @date 2020-10-15 14:29
 */
public interface UserCenterMapper {
	
	LawUserInfo selectGroupUserInfo(Long userId);

	LawUserInfo selectJudicialUserInfo(String judicialId);
	
}
