/**
 * 
 */
package cn.repeatlink.module.usercenter.service;

import java.util.List;

import cn.repeatlink.module.usercenter.vo.GeneralDeductRecordVo;
import cn.repeatlink.module.usercenter.vo.GeneralOrderItemVo;
import cn.repeatlink.module.usercenter.vo.ReqGeneralOrderSearchVo;

/**
 * @author LAI
 * @date 2021-01-18 11:43
 */
public interface IGeneralOrderService {

	/**
	 * @param vo
	 * @return
	 */
	List<GeneralOrderItemVo> getOrderGeneralList(ReqGeneralOrderSearchVo vo);

	/**
	 * @param userId
	 * @return
	 */
	List<GeneralDeductRecordVo> getUserDeductList(String userId);

}
