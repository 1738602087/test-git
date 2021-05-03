/**
 * 
 */
package cn.repeatlink.module.judicial.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.CaseBuyer;
import cn.repeatlink.common.entity.CaseEstate;
import cn.repeatlink.common.entity.CaseEstateRecord;
import cn.repeatlink.common.entity.CaseSeller;
import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.common.entity.EstateUser;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBaseVo;

/**
 * @author LAI
 * @date 2020-09-17 10:22
 */

@Mapper
public interface CaseMapper {

	Long test();
	
	List<EstatecaseBaseVo> search(String groupId, String userId, PageLoadMoreVo pageVo);
	
	CaseEstate selectCaseEstateForUpdate(String caseId);
	
	List<CaseBuyer> getBuyers(String caseId);
	
	EstateInfo selectEstateForUpdate(String estateId);
	
	List<EstateUser> selectEstateUserList(String estateId);
	
	List<CaseSeller> getSellers(String caseId);
	
	List<CaseEstateRecord> getCaseEstateRecordList(String caseId, Short type, Short status);
	
	void deleteCaseEstateRecordByCaseId(String caseId);
	void deleteCaseEstateRecordUserByCaseId(String caseId);
	void deleteCaseSellerByCaseId(String caseId);
	
	List<CaseEstateRecord> getCaseEstateRecordByInfo(String type, String addr, String addr2, String houseId, Short status);
	
}
