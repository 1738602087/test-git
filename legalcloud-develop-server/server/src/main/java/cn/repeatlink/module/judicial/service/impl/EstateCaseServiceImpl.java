/**
 *
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.CaseBuyer;
import cn.repeatlink.common.entity.CaseEstate;
import cn.repeatlink.common.entity.CaseEstateRecord;
import cn.repeatlink.common.entity.CaseEstateRecordUser;
import cn.repeatlink.common.entity.CaseSeller;
import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.common.entity.EstateUser;
import cn.repeatlink.common.entity.UserGeneral;
import cn.repeatlink.common.mapper.CaseBuyerMapper;
import cn.repeatlink.common.mapper.CaseEstateMapper;
import cn.repeatlink.common.mapper.CaseEstateRecordMapper;
import cn.repeatlink.common.mapper.CaseEstateRecordUserMapper;
import cn.repeatlink.common.mapper.CaseSellerMapper;
import cn.repeatlink.common.mapper.EstateInfoMapper;
import cn.repeatlink.common.mapper.EstateUserMapper;
import cn.repeatlink.common.mapper.UserGeneralMapper;
import cn.repeatlink.framework.event.RLEventKit;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.judicial.common.Constant.CaseBuyerFaceStatus;
import cn.repeatlink.module.judicial.common.Constant.CaseOperaAuth;
import cn.repeatlink.module.judicial.common.Constant.CaseRecordType;
import cn.repeatlink.module.judicial.common.Constant.CaseSellerVerify;
import cn.repeatlink.module.judicial.common.Constant.CaseStatus;
import cn.repeatlink.module.judicial.common.Constant.CaseStepVal;
import cn.repeatlink.module.judicial.common.Constant.EstateFetchStatus;
import cn.repeatlink.module.judicial.common.JudicialResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.event.CaseDealFinishedEvent;
import cn.repeatlink.module.judicial.event.CaseEvent.CaseBaseObj;
import cn.repeatlink.module.judicial.event.CaseRegFinishedEvent;
import cn.repeatlink.module.judicial.event.CaseSellerVerifySuccessEvent;
import cn.repeatlink.module.judicial.event.CaseSellerVerifySuccessEvent.CaseSellerVerifyObj;
import cn.repeatlink.module.judicial.mapper.CaseMapper;
import cn.repeatlink.module.judicial.mapper.EstateMapper;
import cn.repeatlink.module.judicial.service.ICaseBuyerChangeService;
import cn.repeatlink.module.judicial.service.IEstateCaseService;
import cn.repeatlink.module.judicial.service.IEstateService;
import cn.repeatlink.module.judicial.service.IUserFaceService;
import cn.repeatlink.module.judicial.util.IDUtil;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;
import cn.repeatlink.module.judicial.vo.estate.FaceVo;
import cn.repeatlink.module.judicial.vo.estatecase.CaseSearchVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBaseVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseBuyerVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseDetailVo;
import cn.repeatlink.module.judicial.vo.estatecase.EstatecaseSellerVo;
import cn.repeatlink.module.judicial.vo.user.BuyerUserInfo;
import cn.repeatlink.module.judicial.vo.user.FaceUserVo;
import cn.repeatlink.module.judicial.vo.user.SaleUserInfo;

/**
 * @author LAI
 * @date 2020-09-21 13:33
 *
 *  ????????????????????????
 */

@Service
public class EstateCaseServiceImpl implements IEstateCaseService {

    @Autowired
    private CaseMapper caseMapper;

    @Autowired
    private CaseEstateMapper caseEstateMapper;

    @Autowired
    private CaseBuyerMapper caseBuyerMapper;

    @Autowired
    private CaseSellerMapper caseSellerMapper;

    @Autowired
    private UserGeneralMapper userGeneralMapper;

    @Autowired
    private EstateInfoMapper estateInfoMapper;

    @Autowired
    private EstateUserMapper estateUserMapper;

    @Autowired
    private CaseEstateRecordMapper caseEstateRecordMapper;

    @Autowired
    private CaseEstateRecordUserMapper caseEstateRecordUserMapper;

    @Autowired
    private EstateMapper estateMapper;

    @Autowired
    private IUserFaceService userFaceService;

    @Autowired
    private ICaseBuyerChangeService caseBuyerChangeService;

    @Autowired
    private IEstateService estateService;

    @Override
    public PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> getCaseList(String groupId, String userId, PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> vo) {
        PageLoadMoreVo<EstatecaseBaseVo, CaseSearchVo> page = new PageLoadMoreVo<>();
        page.setSearch_vo(vo.getSearch_vo());
        // ????????????
        List<EstatecaseBaseVo> searchList = this.caseMapper.search(groupId, userId, vo);
        if (searchList != null && !searchList.isEmpty()) {
            page.setId(searchList.get(searchList.size() - 1).getCase_id());
            page.setTime(searchList.get(searchList.size() - 1).get_orderStr());
            for (int i = 0; i < searchList.size(); i++) {
                EstatecaseBaseVo item = searchList.get(i);
                if (item != null) {
                    if (StringUtils.equals(item.getUser_judicial_id(), userId)) {
                        item.setOperate_auth(CaseOperaAuth.UPDATEABLE);
                    } else {
                        item.setOperate_auth(CaseOperaAuth.NO_UPDATE);
                    }
                    item.setUser_judicial_id(null);
                }
            }
        }
        page.setDatas(searchList);
        page.setDatas(page.getDatas() == null ? new ArrayList<>() : page.getDatas());
        return page;
    }

    @Override
    public EstatecaseDetailVo getCaseDetail(String userId, String caseId) {
        EstatecaseDetailVo vo = new EstatecaseDetailVo();
        CaseEstate ce = this.checkCase(caseId, null);
        // ??????????????????
        vo.setCase_id(caseId);
        vo.setCase_name(ce.getCaseName());
        vo.setCreate_time(ce.getCreateTime());
        vo.setComplete_time(ce.getCompleteTime());
        vo.setStart_date(ce.getStartDate());
        vo.setStatus(ce.getStatus());
        vo.setAssigned_to(Db.queryStr("select fullname from user_judicial where user_id=?", ce.getAssignedTo()));
        vo.setStep_buyer_input(ce.getStepBuyerInput());
        vo.setStep_seller_verify(ce.getStepSellerVerify());
        vo.setStep_deal_finish(ce.getStepDealFinish());
        vo.setStep_reg_finish(ce.getStepRegFinish());
        vo.setFetch_api(ce.getFetchApi());
        vo.setEstate_list(this.getCaseEstateRecordInfo(ce.getCaseId(), CaseRecordType.SELLER));
        // ??????????????????
        vo.setBuyers(this.getCaseBuyersInfo(caseId));
        // ??????????????????
        vo.setSellers(this.getCaseSellersInfo(caseId));

        // 2020-12-07
        // ??????????????????
        vo.setOperate_auth(StringUtils.equals(userId, ce.getUserJudicialId()) ? CaseOperaAuth.UPDATEABLE : CaseOperaAuth.NO_UPDATE);

        return vo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createNewCase(String operaUserId, EstatecaseBaseVo vo) {
        if (vo == null || StringUtils.isBlank(vo.getCase_name())) {
            throw JudicialRuntimeException.build("judicial.case.add.error.name.not.null");
        }
        if (vo.getStart_date() == null) {
            throw JudicialRuntimeException.build("judicial.case.add.error.start.date.not.null");
        }
        CaseEstate ce = new CaseEstate();
        ce.setCaseName(vo.getCase_name().trim());
        ce.setCreatedBy(operaUserId);
        ce.setAssignedTo(operaUserId);
        ce.setCreateTime(new Date());
        ce.setStartDate(vo.getStart_date());
        ce.setUserJudicialId(operaUserId);
        ce.setStatus(CaseStatus.VALID); // ?????????????????????????????????FLAG????????? TODO
        ce.setCaseId(IDUtil.nextID());
        // ??????????????????
        ce.setStepSellerVerify(CaseStepVal.DEFAULT);
        ce.setStepBuyerInput(CaseStepVal.DEFAULT);
        ce.setStepDealFinish(CaseStepVal.DEFAULT);
        ce.setStepRegFinish(CaseStepVal.DEFAULT);
        ce.setFetchApi(CaseStepVal.DEFAULT);

        if (this.caseEstateMapper.insert(ce) <= 0) {
            throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
        }

        // ????????????
        List<EstateBaseVo> estateList = vo.getEstate_list();
        if (estateList == null || estateList.isEmpty()) {
            throw JudicialRuntimeException.build("judicial.case.add.error.estate.not.null");
        }
        for (EstateBaseVo estateInfo : vo.getEstate_list()) {
            // ???????????????????????????
            if (StringUtils.isNotBlank(estateInfo.getEstate_id())) {
                this.chooseEstate(operaUserId, ce.getCaseId(), estateInfo.getEstate_id(), null);
            }
            // ????????????
            else {
                this.addCaseEstateRecord(operaUserId, ce.getCaseId(), estateInfo);
            }
        }
        // 2021-02-04
		// ????????????????????????
        List<EstatecaseSellerVo> sellerList = this.getCaseSellersInfo(ce.getCaseId());
        if(sellerList == null || sellerList.size() == 0) {
        	ce.setStepSellerVerify(CaseStepVal.NO);
        	if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
                throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
            }
        }
        return ce.getCaseId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateCase(String operaUserId, EstatecaseBaseVo vo) {
        // ????????????
        CaseEstate ce = null;
        if (vo != null && StringUtils.isNotBlank(vo.getCase_id())) {
            ce = this.caseMapper.selectCaseEstateForUpdate(vo.getCase_id());
        }
        // Error ???????????????
        if (ce == null || StringUtils.isBlank(ce.getCaseId())) {
            throw JudicialRuntimeException.build("judicial.case.not.found");
        }
        // ????????????????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        // Error ????????????
        if (vo == null || StringUtils.isBlank(vo.getCase_name())) {
            throw JudicialRuntimeException.build("judicial.case.add.error.name.not.null");
        }
        if (vo.getStart_date() == null) {
            throw JudicialRuntimeException.build("judicial.case.add.error.start.date.not.null");
        }

        // ??????????????????
        ce.setCaseName(vo.getCase_name().trim());
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(new Date());
        ce.setStartDate(vo.getStart_date());

        // Error ??????????????????
        this.updateCaseEstates(operaUserId, vo.getCase_id(), vo.getEstate_list());
        
        // ????????????????????????
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
        }

        return ce.getCaseId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addCaseBuyer(String operaUserId, String caseId, EstatecaseBuyerVo vo) {
        // ??????????????????
        CaseEstate ce = this.checkCase(caseId, null);
        // ????????????????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        ce.setStepBuyerInput(CaseStepVal.DEFAULT);
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(new Date());
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            throw new JudicialRuntimeException(new JudicialResultCode(null, "judicial.case.add.buyer.error.save.fail"));
        }

        // ???????????????????????? TODO
        if (StringUtils.isBlank(vo.getCert_base64())) {
            throw JudicialRuntimeException.build("judicial.case.add.buyer.error.cert.null");
        }

        this.checkBuyerInfo(vo);

        UserGeneral userGeneral = this.userGeneralMapper.selectByPrimaryKey(vo.getUser_id());
        if (userGeneral == null) {
            throw JudicialRuntimeException.build("judicial.case.add.buyer.error.user.not.found");
        }
        
        // 2021-01-29
		// ????????????????????????
        List<CaseSeller> sellers = this.caseMapper.getSellers(caseId);
        if(sellers != null && sellers.size() > 0) {
        	for (CaseSeller seller : sellers) {
				if(vo.getUser_id().equals(seller.getUserGeneralId())) {
					throw JudicialRuntimeException.build("judicial.case.add.buyer.error.cant.be.seller");
				}
			}
        }

        String userId = StringUtils.trimToNull(vo.getUser_id());
        List<CaseBuyer> buyers = this.caseMapper.getBuyers(caseId);
        CaseBuyer buyer = null;
        if (buyers != null) {
            for (CaseBuyer caseBuyer : buyers) {
                if (userId.equals(caseBuyer.getUserGeneralId())) {
                    // ???????????????????????????????????????
                    if (Constant.STATUS_VALID.equals(caseBuyer.getStatus())) {
                        throw new JudicialRuntimeException(new JudicialResultCode(null, "judicial.case.add.buyer.error.buyer.exist"));
                    }
                    buyer = caseBuyer;
                    break;
                }
            }
        }
        if (buyer == null) {
            buyer = new CaseBuyer();
            buyer.setId(IDUtil.nextID());
            buyer.setCaseId(caseId);
            buyer.setUserGeneralId(userId);
            buyer.setCreatedBy(operaUserId);
            buyer.setCreateTime(new Date());
        } else {
            buyer.setUpdatedBy(operaUserId);
            buyer.setUpdateTime(new Date());
        }
        // ?????????????????????????????????????????????????????????????????????
        buyer.setFullname(vo.getFullname());
        buyer.setFullnameKana(vo.getFullname_kana());
        buyer.setPostcode(vo.getPostcode());
        buyer.setBirthday(vo.getBirthday());
        buyer.setGender(vo.getGender());
        buyer.setAddr1(vo.getAddr1());
        buyer.setAddr2(vo.getAddr2());
        buyer.setAddr3(vo.getAddr3());
        buyer.setFetchConfirm(Constant.DISABLED);
        buyer.setUserGeneralCert(vo.getCert_base64().getBytes());
        // 2021-02-22
		// ????????????
        buyer.setUserGeneralCertBack(vo.getCert_base64_back().getBytes());
        buyer.setStatus(Constant.STATUS_VALID);
        int n = 0;
        if (buyer.getUpdatedBy() == null) {
            n = this.caseBuyerMapper.insert(buyer);
        } else {
            n = this.caseBuyerMapper.updateByPrimaryKey(buyer);
        }
        // ??????????????????
        if (n <= 0) {
            throw new JudicialRuntimeException(new JudicialResultCode(null, "judicial.case.add.buyer.error.save.fail"));
        }

        // ???????????????????????????????????????????????????????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepBuyerInput())) {
            ce.setStepBuyerInput(CaseStepVal.DEFAULT);
            ce.setUpdatedBy(operaUserId);
            ce.setUpdatedTime(new Date());
            if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
                throw new JudicialRuntimeException(new JudicialResultCode(null, "judicial.case.add.buyer.error.save.fail"));
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateCaseBuyer(String operaUserId, String caseId, String userId, EstatecaseBuyerVo vo) {
        // ??????????????????????????????
        this.checkBuyerInfo(vo);
        // ????????????
        CaseEstate caseEstate = this.checkCase(caseId, null);

        // ????????????????????????
        if (CaseStepVal.COMPLETED.equals(caseEstate.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }

        List<CaseBuyer> buyers = this.caseMapper.getBuyers(caseId);
        CaseBuyer buyer = null;
        if (buyers != null) {
            for (CaseBuyer caseBuyer : buyers) {
                if (userId.equals(caseBuyer.getUserGeneralId())) {
                    buyer = caseBuyer;
                    break;
                }
            }
        }
        if (buyer == null) {
            // ?????????????????????
            throw JudicialRuntimeException.build("judicial.case.add.buyer.error.user.not.found");
        }

        // ???????????????????????????????????????
        CaseBuyer oldBuyer = BeanUtil.copyProperties(buyer, CaseBuyer.class, new String[]{"id"});

        // ??????
        buyer.setFullname(vo.getFullname());
        buyer.setFullnameKana(vo.getFullname_kana());
        buyer.setPostcode(vo.getPostcode());
        buyer.setBirthday(vo.getBirthday());
        buyer.setGender(vo.getGender());
        buyer.setAddr1(vo.getAddr1());
        buyer.setAddr2(vo.getAddr2());
        buyer.setAddr3(vo.getAddr3());
        buyer.setUpdatedBy(operaUserId);
        buyer.setUpdateTime(new Date());
        if (StringUtils.isNotBlank(vo.getCert_base64())) {
            buyer.setUserGeneralCert(vo.getCert_base64().getBytes());
        }
        if (StringUtils.isNotBlank(vo.getCert_base64_back())) {
            buyer.setUserGeneralCertBack(vo.getCert_base64_back().getBytes());
        }

        try {
            this.caseBuyerChangeService.saveChangeRecord(operaUserId, oldBuyer, buyer);
        } catch (Exception e) {
            if (!(e instanceof BaseRuntimeException)) {
                e.printStackTrace();
            }
            throw JudicialRuntimeException.build("judicial.case.update.buyer.error.save.fail");
        }

        if (this.caseBuyerMapper.updateByPrimaryKey(buyer) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.update.buyer.error.save.fail");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeCaseBuyer(String operaUserId, String caseId, String userId) {
        CaseEstate ce = this.checkCase(caseId, null);
        List<CaseBuyer> buyers = this.caseMapper.getBuyers(caseId);
        CaseBuyer buyer = null;
        if (buyers != null) {
            for (CaseBuyer caseBuyer : buyers) {
                if (userId.equals(caseBuyer.getUserGeneralId())) {
                    buyer = caseBuyer;
                    break;
                }
            }
        }
        if (buyer != null) {
            // ???????????????
            if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
                throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
            }
            // ?????????????????????
            int n = this.caseBuyerMapper.deleteByPrimaryKey(buyer.getId());
            if (n <= 0) {
                throw JudicialRuntimeException.build("judicial.case.remove.buyer.error.save.fail");
            }
        } else {
            throw JudicialRuntimeException.build("judicial.case.step.error.no.buyer");
        }
    }


    private void addCaseEstateRecord(String operaUserId, String caseId, EstateBaseVo estateInfo) {
        CaseEstate ce = this.checkCase(caseId, null);
        Date nowtime = new Date();
        // ???????????????
        if (StringUtils.isNotBlank(estateInfo.getHouse_id())) {
            estateInfo.setHouse_id(Convert.toSBC(estateInfo.getHouse_id()));
        }
        if (StringUtils.isNotBlank(estateInfo.getAddr())) {
        	estateInfo.setAddr(Convert.toSBC(estateInfo.getAddr()));
        }
        if (StringUtils.isNotBlank(estateInfo.getAddr_code())) {
        	if(!estateInfo.getAddr_code().contains("${code=")) {
        		estateInfo.setAddr_code(Convert.toSBC(estateInfo.getAddr_code()));
        	}
        } else {
        	estateInfo.setAddr_code(estateInfo.getAddr());
        }
        if (StringUtils.isNotBlank(estateInfo.getAddr2())) {
        	estateInfo.setAddr2(Convert.toSBC(estateInfo.getAddr2()));
        }
        // ??????????????????
        EstateInfo info = new EstateInfo();
        info.setAddr(estateInfo.getAddr());
        info.setAddrCode(estateInfo.getAddr_code());
        // 2021-03-02
		// ?????????????????????2
        info.setAddr2(estateInfo.getAddr2());
        info.setArea(estateInfo.getArea());
        info.setHouseId(estateInfo.getHouse_id());
        info.setStruct(estateInfo.getStruct());
        info.setType(estateInfo.getType());
        info.setRecord(estateInfo.getRecord());
        CaseEstateRecord estateRecord = this.buildEsateRecord(info);
        estateRecord.setAddr1(estateInfo.getAddr1());
        estateRecord.setCaseId(caseId);
        estateRecord.setCreatedBy(operaUserId);
        estateRecord.setCreateTime(nowtime);
        estateRecord.setRecordType(CaseRecordType.SELLER);
        // ???????????????????????????
        this.checkCaseEstateRecordRepeat(caseId, estateRecord);
        if (this.caseEstateRecordMapper.insert(estateRecord) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
        }
        // ??????????????????
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(nowtime);
        // ce.setStatus(CaseStatus.WAITING_VERIFY_SELLER_FACE);
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public EstateUserInfo chooseEstate(String operaUserId, String caseId, String estateId, String faceCode) {
        // ????????????(???????????????????????????????????????????????????????????????????????????)
        EstateInfo estateInfo = this.caseMapper.selectEstateForUpdate(estateId);
        if (estateInfo == null) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.estate.not.found");
        }

        // ????????????????????????????????????
        Record record = Db.findFirst("select er.record_id,er.case_id from (select * from case_estate_record WHERE `status` = 1 and estate_id = ?) er LEFT JOIN case_estate ce ON er.case_id = ce.case_id  where ce.`step_reg_finish` = ?", estateId, CaseStepVal.DEFAULT);
        if (record != null && !StringUtils.equals(caseId, record.getStr("case_id"))) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.estate.with.trade", StringUtils.join(estateInfo.getAddr(), estateInfo.getAddr2(), estateInfo.getHouseId()));
        }
        // ????????????
        CaseEstate ce = this.checkCase(caseId, null);
        Date nowtime = new Date();
        // ??????????????????
        CaseEstateRecord estateRecord = this.buildEsateRecord(estateInfo);
        estateRecord.setCaseId(caseId);
        estateRecord.setCreatedBy(operaUserId);
        estateRecord.setCreateTime(nowtime);
        estateRecord.setStatus(Constant.STATUS_VALID);
        estateRecord.setRecordType(CaseRecordType.SELLER);
        if (this.caseEstateRecordMapper.insert(estateRecord) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
        }
        // ?????????????????????
        List<EstateUser> estateUserList = this.caseMapper.selectEstateUserList(estateId);
        // ??????????????????????????????????????????????????????????????????????????? TODO
        String facedUserId = null;
        // ????????????????????????
        int userSize = 0;
        List<CaseSeller> existSellerList = this.caseMapper.getSellers(caseId);
        List<CaseSeller> sellerList = new ArrayList<>();
        for (EstateUser estateUser : estateUserList) {
            if (!Constant.STATUS_VALID.equals(estateUser.getStatus())) {
                continue;
            }
            String sellerId = estateUser.getUserGeneralId();
            // ?????????????????????????????????
            if (existSellerList != null && existSellerList.stream().anyMatch(seller -> sellerId.equals(seller.getUserGeneralId()))) {
                userSize++;
                continue;
            }
            // ??????????????????
            CaseSeller seller = new CaseSeller();
            seller.setId(cn.repeatlink.framework.util.IDUtil.nextID());
            seller.setCaseId(caseId);
            seller.setUserGeneralId(sellerId);
            seller.setCreatedBy(operaUserId);
            seller.setCreateTime(nowtime);
            seller.setStatus(Constant.STATUS_VALID);
            seller.setVerify(seller.getUserGeneralId().equals(facedUserId) ? CaseSellerVerify.VERIFYED : CaseSellerVerify.WAITING_VERIFY);
            if (this.caseSellerMapper.insert(seller) <= 0) {
                throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
            }
            sellerList.add(seller);

            CaseEstateRecordUser estateRecordUser = new CaseEstateRecordUser();
            estateRecordUser.setId(cn.repeatlink.framework.util.IDUtil.nextID());
            estateRecordUser.setEstateRecordId(estateRecord.getRecordId());
            estateRecordUser.setUserGeneralId(seller.getUserGeneralId());
            estateRecordUser.setCreateTime(nowtime);
            estateRecordUser.setStatus(Constant.STATUS_VALID);
            if (this.caseEstateRecordUserMapper.insert(estateRecordUser) <= 0) {
                throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
            }

            userSize++;
        }
        
        // 2021-03-04
		// ????????????????????????????????????????????????
//        if (userSize == 0) {
//            throw JudicialRuntimeException.build("judicial.estate.error.bind.owner.not.found");
//        }

        // ??????????????????
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(nowtime);

        // ce.setStatus(userSize == 1 && StringUtils.isNotBlank(facedUserId) ? CaseStatus.WAITING_INPUT_BUYER_FACE : CaseStatus.WAITING_VERIFY_SELLER_FACE);

        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.save.fail");
        }

        // ??????????????????
        EstateUserInfo info = new EstateUserInfo();
        info.setEstate_id(estateId);
        info.setHouse_id(estateRecord.getHouseId());
        info.setAddr(estateRecord.getAddr());
        info.setAddr_code(estateRecord.getAddrCode());
        info.setAddr2(estateRecord.getAddr2());
        // ????????????
        info.setUsers(new ArrayList<>());
        for (CaseSeller seller : sellerList) {
            SaleUserInfo userInfo = this.querySellerInfo(seller.getUserGeneralId());
            userInfo.setVerify(seller.getVerify());
            info.getUsers().add(userInfo);
        }
        // ???????????? TODO


        return info;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void verifyCaseSeller(String operaUserId, String caseId, EstatecaseSellerVo vo) {
        if (vo == null || vo.getFace_code_info() == null || vo.getUser_id() == null) {
            throw JudicialRuntimeException.build("manager.data.null");
        }
        CaseEstate caseEstate = this.checkCase(caseId, null);
        String sellerId = vo.getUser_id();
        UserGeneral sellerUser = this.userGeneralMapper.selectByPrimaryKey(sellerId);
        if (sellerUser == null) {
            throw JudicialRuntimeException.build("judicial.case.seller.error.user.not.found");
        }
        List<CaseSeller> sellers = this.caseMapper.getSellers(caseId);
        // ?????????????????????
        boolean toNext = true;
        Date nowtime = new Date();
        for (CaseSeller caseSeller : sellers) {
            if (!Constant.STATUS_VALID.equals(caseSeller.getStatus())) {
                continue;
            }
            if (sellerId.equals(caseSeller.getUserGeneralId())) {
                if (CaseSellerVerify.VERIFYED.equals(caseSeller.getVerify())) {
                    // ???????????????????????????
                    throw JudicialRuntimeException.build("judicial.case.seller.error.verifyed");
                }
                // ?????????????????????
                // 2020-11-24
                // ??????face_code??????
//				String imgBase64Data = vo.getPic_base64_list().get(0);
//				Boolean passed = sellerId.equals(this.userFaceService.getUserIdByImage(imgBase64Data));
                FaceUserVo faceUserVo = this.userFaceService.verifyFaceCode(sellerId, vo.getFace_code_info().getUse_type(), vo.getFace_code_info());
                // ????????????
                if (sellerId.equals(faceUserVo.getUser_id())) {
                    // ?????????????????????????????????????????????
                    RLEventKit.publish(new CaseSellerVerifySuccessEvent(new CaseSellerVerifyObj().setCaseId(caseId).setSellerId(sellerId).setImgBase64Data(faceUserVo.getFace_code_info().getBase64data())));
                    // ????????????
                    caseSeller.setVerify(CaseSellerVerify.VERIFYED);
                    caseSeller.setUpdatedBy(operaUserId);
                    caseSeller.setUpdateTime(nowtime);
                    if (this.caseSellerMapper.updateByPrimaryKey(caseSeller) <= 0) {
                        throw JudicialRuntimeException.build("judicial.case.seller.error.verify.save.fail");
                    }
                } else {
                    toNext = false;
                }
            } else {
                if (!CaseSellerVerify.VERIFYED.equals(caseSeller.getVerify())) {
                    toNext = false;
                }
            }
        }
        // ???????????????
        // 2020-11-03
        // ??????????????????
        if (toNext) {
            // caseEstate.setStatus(CaseStatus.WAITING_INPUT_BUYER_FACE);
            caseEstate.setStepSellerVerify(CaseStepVal.COMPLETED);
            caseEstate.setUpdatedBy(operaUserId);
            caseEstate.setUpdatedTime(nowtime);
            if (this.caseEstateMapper.updateByPrimaryKey(caseEstate) <= 0) {
                throw JudicialRuntimeException.build("judicial.case.seller.error.verify.save.fail");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
	public void inputNewEstate(String operaUserId, String caseId, EstateBaseVo vo) {
        CaseEstate caseEstate = this.checkCase(caseId, null);
        Date nowtime = new Date();
        // ??????????????????
        EstateInfo estateInfo = null; // this.caseMapper.selectEstateForUpdate(caseEstate.getEstateId());
        // estateInfo.setCertImage();
        estateInfo.setAddr(vo.getAddr());
        estateInfo.setArea(vo.getArea());
        estateInfo.setHouseId(vo.getHouse_id());
        estateInfo.setStruct(vo.getStruct());
        estateInfo.setType(vo.getType());
        estateInfo.setRecord(vo.getRecord());
        estateInfo.setStatus(Constant.STATUS_VALID);
        estateInfo.setUpdatedBy(operaUserId);
        estateInfo.setUpdateTime(nowtime);
        if (this.estateInfoMapper.updateByPrimaryKey(estateInfo) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.input.estate.error.save.fail");
        }
        // ????????????????????????
        CaseEstateRecord estateRecord = this.buildEsateRecord(estateInfo);
        estateRecord.setCaseId(caseId);
        estateRecord.setCreatedBy(operaUserId);
        estateRecord.setCreateTime(nowtime);
        estateRecord.setStatus(Constant.STATUS_VALID);
        estateRecord.setRecordType(CaseRecordType.SELLER);
        if (this.caseEstateRecordMapper.insert(estateRecord) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.input.estate.error.save.fail");
        }
        // ?????????????????????????????????
        List<CaseSeller> sellers = this.caseMapper.getSellers(caseId);
        for (CaseSeller caseSeller : sellers) {
            if (!Constant.STATUS_VALID.equals(caseSeller.getStatus())) {
                continue;
            }
            CaseEstateRecordUser estateRecordUser = new CaseEstateRecordUser();
            estateRecordUser.setId(IDUtil.nextID());
            estateRecordUser.setEstateRecordId(estateRecord.getRecordId());
            estateRecordUser.setUserGeneralId(caseSeller.getUserGeneralId());
            estateRecordUser.setStatus(Constant.STATUS_VALID);
            estateRecordUser.setCreateTime(nowtime);
            if (this.caseEstateRecordUserMapper.insert(estateRecordUser) <= 0) {
                throw JudicialRuntimeException.build("judicial.case.input.estate.error.save.fail");
            }
        }

        // ???????????? -> ?????????
        caseEstate.setUpdatedBy(operaUserId);
        caseEstate.setUpdatedTime(nowtime);
        if (this.caseEstateMapper.updateByPrimaryKey(caseEstate) <= 0) {
            throw JudicialRuntimeException.build("judicial.case.input.estate.error.save.fail");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void stepBuyerInput(String operaUserId, String caseId) {
        CaseEstate ce = this.checkCase(caseId, null);
        // ????????????????????????
//		if(!CaseStepVal.COMPLETED.equals(ce.getStepSellerVerify()) || !CaseStepVal.COMPLETED.equals(ce.getStepBuyerInput())) {
//			throw JudicialRuntimeException.build("");
//		}
        // ???????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        
        List<EstatecaseBuyerVo> buyerList = this.getCaseBuyersInfo(caseId);
        if (buyerList != null && buyerList.size() > 0) {
        	for (EstatecaseBuyerVo buyerVo : buyerList) {
                if (!CaseBuyerFaceStatus.INPUTED.equals(buyerVo.getFace_status())) {
                    throw JudicialRuntimeException.build("judicial.case.step.error.not.buyer.input.complete");
                }
            }
        	ce.setStepBuyerInput(CaseStepVal.COMPLETED);
        } else {
        	ce.setStepBuyerInput(CaseStepVal.NO);
        }
        
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(new Date());
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            // ????????????
            throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void stepDealFinished(String operaUserId, String caseId) {
        CaseEstate ce = this.checkCase(caseId, null);

        // ???????????????????????????????????????
        List<EstatecaseSellerVo> sellerList = this.getCaseSellersInfo(caseId);
        if (sellerList != null && sellerList.size() > 0) {
            // ?????????????????????????????????????????????????????????
            if (!CaseStepVal.COMPLETED.equals(ce.getStepSellerVerify())) {
                throw JudicialRuntimeException.build("judicial.case.step.error.not.seller.verify");
            }
        }
        // 2020-11-03
        // ????????????????????????????????????????????????
        List<EstatecaseBuyerVo> buyerList = this.getCaseBuyersInfo(caseId);
        if (buyerList != null && buyerList.size() > 0) {
            if (!CaseStepVal.COMPLETED.equals(ce.getStepBuyerInput())) {
                throw JudicialRuntimeException.build("judicial.case.step.error.not.buyer.input.complete");
            }
            for (EstatecaseBuyerVo buyerVo : buyerList) {
                if (!CaseBuyerFaceStatus.INPUTED.equals(buyerVo.getFace_status())) {
                    throw JudicialRuntimeException.build("judicial.case.step.error.not.buyer.input.complete");
                }
            }
        }
        // 2021-02-04
		// ?????????????????????????????????
        if((sellerList == null || sellerList.size() == 0) && (buyerList == null || buyerList.size() == 0) ) {
        	throw JudicialRuntimeException.build("judicial.case.step.error.no.buyer.input");
        }

        // ???????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        ce.setStepDealFinish(CaseStepVal.COMPLETED);
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(new Date());
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            // ????????????
            throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
        }
        // ???????????????????????????????????????????????????
        if (sellerList != null && sellerList.size() > 0) {
            this.unbindEsateOwnerByCaseId(operaUserId, caseId);
        }
        // ?????????????????????????????????????????????
        if (buyerList != null && buyerList.size() > 0) {
            this.bindEstateOwnerByCaseId(operaUserId, caseId);
        }
        // ????????????????????????
        RLEventKit.publish(new CaseDealFinishedEvent(new CaseBaseObj().setCaseId(caseId)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void stepRegFinished(String operaUserId, String caseId) {
        CaseEstate ce = this.checkCase(caseId, null);
        // ???????????? -> ????????????
        if (!CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.not.dealfinish");
        }
        // ???????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepRegFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.regfinished");
        }
        ce.setStepRegFinish(CaseStepVal.COMPLETED);
        ce.setUpdatedBy(operaUserId);
        ce.setUpdatedTime(new Date());
        ce.setCompleteTime(ce.getUpdatedTime());
        if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
            // ????????????
            throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
        }

        // ????????????????????????
        RLEventKit.publish(new CaseRegFinishedEvent(new CaseBaseObj().setCaseId(caseId)));
    }

    private void unbindEsateOwnerByCaseId(String operUserId, String caseId) {
        List<EstateBaseVo> estateList = this.getCaseEstateRecordInfo(caseId, CaseRecordType.SELLER);
        if (estateList != null) {
            for (EstateBaseVo estateBaseVo : estateList) {
                String estateId = estateBaseVo.getEstate_id();
                if (StringUtils.isNotBlank(estateId)) {
                    this.estateService.unbindOwner(operUserId, estateId);
                    // 2021-02-22
					// ??????????????????
                    EstateInfo estateInfo = this.estateInfoMapper.selectByPrimaryKey(estateId);
                    // ???????????????????????????????????????ID
                    estateInfo.setSetId(null);
                    this.estateInfoMapper.updateByPrimaryKey(estateInfo);
                }
            }
        }
    }

    private void bindEstateOwnerByCaseId(String operUserId, String caseId) {
        List<CaseEstateRecord> estateList = this.caseMapper.getCaseEstateRecordList(caseId, CaseRecordType.SELLER, Constant.STATUS_VALID);
        if (estateList != null) {
            List<EstatecaseBuyerVo> buyerList = this.getCaseBuyersInfo(caseId);
            if (buyerList == null || buyerList.isEmpty()) {
                throw JudicialRuntimeException.build("judicial.case.step.error.no.buyer");
            }
            List<String> userIdList = new ArrayList<>();
            for (EstatecaseBuyerVo buyerVo : buyerList) {
                userIdList.add(buyerVo.getUser_id());
            }
            
            String setId = caseId;
            for (CaseEstateRecord estateRecord : estateList) {
                String estateId = estateRecord.getEstateId();
                // ????????????????????????????????????
                if (StringUtils.isBlank(estateId)) {
                    EstateInfo estate = this.estateService.createEstate(operUserId, this.convert2Vo(estateRecord));
                    estateRecord.setEstateId(estate.getEstateId());
                    this.caseEstateRecordMapper.updateByPrimaryKey(estateRecord);
                    estateId = estateRecord.getEstateId();
                }
                if (StringUtils.isNotBlank(estateId)) {
                    this.estateService.bindOwner(operUserId, estateId, userIdList);
                    // 2021-02-22
					// ??????????????????
                    EstateInfo estateInfo = this.estateInfoMapper.selectByPrimaryKey(estateId);
                    estateInfo.setSetId(setId);
                    this.estateInfoMapper.updateByPrimaryKey(estateInfo);
                }
            }
        }
    }

    @Override
    public CaseEstate checkCase(String caseId, Short status) {
        // ?????????????????????????????????
        CaseEstate ce = this.caseMapper.selectCaseEstateForUpdate(caseId);
        if (ce == null) {
            throw JudicialRuntimeException.build("judicial.case.not.found");
        }
        if (status != null) {
            // ???????????????????????????
//            if (CaseStatus.COMPLETED.equals(ce.getStatus())) {
//                throw JudicialRuntimeException.build("judicial.case.completed.not.opera");
//            }
//			if(CaseStatus.WAITING_INPUT_BUYER_INFO.equals(status)) {
//				if(!status.equals(ce.getStatus())) {
//					throw JudicialRuntimeException.build("judicial.case.status.not.opera");
//				}
//			}
//			if(CaseStatus.WAITING_CHOOSE_ESTATE.equals(ce.getStatus())) {
//				if(!CaseStatus.WAITING_CHOOSE_ESTATE.equals(ce.getStatus()) && !CaseStatus.WAITING_INPUT_BUYER_INFO.equals(ce.getStatus())) {
//					throw JudicialRuntimeException.build("judicial.case.completed.not.opera");
//				}
//			}
        }
        return ce;
    }

	private CaseEstateRecord buildEsateRecord(EstateInfo estateInfo) {
		CaseEstateRecord record = new CaseEstateRecord();
		record.setRecordId(cn.repeatlink.framework.util.IDUtil.nextID());
		record.setEstateId(estateInfo.getEstateId());
		record.setAddr(estateInfo.getAddr());
		record.setAddrCode(estateInfo.getAddrCode());
		// 2021-03-02
		// ?????????????????????2
		record.setAddr2(estateInfo.getAddr2());
		record.setArea(estateInfo.getArea());
		record.setCertImage(estateInfo.getCertImage());
		record.setHouseId(estateInfo.getHouseId());
		record.setRecord(estateInfo.getRecord());
		record.setRemark(estateInfo.getRemark());
		record.setStruct(estateInfo.getStruct());
		record.setType(estateInfo.getType());
		record.setStatus(Constant.STATUS_VALID);
		record.setFetchStatus(EstateFetchStatus.NOT_FETCH);
		return record;
	}
	
	private EstateBaseVo convert2Vo(CaseEstateRecord estateRecord) {
		EstateBaseVo vo = new EstateBaseVo();
		vo.setRecord_id(estateRecord.getRecordId());
		vo.setEstate_id(estateRecord.getEstateId());
		vo.setAddr1(estateRecord.getAddr1());
		vo.setAddr(estateRecord.getAddr());
		vo.setAddr_code(estateRecord.getAddrCode());
		// 2021-03-02
		// ?????????????????????2
		vo.setAddr2(estateRecord.getAddr2());
		vo.setArea(estateRecord.getArea());
		vo.setHouse_id(estateRecord.getHouseId());
		vo.setStruct(estateRecord.getStruct());
		vo.setType(estateRecord.getType());
		vo.setRecord(estateRecord.getRecord());
		vo.setEstate_no(estateRecord.getEstateNo());
		vo.setCategory(estateRecord.getCategory());
		return vo;
	}

    private SaleUserInfo querySellerInfo(String sellerId) {
        Record r = Db.findFirst("select * from user_general where user_id=?", sellerId);
        SaleUserInfo userInfo = new SaleUserInfo();
        userInfo.setUser_id(sellerId);
        userInfo.setGender(r.getShort("gender"));
        userInfo.setFullname(r.getStr("fullname"));
        userInfo.setFullname_kana(r.getStr("fullname_kana"));
        userInfo.setAddr(StringUtils.trimToEmpty(r.getStr("addr1")) + StringUtils.trimToEmpty(r.getStr("addr2")) + StringUtils.trimToEmpty(r.getStr("addr3")));
        return userInfo;
    }

    @Override
    public List<EstateBaseVo> getCaseEstateRecordInfo(String caseId) {
        return this.getCaseEstateRecordInfo(caseId, CaseRecordType.SELLER);
    }

    private List<EstateBaseVo> getCaseEstateRecordInfo(String caseId, Short type) {
        List<EstateBaseVo> voList = new ArrayList<>();
        List<CaseEstateRecord> estateRecordList = this.caseMapper.getCaseEstateRecordList(caseId, type, Constant.STATUS_VALID);
        Set<String> estateIdSet = new HashSet<>();
        for (CaseEstateRecord estateRecord : estateRecordList) {
            if (estateRecord.getEstateId() != null && estateIdSet.contains(estateRecord.getEstateId())) {
                continue;
            }
            EstateBaseVo vo = this.convert2Vo(estateRecord);
            if(StringUtils.isNotBlank(vo.getEstate_id())) {
            	String pdfPath = Db.queryStr("select pdf_path from estate_info where estate_id=? limit 1", vo.getEstate_id());
            	vo.setHas_pdf(StringUtils.isNotBlank(pdfPath));
            }
            voList.add(vo);
            estateIdSet.add(vo.getEstate_id());
        }

        return voList;
    }

    @Override
    public List<EstatecaseBuyerVo> getCaseBuyersInfo(String caseId) {
        List<EstatecaseBuyerVo> buyerVoList = new ArrayList<>();
        List<CaseBuyer> list = this.caseMapper.getBuyers(caseId);
        if (list != null) {
            for (CaseBuyer caseBuyer : list) {
                if (!Constant.STATUS_VALID.equals(caseBuyer.getStatus())) {
                    continue;
                }
                EstatecaseBuyerVo buyerVo = new EstatecaseBuyerVo();
                buyerVo.setUser_id(caseBuyer.getUserGeneralId());
                buyerVo.setCert_base64(new String(caseBuyer.getUserGeneralCert()));
                // 2021-02-22
				// ??????????????????????????????
                if(caseBuyer.getUserGeneralCertBack() != null) {
                	buyerVo.setCert_base64_back(new String(caseBuyer.getUserGeneralCertBack()));
                }
                //
                // buyerVo.setUser_name(r.getStr("user_name"));
                buyerVo.setFullname(caseBuyer.getFullname());
                buyerVo.setFullname_kana(caseBuyer.getFullnameKana());
                buyerVo.setGender(caseBuyer.getGender());
                buyerVo.setAddr1(caseBuyer.getAddr1());
                buyerVo.setAddr2(caseBuyer.getAddr2());
                buyerVo.setAddr3(caseBuyer.getAddr3());
                buyerVo.setBirthday(caseBuyer.getBirthday());
                // 2021-03-04
				// ??????????????????
                buyerVo.setPostcode(caseBuyer.getPostcode());
                // ??????????????????ID??????
//				List<String> userFaceIds = this.userFaceService.getUserFaceIds(buyerVo.getUser_id());
//				buyerVo.setFace_status((userFaceIds == null || userFaceIds.isEmpty()) ? CaseBuyerFaceStatus.NOT_INPUT : CaseBuyerFaceStatus.INPUTED);
                FaceVo userFaceInfo = this.userFaceService.getUserFaceInfo(buyerVo.getUser_id());
                buyerVo.setFace_status((userFaceInfo == null) ? CaseBuyerFaceStatus.NOT_INPUT : CaseBuyerFaceStatus.INPUTED);
                if (userFaceInfo != null)
                    buyerVo.setUpdateTime(userFaceInfo.getUpdateTime());
                buyerVoList.add(buyerVo);
            }
        }

        return buyerVoList;
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void invalidCase(String operUserId, String caseId) {
    	// ?????????????????????????????????
        CaseEstate ce = this.caseMapper.selectCaseEstateForUpdate(caseId);
        if (ce == null) {
            throw JudicialRuntimeException.build("judicial.case.not.found");
        }
        // ??????????????????XXX???
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        Date nowtime = new Date();
        // ??????????????????
        List<CaseEstateRecord> estateRecordList = this.caseMapper.getCaseEstateRecordList(caseId, CaseRecordType.SELLER, Constant.STATUS_VALID);
        if(estateRecordList != null) {
        	for (CaseEstateRecord record : estateRecordList) {
        		record.setStatus(Constant.STATUS_INVALID);
        		record.setUpdatedBy(operUserId);
        		record.setUpdateTime(nowtime);
				this.caseEstateRecordMapper.updateByPrimaryKey(record);
			}
        }
        // ????????????
        List<CaseSeller> sellers = this.caseMapper.getSellers(caseId);
        if(sellers != null) {
        	for (CaseSeller caseSeller : sellers) {
        		caseSeller.setStatus(Constant.STATUS_INVALID);
        		caseSeller.setUpdatedBy(operUserId);
        		caseSeller.setUpdateTime(nowtime);
        		this.caseSellerMapper.updateByPrimaryKey(caseSeller);
			}
        }
        // ????????????
        List<CaseBuyer> buyers = this.caseMapper.getBuyers(caseId);
        if(buyers != null) {
        	for (CaseBuyer caseBuyer : buyers) {
				caseBuyer.setStatus(Constant.STATUS_INVALID);
				caseBuyer.setUpdatedBy(operUserId);
				caseBuyer.setUpdateTime(nowtime);
        		this.caseBuyerMapper.updateByPrimaryKey(caseBuyer);
			}
        }
        
        // ????????????
        ce.setStatus(CaseStatus.INVALID);
        ce.setUpdatedBy(operUserId);
        ce.setUpdatedTime(nowtime);
        
        this.caseEstateMapper.updateByPrimaryKey(ce);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateCaseEstates(String operUserId, String caseId, List<EstateBaseVo> estates) {
    	// ??????????????????
        CaseEstate ce = this.checkCase(caseId, null);
        // ????????????????????????
        if (CaseStepVal.COMPLETED.equals(ce.getStepDealFinish())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.dealfinished");
        }
        // Error ??????????????????
        List<EstateBaseVo> estateList = estates;
        if (estateList == null || estateList.isEmpty()) {
            throw JudicialRuntimeException.build("judicial.case.add.error.estate.not.null");
        }

        List<CaseEstateRecord> recordList = this.caseMapper.getCaseEstateRecordList(ce.getCaseId(), CaseRecordType.SELLER, Constant.STATUS_VALID);
        // ??????????????????
        boolean sameEstate = true;
        // ????????????
        if (estateList.size() != recordList.size()) {
            sameEstate = false;
        } else {
            // ??????????????????
            for (EstateBaseVo bv : estateList) {
                String estateId = bv.getEstate_id();
                // ???????????????????????????
                boolean foundEstate = false;
                // ???????????????
                if (StringUtils.isNotBlank(estateId)) {
                    for (CaseEstateRecord er : recordList) {
                        if (estateId.equals(er.getEstateId())) {
                            foundEstate = true;
                            break;
                        }
                    }
                }
                // ?????????????????????
                else {
                    String addr = bv.getAddr();
                    String addr2 = bv.getAddr2();
                    String type = bv.getType();
                    String house_id = bv.getHouse_id();
                    for (CaseEstateRecord er : recordList) {
                        if (StringUtils.equals(addr, er.getAddr()) && StringUtils.equals(type, er.getType()) && StringUtils.equals(house_id, er.getHouseId())
                        		&& StringUtils.equals(addr2, er.getAddr2())) {
                            foundEstate = true;
                            break;
                        }
                    }
                }

                // ???????????????????????????????????????
                if (!foundEstate) {
                    sameEstate = false;
                }
            }
        }

        // ???????????????????????????????????????
        if (!sameEstate) {
            // ????????????????????????????????????
            ce.setStepSellerVerify(CaseStepVal.DEFAULT);
            // ????????????????????????
            this.caseMapper.deleteCaseSellerByCaseId(ce.getCaseId());
            this.caseMapper.deleteCaseEstateRecordUserByCaseId(ce.getCaseId());
            this.caseMapper.deleteCaseEstateRecordByCaseId(ce.getCaseId());
            // ??????????????????????????????
            for (EstateBaseVo estateInfo : estateList) {
                // ???????????????
                if (StringUtils.isNotBlank(estateInfo.getHouse_id())) {
                    estateInfo.setHouse_id(Convert.toSBC(estateInfo.getHouse_id()));
                }
                // ???????????????????????????
                if (StringUtils.isNotBlank(estateInfo.getEstate_id())) {
                    this.chooseEstate(operUserId, ce.getCaseId(), estateInfo.getEstate_id(), null);
                }
                // ????????????
                else {
                    this.addCaseEstateRecord(operUserId, ce.getCaseId(), estateInfo);
                }
            }
            // 2021-03-18
            // ????????????????????????
            List<EstatecaseSellerVo> sellerList = this.getCaseSellersInfo(ce.getCaseId());
            if(sellerList == null || sellerList.size() == 0) {
            	ce.setStepSellerVerify(CaseStepVal.NO);
            }
            // ????????????????????????
            if (this.caseEstateMapper.updateByPrimaryKey(ce) <= 0) {
                throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
            }
        }
    }
    
    private List<EstatecaseSellerVo> getCaseSellersInfo(String caseId) {
        List<EstatecaseSellerVo> sellerVoList = new ArrayList<>();
        List<CaseSeller> list = this.caseMapper.getSellers(caseId);
        if (list != null) {
            for (CaseSeller caseSeller : list) {
                if (!Constant.STATUS_VALID.equals(caseSeller.getStatus())) {
                    continue;
                }
                EstatecaseSellerVo sellerVo = new EstatecaseSellerVo();
                sellerVo.setUser_id(caseSeller.getUserGeneralId());
                sellerVo.setVerify(caseSeller.getVerify());
                //
                Record r = this.getUserGeneralInfo(sellerVo.getUser_id());
                if (r != null) {
                    sellerVo.setFullname(r.getStr("fullname"));
                    sellerVo.setFullname_kana(r.getStr("fullname_kana"));
                    sellerVo.setGender(r.getShort("gender"));
                }
                sellerVoList.add(sellerVo);
            }
        }

        return sellerVoList;
    }

    private Record getUserGeneralInfo(String userId) {
        return Db.findFirst("SELECT * from user_general where user_id = ?", userId);
    }

    @Override
    public BuyerUserInfo getBuyerUserInfo(String userId) {
        UserGeneral userGeneral = this.userGeneralMapper.selectByPrimaryKey(userId);
        if (userGeneral == null) {
            throw JudicialRuntimeException.build("judicial.case.add.buyer.error.qr.user.not.found");
        }
        BuyerUserInfo info = new BuyerUserInfo();
        info.setUser_id(userGeneral.getUserId());
        info.setFullname(userGeneral.getFullname());
        info.setFullname_kana(userGeneral.getFullnameKana());
        info.setPostcode(userGeneral.getPostcode());
        info.setBirthday(userGeneral.getBirthday());
        info.setAddr1(userGeneral.getAddr1());
        info.setAddr2(userGeneral.getAddr2());
        info.setAddr3(userGeneral.getAddr3());
        info.setGender(userGeneral.getGender());

        return info;
    }

    private void checkBuyerInfo(EstatecaseBuyerVo vo) {
        if (StringUtils.isBlank(vo.getFullname()) || StringUtils.isBlank(vo.getFullname_kana())) {
            throw JudicialRuntimeException.build("judicial.case.step.error.buyer.info.incompelte");
        }
        if (vo.getGender() == null) {
            throw JudicialRuntimeException.build("judicial.case.step.error.buyer.info.incompelte");
        }
        if (vo.getBirthday() == null) {
            throw JudicialRuntimeException.build("judicial.case.step.error.buyer.info.incompelte");
        }
        if (vo.getUser_id() == null) {
            throw JudicialRuntimeException.build("judicial.case.step.error.buyer.info.incompelte");
        }
    }

    private void checkCaseEstateRecordRepeat(String caseId, CaseEstateRecord record) {
        if (record == null || StringUtils.isNotBlank(record.getEstateId())) return;
        String addr = record.getAddr();
        String addr2 = record.getAddr2();
        String type = record.getType();
        String houseId = record.getHouseId();

        // ???????????????????????????
        List<EstateInfo> list = this.estateMapper.getEstateByInfo(type, addr, addr2, houseId, Constant.STATUS_VALID);
        if (list != null && list.size() > 0) {
            throw JudicialRuntimeException.build("judicial.estate.error.info.exist", StringUtils.join(addr, addr2, houseId));
        }

        // ???????????????????????????
        List<CaseEstateRecord> list2 = this.caseMapper.getCaseEstateRecordByInfo(type, addr, addr2, houseId, Constant.STATUS_VALID);
        if (list2 != null && list2.size() > 0) {
            throw JudicialRuntimeException.build("judicial.case.choose.estate.error.estate.with.trade", StringUtils.join(addr, addr2, houseId));
        }

    }
}
