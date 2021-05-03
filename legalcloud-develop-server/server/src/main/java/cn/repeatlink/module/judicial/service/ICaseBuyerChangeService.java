/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.List;

import cn.repeatlink.common.entity.CaseBuyer;
import cn.repeatlink.module.judicial.vo.estatecase.BuyerInfoChangeItemVo;

/**
 * @author LAI
 * @date 2020-09-27 15:37
 */
public interface ICaseBuyerChangeService {

	/**
	 * @param operaUserId
	 * @param oldBuyer
	 * @param newBuyer
	 */
	void saveChangeRecord(String operaUserId, CaseBuyer oldBuyer, CaseBuyer newBuyer);

	/**
	 * @param caseId
	 * @param buyerId
	 * @return
	 */
	List<BuyerInfoChangeItemVo> getChangeRecordList(String caseId, String buyerId);

}
