/**
 * 
 */
package cn.repeatlink.module.usercenter.service;

import java.util.List;

import cn.repeatlink.module.usercenter.vo.LawApplyDetailVo;
import cn.repeatlink.module.usercenter.vo.LawApplyItemVo;
import cn.repeatlink.module.usercenter.vo.LawGroupDetailVo;
import cn.repeatlink.module.usercenter.vo.LawGroupItemVo;
import cn.repeatlink.module.usercenter.vo.ReqLawApplySearchVo;
import cn.repeatlink.module.usercenter.vo.ReqLawGroupSearchVo;

/**
 * @author LAI
 * @date 2021-01-14 16:14
 */
public interface ILawManageService {

	/**
	 * @param vo
	 * @return
	 */
	List<LawApplyItemVo> searchApplyList(ReqLawApplySearchVo vo);

	/**
	 * @param applyId
	 * @return
	 */
	LawApplyDetailVo getApplyDetail(String applyId);

	/**
	 * @param applyId
	 */
	void sendApplyResultMail(String applyId);

	/**
	 * @param operaUserId
	 * @param applyId
	 * @param status
	 * @param reason
	 */
	void reviewApply(String operaUserId, String applyId, Short status, String reason);

	/**
	 * @param operaUserId
	 * @param applyId
	 */
	void deleteApply(String operaUserId, String applyId);

	/**
	 * @param vo
	 * @return
	 */
	List<LawGroupItemVo> searchGroupList(ReqLawGroupSearchVo vo);

	/**
	 * @param groupId
	 * @return
	 */
	LawGroupDetailVo getGroupDetail(String groupId);

	/**
	 * @param operaUserId
	 * @param groupId
	 * @param status
	 */
	void updateGroupStatus(String operaUserId, String groupId, Short status);

}
