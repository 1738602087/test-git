/**
 * 
 */
package cn.repeatlink.module.general.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.OmCustomerCredit;
import cn.repeatlink.common.entity.OmDeductionRecord;
import cn.repeatlink.common.mapper.OmCustomerCreditMapper;
import cn.repeatlink.common.mapper.OmDeductionRecordMapper;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.general.common.GeneralRuntimeException;
import cn.repeatlink.module.general.mapper.GeneralOmMapper;
import cn.repeatlink.module.general.service.IGeneralPaymentService;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.payment.AutoDeductionVo;
import cn.repeatlink.module.general.vo.payment.CreditCardTokenVo;
import cn.repeatlink.module.general.vo.payment.CreditCardVo;
import cn.repeatlink.module.general.vo.payment.FeeDetailVo;
import cn.repeatlink.module.general.vo.payment.FeeItemVo;
import cn.repeatlink.module.general.vo.user.UserInfo;
import cn.repeatlink.module.judicial.service.IOmiseService;
import cn.repeatlink.module.judicial.service.IProtectService;

/**
 * @author LAI
 * @date 2020-10-23 15:17
 */

@Service
public class GeneralPaymentServiceImpl implements IGeneralPaymentService {
	
	@Autowired
	private OmCustomerCreditMapper omCustomerCreditMapper;
	
	@Autowired
	private OmDeductionRecordMapper omDeductionRecordMapper;
	
	@Autowired
	private GeneralOmMapper generalOmMapper;
	
	@Autowired
	private IOmiseService omiseService;
	
	@Autowired
	private IGeneralUserService generalUserService;
	
	@Autowired
	private IProtectService protectService;
	
	@Override
	public List<CreditCardVo> getCreditCardListByUserId(String userId) {
		List<CreditCardVo> voList = new ArrayList<>();
		List<Record> list = Db.find("select * from om_customer_credit where customer_id=? and `status`=?", userId, Constant.STATUS_VALID);
		if(list != null) {
			for (Record record : list) {
				CreditCardVo vo = new CreditCardVo();
				vo.setId(record.getStr("id"));
				vo.setCard_no(record.getStr("card_no"));
				vo.setCompany_name(record.getStr("company_name"));
				vo.setValid_date(record.getStr("valid_date"));
				vo.setSelected(Boolean.TRUE.equals(record.getBoolean("is_selected")));
				vo.setHolder_name(record.getStr("name"));
				if(Boolean.TRUE.equals(vo.getSelected())) {
					voList.add(0, vo);
					continue;
				}
				voList.add(vo);
			}
		}
		return voList;
	}
	
	@Override
	public void bindCreditCard(String userId, CreditCardTokenVo vo) {
		// 查询用户已绑定的卡
		List<OmCustomerCredit> creditList = filterValid(this.generalOmMapper.getAllCreditCardListByUserIdForUpdate(userId));
		Date nowtime = new Date();
		// 先检查卡ID
		CreditCardVo cardVo = this.omiseService.getCard(vo.getToken());
		if(cardVo == null) {
			throw GeneralRuntimeException.build("general.user.credit.card.bind.error.save.fail");
		}
		if(creditList != null && creditList.size() > 0) {
			for (OmCustomerCredit credit : creditList) {
				// 重复添加
				if(credit.getCardCode().equals(cardVo.getCard_code())) {
					throw GeneralRuntimeException.build("general.user.credit.card.bind.error.repeat");
				}
//				if(Boolean.TRUE.equals(vo.getSelected()) && Boolean.TRUE.equals(credit.getIsSelected())) {
//					credit.setIsSelected(false);
//					credit.setUpdatedBy(userId);
//					credit.setUpdateTime(nowtime);
//					if(this.omCustomerCreditMapper.updateByPrimaryKey(credit) <= 0) {
//						throw GeneralRuntimeException.build("general.user.credit.card.bind.error.save.fail");
//					}
//				}
			}
		}
		// 顾客信息
		UserInfo userInfo = this.generalUserService.getUserInfo(userId);
		
		// 通过顾客ID绑过卡
		if(creditList == null || creditList.size() == 0) {
			cardVo = this.omiseService.bindCard(userInfo.getEmail(), null, vo.getToken(), "customerid:" + userId, Kv.by("customerid", userId));
			if(cardVo != null) {
				cardVo.setSelected(true);
			}
		} else {
			cardVo = this.omiseService.bindCard(userInfo.getEmail(), creditList.get(0).getCustomerCode(), vo.getToken(), "customerid:" + userId, Kv.by("customerid", userId));
			if(cardVo != null) {
				cardVo.setSelected(false);
			}
		}
		
		if(cardVo == null) {
			throw GeneralRuntimeException.build("general.user.credit.card.bind.error.save.fail");
		}
		
		// 新卡作成
		OmCustomerCredit credit = new OmCustomerCredit();
		credit.setId(IDUtil.nextID());
		credit.setCustomerId(userId);
		credit.setCardCode(cardVo.getCard_code());
		credit.setCardNo(cardVo.getCard_no());
		credit.setCustomerCode(cardVo.getCustomer_code());
		credit.setCompanyName(cardVo.getCompany_name());
		credit.setIsSelected(cardVo.getSelected());
		credit.setValidDate(cardVo.getValid_date());
		credit.setName(cardVo.getHolder_name());
		credit.setStatus(Constant.STATUS_VALID);
		credit.setCreatedBy(userId);
		credit.setCreateTime(nowtime);
		
		if(this.omCustomerCreditMapper.insert(credit) <= 0) {
			throw GeneralRuntimeException.build("general.user.credit.card.bind.error.save.fail");
		}
	}
	
	@Override
	public void unbindCreditCard(String userId, String cardId) {
		OmCustomerCredit credit = this.omCustomerCreditMapper.selectByPrimaryKey(cardId);
		// 非本人的卡无权删除
		if(!credit.getCustomerId().equals(userId)) {
			throw GeneralRuntimeException.build("general.user.credit.card.unbind.error.not.myself");
		}
		
		credit.setStatus(Constant.STATUS_INVALID);
		credit.setUpdatedBy(userId);
		credit.setUpdateTime(new Date());
		if(this.omCustomerCreditMapper.updateByPrimaryKey(credit) <= 0) {
			throw GeneralRuntimeException.build("general.user.credit.card.unbind.error.save.fail");
		}
		
		this.omiseService.unbindCard(credit.getCustomerCode(), credit.getCardCode());
		
	}
	
	@Override
	public void selectCreditCard(String userId, String cardId) {
		OmCustomerCredit credit = this.omCustomerCreditMapper.selectByPrimaryKey(cardId);
		// 非本人的卡无权删除
		if(!credit.getCustomerId().equals(userId)) {
			throw GeneralRuntimeException.build("general.user.credit.card.unbind.error.not.myself");
		}
		// 查询用户已绑定的卡
		List<OmCustomerCredit> creditList = filterValid(this.generalOmMapper.getAllCreditCardListByUserIdForUpdate(userId));
		
		Date nowtime = new Date();
		if(creditList != null) {
			for (OmCustomerCredit omCustomerCredit : creditList) {
				if(omCustomerCredit.getId().equals(cardId)) {
					continue;
				}
				if(!Boolean.TRUE.equals(omCustomerCredit.getIsSelected())) {
					continue;
				}
				omCustomerCredit.setIsSelected(false);
				omCustomerCredit.setUpdatedBy(userId);
				omCustomerCredit.setUpdateTime(nowtime);
				this.omCustomerCreditMapper.updateByPrimaryKey(omCustomerCredit);
			}
		}
		
		credit.setIsSelected(true);
		credit.setUpdatedBy(userId);
		credit.setUpdateTime(nowtime);
		this.omCustomerCreditMapper.updateByPrimaryKey(credit);
		
	}
	
	@Override
	public PageLoadMoreVo<FeeDetailVo, FeeItemVo> getFeeHistoryList(String userId, PageLoadMoreVo<FeeDetailVo, FeeItemVo> vo) {
		List<FeeDetailVo> list = this.generalOmMapper.findFeeHistoryPageList(userId, vo);
		PageLoadMoreVo<FeeDetailVo, FeeItemVo> page = new PageLoadMoreVo<>();
		page.setDatas(list);
		if(page.getDatas() != null && page.getDatas().size() > 0) {
			page.setId(page.getDatas().get(page.getDatas().size() - 1).getFee_id());
			page.setTime(DateUtil.formatDateTime(page.getDatas().get(page.getDatas().size() - 1).getTime()));
		}
		return page;
	}
	
	@Override
	public FeeDetailVo getFeeDetail(String userId, String feeId) {
		OmDeductionRecord record = this.omDeductionRecordMapper.selectByPrimaryKey(feeId);
		FeeDetailVo vo = new FeeDetailVo();
		vo.setFee_id(feeId);
		vo.setMoney(record.getAmount());
		vo.setTitle(record.getDesc());
		vo.setTime(record.getDeductionTime());
		vo.setCard_no(record.getCardNo());
		vo.setCredit_company_name(record.getCreditCompanyName());
		
		return vo;
	}
	
	@Override
	public List<AutoDeductionVo> getAutoDeductionInfo(String userId) {
		// 原始数据
		List<AutoDeductionVo> voList = this.protectService.getProtectInfo(userId);
		// 2021-02-22
		// 共通物件, 共享保护
		if(voList != null) {
			for (int i = 0; i < voList.size(); i++) {
				AutoDeductionVo vo = voList.get(i);
				if(Constant.ENABLE.equals(vo.getStatus())) {
					continue;
				}
				Record record = Db.findFirst("select * from estate_protection where set_id=? and `status`=? ", vo.getSet_id(), Constant.STATUS_VALID);
				if(record != null) {
					vo.setStatus(Constant.ENABLE);
					vo.setContract_date(record.getDate("start_date"));
					vo.setNext_deduct_date(record.getDate("next_deduct_date"));
					vo.setQuit_date(record.getDate("quit_date"));
					vo.setMoney(record.getLong("money"));
					vo.setRemark(record.getStr("remark"));
					vo.setTitle(record.getStr("title"));
					// 卡号（显示加入者的卡号）
					vo.setCard_no(Db.queryStr("select card_no from om_customer_credit where customer_id=? and is_selected=? and `status`=? ", record.getStr("customer_id"), true, Constant.STATUS_VALID));
					vo.setHolder_name(Db.queryStr("select ifnull(fullname,'') from user_general where user_id=? ", record.getStr("customer_id")));
					// 只显示，不可操作
					vo.setOnly_show(true);
				} else {
					voList.remove(i--);
				}
			}
		}
		return voList;
	}
	
	@Override
	public void terminateAutoDeduction(String userId, String setId) {
		this.protectService.terminate(userId, setId, "顧客のキャンセル");
	}

	@Override
	public void joinAutoDeduction(String userId, String setId) {
		this.protectService.join(userId, setId);
	}
	
	private List<OmCustomerCredit> filterValid(List<OmCustomerCredit> credits) {
		List<OmCustomerCredit> validList= null;
		if(credits != null) {
			validList = new ArrayList<>();
			for (OmCustomerCredit credit : credits) {
				if(Constant.STATUS_VALID.equals(credit.getStatus())) {
					validList.add(credit);
				}
			}
		}
		return validList;
	}
	
}
