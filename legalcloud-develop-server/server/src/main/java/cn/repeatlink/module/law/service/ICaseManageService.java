/**
 * 
 */
package cn.repeatlink.module.law.service;

import java.util.List;

import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.cases.CaseInfoVo;
import cn.repeatlink.module.law.vo.cases.CaseItemVo;
import cn.repeatlink.module.law.vo.cases.ReqCaseSearchVo;

/**
 * @author LAI
 * @date 2020-10-13 14:06
 */
public interface ICaseManageService {

	/**
	 * @param userInfo
	 * @param caseName
	 * @return
	 */
	List<CaseItemVo> getProcessCaseList(LawLoginUserInfo userInfo, ReqCaseSearchVo vo);

	/**
	 * @param userInfo
	 * @param caseName
	 * @return
	 */
	List<CaseItemVo> getCompletedCaseList(LawLoginUserInfo userInfo, ReqCaseSearchVo vo);

	/**
	 * @param caseId
	 * @return
	 */
	CaseInfoVo getCaseDetail(String caseId);

	/**
	 * @param operaUserId
	 * @param caseId
	 */
	void doneCase(String operaUserId, String caseId);

}
