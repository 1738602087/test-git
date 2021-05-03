/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.module.general.mapper.GeneralOrderMapper;
import cn.repeatlink.module.usercenter.service.IGeneralOrderService;
import cn.repeatlink.module.usercenter.vo.GeneralDeductRecordVo;
import cn.repeatlink.module.usercenter.vo.GeneralOrderItemVo;
import cn.repeatlink.module.usercenter.vo.ReqGeneralOrderSearchVo;

/**
 * @author LAI
 * @date 2021-01-18 11:43
 */

@Service
public class GeneralOrderServiceImpl implements IGeneralOrderService {
	
	@Autowired
	private GeneralOrderMapper generalOrderMapper;
	
	@Override
	public List<GeneralOrderItemVo> getOrderGeneralList(ReqGeneralOrderSearchVo vo) {
		List<GeneralOrderItemVo> list = this.generalOrderMapper.getGeneralUserList(vo);
		return list;
	}
	
	@Override
	public List<GeneralDeductRecordVo> getUserDeductList(String userId) {
		List<GeneralDeductRecordVo> list = this.generalOrderMapper.getUserDeductList(userId);
		return list;
	}

}
