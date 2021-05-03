/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.List;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.CaseEstate;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;
import cn.repeatlink.module.judicial.vo.estatecase.CaseSearchVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBaseVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBuyerVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseDetailVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseSellerVo;
import cn.repeatlink.module.judicial.vo.user.BuyerUserInfo;

/**
 * @author LAI
 * @date 2020-09-21 13:33
 */
public interface IEstateCaseService {

	/**
	 * @param vo
	 * @return
	 */
	PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> getCaseList(String groupId, String userId, 
			PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> vo);

	/**
	 * @param vo
	 * @return
	 */
	String createNewCase(String operaUserId, EstatecaseBaseVo vo);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param vo
	 */
	void addCaseBuyer(String operaUserId, String caseId, EstatecaseBuyerVo vo);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param userId
	 */
	void removeCaseBuyer(String operaUserId, String caseId, String userId);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param estateId
	 * @param faceCode
	 * @return
	 */
	EstateUserInfo chooseEstate(String operaUserId, String caseId, String estateId, String faceCode);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param vo
	 */
	void verifyCaseSeller(String operaUserId, String caseId, EstatecaseSellerVo vo);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param vo
	 */
	void inputNewEstate(String operaUserId, String caseId, EstateBaseVo vo);

	/**
	 * @param caseId
	 * @return
	 */
	EstatecaseDetailVo getCaseDetail(String userId, String caseId);

	/**
	 * @param userId
	 * @return
	 */
	BuyerUserInfo getBuyerUserInfo(String userId);

	/**
	 * @param operaUserId
	 * @param caseId
	 * @param userId
	 * @param vo
	 */
	void updateCaseBuyer(String operaUserId, String caseId, String userId, EstatecaseBuyerVo vo);

	/**
	 * @param operaUserId
	 * @param caseId
	 */
	void stepDealFinished(String operaUserId, String caseId);

	/**
	 * @param operaUserId
	 * @param caseId
	 */
	void stepRegFinished(String operaUserId, String caseId);

	/**
	 * @param operaUserId
	 * @param caseId
	 */
	void stepBuyerInput(String operaUserId, String caseId);

	/**
	 * @param caseId
	 * @return
	 */
	List<EstatecaseBuyerVo> getCaseBuyersInfo(String caseId);

	/**
	 * @param caseId
	 * @return
	 */
	List<EstateBaseVo> getCaseEstateRecordInfo(String caseId);

	/**
	 * @param caseId
	 * @param status
	 * @return
	 */
	CaseEstate checkCase(String caseId, Short status);

	/**
	 * @param operaUserId
	 * @param vo
	 * @return
	 */
	String updateCase(String operaUserId, EstatecaseBaseVo vo);

	/**
	 * @param operUserId
	 * @param caseId
	 */
	void invalidCase(String operUserId, String caseId);

	/**
	 * @param operUserId
	 * @param caseId
	 * @param estates
	 */
	void updateCaseEstates(String operUserId, String caseId, List<EstateBaseVo> estates);

}
