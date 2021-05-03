/**
 * 
 */
package cn.repeatlink.module.general.mapper;

import java.util.List;

import cn.repeatlink.module.usercenter.vo.GeneralDeductRecordVo;
import cn.repeatlink.module.usercenter.vo.GeneralOrderItemVo;
import cn.repeatlink.module.usercenter.vo.ReqGeneralOrderSearchVo;

/**
 * 
 * @author LAI
 * @date 2021-01-18 11:45
 */
public interface GeneralOrderMapper {
	
	List<GeneralOrderItemVo> getGeneralUserList(ReqGeneralOrderSearchVo vo);

	List<GeneralDeductRecordVo> getUserDeductList(String userId);
	
}
