/**
 * 
 */
package cn.repeatlink.module.general.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;

import cn.repeatlink.common.Constant;
import cn.repeatlink.module.general.mapper.GeneralEstateMapper;
import cn.repeatlink.module.general.service.IGeneralEstateService;
import cn.repeatlink.module.general.service.IGeneralPaymentService;
import cn.repeatlink.module.general.vo.main.EstateInfo;
import cn.repeatlink.module.general.vo.main.EstateSetInfo;
import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import cn.repeatlink.module.general.vo.payment.EstateEasyVo;
import cn.repeatlink.module.judicial.service.IProtectService;

/**
 * @author LAI
 * @date 2020-10-23 10:07
 */

@Service
public class GeneralEstateServiceImpl implements IGeneralEstateService {
	
	@Autowired
	private GeneralEstateMapper generalEstateMapper;
	
	@Autowired
	private IGeneralPaymentService generalPaymentService;
	
	@Autowired
	private IProtectService protectService;

	@Override
	public List<EstateSetInfo> getUserEstateList(String userId) {
		final List<EstateSetInfo> estateSetList = new ArrayList<>();
		// 查出房产信息列表
		List<EstateInfo> estateList = this.generalEstateMapper.getEstateListByUserId(userId);
		// 查出每个房产的名义人
		if(estateList != null && estateList.size() > 0) {
			List<AutoDeductionVo> deductionList = this.generalPaymentService.getAutoDeductionInfo(userId);
			Map<String, AutoDeductionVo> deductionMap = new HashMap<>();
			if(deductionList != null) {
				for (AutoDeductionVo vo : deductionList) {
					deductionMap.put(vo.getSet_id(), vo);
				}
			}
			String setId = null;
			for (EstateInfo estateInfo : estateList) {
				if(setId == null || !StringUtils.equals(setId, estateInfo.get_set_id())) {
					EstateSetInfo set = new EstateSetInfo();
					set.setUser_list(this.generalEstateMapper.getEstateUserByEstateId(estateInfo.getEstate_id()));
					set.setDeduction(deductionMap.get(estateInfo.get_set_id()));
					set.setSet_id(estateInfo.get_set_id());
					set.setEstate_list(new ArrayList<>());
					estateSetList.add(set);
					setId = set.getSet_id();
					
					if(set.getDeduction() == null) {
						Kv order = this.protectService.getConfigOrderInfo();
						set.setDeduction(new AutoDeductionVo());
						set.getDeduction().setSet_id(set.getSet_id());
						set.getDeduction().setStatus(Constant.DISABLED);
						// 未加入时，设置默认购买价格、标题等信息
						set.getDeduction().setMoney(order.getLong("money"));
						set.getDeduction().setTitle(order.getStr("title"));
						set.getDeduction().setRemark(order.getStr("remark"));
						set.getDeduction().setEstate_list(new ArrayList<>());
					}
				}
				estateSetList.get(estateSetList.size() - 1).getEstate_list().add(estateInfo);
				List<EstateEasyVo> estate_list = estateSetList.get(estateSetList.size() - 1).getDeduction().getEstate_list();
				estate_list.add(new EstateEasyVo());
				estate_list.get(estate_list.size() - 1).setEstate_addr(StringUtils.trimToEmpty(estateInfo.getAddr()) + StringUtils.trimToEmpty(estateInfo.getAddr2()) + StringUtils.trimToEmpty(estateInfo.getHouse_id()));
			}
		}
		return estateSetList;
	}
	
}
