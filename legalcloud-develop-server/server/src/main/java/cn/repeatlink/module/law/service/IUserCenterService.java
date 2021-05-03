/**
 * 
 */
package cn.repeatlink.module.law.service;

import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.ReqUpdatePwdVo;
import cn.repeatlink.module.law.vo.user.LawUserInfo;

/**
 * @author LAI
 * @date 2020-10-15 14:13
 */
public interface IUserCenterService {

	/**
	 * @param userInfo
	 * @return
	 */
	LawUserInfo getUserInfo(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @param vo
	 */
	void saveUserInfo(LawLoginUserInfo userInfo, LawUserInfo vo);

	/**
	 * @param userInfo
	 * @param vo
	 */
	void savePwd(LawLoginUserInfo userInfo, ReqUpdatePwdVo vo);

}
