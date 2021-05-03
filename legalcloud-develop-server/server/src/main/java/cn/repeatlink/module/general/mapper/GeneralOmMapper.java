/**
 * 
 */
package cn.repeatlink.module.general.mapper;

import java.util.List;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.OmCustomerCredit;
import cn.repeatlink.common.entity.OmDeductionRecord;
import cn.repeatlink.module.general.vo.payment.FeeDetailVo;
import cn.repeatlink.module.general.vo.payment.FeeItemVo;

/**
 * @author LAI
 * @date 2020-10-23 15:35
 */
public interface GeneralOmMapper {
	
	List<OmCustomerCredit> getAllCreditCardListByUserIdForUpdate(String userId);
	
	List<FeeDetailVo> findFeeHistoryPageList(String userId, PageLoadMoreVo<FeeDetailVo, FeeItemVo> vo);
	
	OmDeductionRecord getOmRecordByIdForUpdate(String recordId);

}
