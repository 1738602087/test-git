/**
 * 
 */
package cn.repeatlink.module.law.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.module.judicial.common.Constant.CaseOperaAuth;
import cn.repeatlink.module.judicial.service.IEstateCaseService;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBuyerVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseDetailVo;
import cn.repeatlink.module.law.mapper.LawCaseMapper;
import cn.repeatlink.module.law.service.ICaseManageService;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.cases.CaseBuyerInfo;
import cn.repeatlink.module.law.vo.cases.CaseEstateInfo;
import cn.repeatlink.module.law.vo.cases.CaseInfoVo;
import cn.repeatlink.module.law.vo.cases.CaseItemVo;
import cn.repeatlink.module.law.vo.cases.ReqCaseSearchVo;

/**
 * @author LAI
 * @date 2020-10-13 14:06
 */

@Service
public class CaseManageServiceImpl implements ICaseManageService {
	
	@Autowired
	private LawCaseMapper lawCaseMapper;
	
//	@Autowired
//	private CaseMapper caseMapper;
//	
//	@Autowired
//	private CaseEstateMapper caseEstateMapper;
	
	@Autowired
	private IEstateCaseService estateCaseService;
	
	@Override
	public List<CaseItemVo> getProcessCaseList(LawLoginUserInfo userInfo, ReqCaseSearchVo vo) {
		List<CaseItemVo> list = this.lawCaseMapper.search(userInfo.getGroup_id(), userInfo.getJudicial_user_id(), false, vo);
		fillItemInfo(list, userInfo.getJudicial_user_id());
		return list;
	}
	
	@Override
	public List<CaseItemVo> getCompletedCaseList(LawLoginUserInfo userInfo, ReqCaseSearchVo vo) {
		List<CaseItemVo> list = this.lawCaseMapper.search(userInfo.getGroup_id(), userInfo.getJudicial_user_id(), true, vo);
		fillItemInfo(list, userInfo.getJudicial_user_id());
		return list;
	}
	
	@Override
	public CaseInfoVo getCaseDetail(String caseId) {
		CaseInfoVo vo = new CaseInfoVo();
		EstatecaseDetailVo detail = this.estateCaseService.getCaseDetail(null, caseId);
		// 复制基本信息
		vo.setCase_id(caseId);
		vo.setCase_name(detail.getCase_name());
		vo.setStart_date(detail.getStart_date());
		vo.setStatus(detail.getStatus());
		vo.setStaff(detail.getAssigned_to());
		vo.setStep_seller_verify(detail.getStep_seller_verify());
		vo.setStep_buyer_input(detail.getStep_buyer_input());
		vo.setStep_deal_finish(detail.getStep_deal_finish());
		vo.setStep_reg_finish(detail.getStep_reg_finish());
		vo.setFetch_api(detail.getFetch_api());
		
		// vo.setStaff(this.getStaffFullname(detail.getAssigned_to()));
		
		// 复制买主情报
		vo.setBuyers(new ArrayList<>());
		List<EstatecaseBuyerVo> buyers = detail.getBuyers();
		if(buyers != null) {
			for (EstatecaseBuyerVo buyer : buyers) {
				CaseBuyerInfo info = new CaseBuyerInfo();
				info.setFullname(buyer.getFullname());
				info.setFullname_kana(buyer.getFullname_kana());
				info.setAddr(StringUtils.trimToEmpty(buyer.getAddr1()) + StringUtils.trimToEmpty(buyer.getAddr2()) + StringUtils.trimToEmpty(buyer.getAddr3()));
				info.setBirthday(buyer.getBirthday());
				info.setGender(buyer.getGender());
				info.setUser_id(buyer.getUser_id());
				vo.getBuyers().add(info);
			}
		}
		
		// 复制不动产信息
		List<EstateBaseVo> estateInfoList = detail.getEstate_list();
//		if(estateInfoList == null) {
//			estateInfoList = detail.getBefore_estate_list();
//		}
		if(estateInfoList != null && !estateInfoList.isEmpty()) {
			vo.setEstate_list(new ArrayList<>());
			for (EstateBaseVo estateInfo : estateInfoList) {
				CaseEstateInfo info = new CaseEstateInfo();
				info.setHouse_id(estateInfo.getHouse_id());
				info.setAddr(estateInfo.getAddr());
				info.setAddr1(estateInfo.getAddr1());
				info.setAddr_code(estateInfo.getAddr_code());
				// 2021-03-02
				// 添加字段所在地2
				info.setAddr2(estateInfo.getAddr2());
				info.setArea(estateInfo.getArea());
				info.setStruct(estateInfo.getStruct());
				info.setType(estateInfo.getType());
				info.setRemark(estateInfo.getRecord());
				info.setCategory(estateInfo.getCategory());
				info.setEstate_no(estateInfo.getEstate_no());
				info.setHas_pdf(estateInfo.getHas_pdf());
				info.setEstate_id(estateInfo.getEstate_id());
				vo.getEstate_list().add(info);
			}
		}
		
		
		return vo;
	}
	
	@Override
	public void doneCase(String operaUserId, String caseId) {
//		CaseEstate caseEstate = this.caseMapper.selectCaseEstateForUpdate(caseId);
//		if(!CaseStepVal.COMPLETED.equals(caseEstate.getStepRegFinish())) {
//			throw LawRuntimeException.build("judicial.case.step.error.not.regfinish");
//		}
//		caseEstate.setStatus(CaseStatus.COMPLETED);
//		caseEstate.setUpdatedBy(operaUserId);
//		caseEstate.setUpdatedTime(new Date());
//		int n = this.caseEstateMapper.updateByPrimaryKey(caseEstate);
//		if(n <= 0) {
//			throw LawRuntimeException.build("manager.user.operation.fail");
//		}
	}

	private void fillItemInfo(List<CaseItemVo> voList, String userId) {
		/*这个方法就是主要用来进行判断集合List<CaseItemVo>是否为空，如果不为空，我们就进行遍历该集合*/
		if(voList != null) {
			//使用增强for循环进行遍历，依次得到集合中的每一个元素即CaseItemVo对象
			for (CaseItemVo vo : voList) {
				if(vo != null) {
					//如果对象不为空,我们就去判断CaseItemVo对象的买主是否为空,如果不为空,我们就讲数据库
					// 中存放的字符串内容修改为数组,
					if(StringUtils.isNotBlank(vo.get_buyer_names())) {
						vo.setBuyer_names(Arrays.asList(vo.get_buyer_names().split(",")));
						vo.set_buyer_names(null);
					}
					if(StringUtils.isNotBlank(vo.get_estate_names())) {
						vo.setEstate_names(Arrays.asList(vo.get_estate_names().split(",")));
						vo.set_estate_names(null);
					}
					if(StringUtils.isNotBlank(vo.get_estate_code_names())) {
						vo.setEstate_code_names(Arrays.asList(vo.get_estate_code_names().split(",")));
						vo.set_estate_code_names(null);
					}
					if (StringUtils.equals(vo.getUser_judicial_id(), userId)) {
                        vo.setOperate_auth(CaseOperaAuth.UPDATEABLE);
                    } else {
                        vo.setOperate_auth(CaseOperaAuth.NO_UPDATE);
                    }
                    vo.setUser_judicial_id(null);
				}
			}
		}
	}
	
//	private String getStaffFullname(String userId) {
//		return Db.queryStr("select fullname from user_judicial where user_id = ?", userId);
//	}

}
