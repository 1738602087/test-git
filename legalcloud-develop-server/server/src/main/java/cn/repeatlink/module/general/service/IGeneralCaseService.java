/**
 * 
 */
package cn.repeatlink.module.general.service;

import java.util.List;

import cn.repeatlink.module.general.vo.main.CaseInfo;

/**
 * @author LAI
 * @date 2020-10-23 10:04
 */
public interface IGeneralCaseService {

	/**
	 * @param userId
	 * @return
	 */
	List<CaseInfo> getProcessCaseList(String userId);

	/**
	 * @param userId
	 * @param caseId
	 */
	void confirm(String userId, String caseId);

	/**
	 * @param userId
	 * @param notConfirmCaseList
	 * @return
	 */
	Integer getUserCaseStatus(String userId, List<CaseInfo> notConfirmCaseList);

}
