/**
 * 
 */
package cn.repeatlink.module.judicial.listener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.CaseEstateRecord;
import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.common.mapper.CaseEstateRecordMapper;
import cn.repeatlink.common.mapper.EstateInfoMapper;
import cn.repeatlink.framework.common.Constant.PushRegistrationType;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.framework.util.SysConfigUtil;
import cn.repeatlink.module.judicial.common.Constant.CaseStepVal;
import cn.repeatlink.module.judicial.common.Constant.EstateFetchStatus;
import cn.repeatlink.module.judicial.common.Define.ConfigKeys;
import cn.repeatlink.module.judicial.event.CaseEvent.CaseBaseObj;
import cn.repeatlink.module.judicial.event.CaseRegFinishedEvent;
import cn.repeatlink.module.judicial.service.IEstateCaseService;
import cn.repeatlink.module.judicial.service.IEstateSpiderService;
import cn.repeatlink.module.judicial.service.impl.EstateSpiderServiceImpl.EsateCaseInfo;
import cn.repeatlink.module.judicial.util.EstateInfoUtil;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateSpiderResultVo;
import cn.repeatlink.module.usercenter.service.IAppNotifyService;
import lombok.extern.log4j.Log4j2;

/**
 * ???????????????????????????????????????????????????<br>
 * <br>
 * ??????????????????
 * @author LAI
 * @date 2020-11-05 17:05
 */

@Log4j2
@Component
public class CaseRegFinishedListener implements ApplicationListener<CaseRegFinishedEvent> {

	@Autowired
	private IEstateCaseService estateCaseService;
	
	@Autowired
	private CaseEstateRecordMapper caseEstateRecordMapper;
	
	@Autowired
	private EstateInfoMapper estateInfoMapper;

	@Autowired
	private IAppNotifyService appNotifyService;
	
	@Autowired
	private IEstateSpiderService estateSpiderService;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Async
	@Override
	public void onApplicationEvent(CaseRegFinishedEvent event) {
		CaseBaseObj source = event.getSource();

		List<EstateBaseVo> list = this.estateCaseService.getCaseEstateRecordInfo(source.getCaseId());
		if(list != null) {
			// B: fetch
			log.info("??????API????????????, ??????ID = " + source.getCaseId());
			List<String> fetchOkList = new ArrayList<>();
			for (EstateBaseVo estateBaseVo : list) {
				try {
					String estateId = estateBaseVo.getEstate_id();
					String houseId = estateBaseVo.getHouse_id();
					String addr = estateBaseVo.getAddr();
					String addr2 = estateBaseVo.getAddr2();
					String type = estateBaseVo.getType();
					log.info("fetch start ?????????record_id = {}, estate_id = {}, type = {}, addr = {}, addr2 = {}, house_id = {}", estateBaseVo.getRecord_id(), estateId, type, addr, addr2, houseId);
					EstateSpiderResultVo fetchVo = this.fetch(estateBaseVo);
					log.info("fetch end   ?????????record_id = {}, result = {}", estateBaseVo.getRecord_id(), fetchVo == null ? "null" : JSONUtil.toJsonStr(fetchVo));
					Date nowtime = new Date();
					// ???????????????????????????
					CaseEstateRecord record = this.caseEstateRecordMapper.selectByPrimaryKey(estateBaseVo.getRecord_id());
					record.setUpdateTime(nowtime);
					record.setEstateNo(fetchVo.getEstateNo());
					record.setCategory(fetchVo.getCategory());
					record.setArea(fetchVo.getArea());
					record.setStruct(fetchVo.getStruct());
					record.setRecord(fetchVo.getRecord());
					// ?????????
					if(StringUtils.isNotBlank(fetchVo.getErrorMsg())) {
						record.setFetchStatus(EstateFetchStatus.FETCH_ERROR);
						record.setFetchMsg(fetchVo.getErrorMsg());
					} 
					// ??????fetch
					else {
						record.setFetchStatus(EstateFetchStatus.FETCH_OK);
						fetchOkList.add(record.getRecordId());
					}
					this.caseEstateRecordMapper.updateByPrimaryKey(record);
					
					// ??????????????????
					if(StringUtils.isNotBlank(record.getEstateId())) {
						EstateInfo info = this.estateInfoMapper.selectByPrimaryKey(record.getEstateId());
						info.setEstateNo(fetchVo.getEstateNo());
						info.setCategory(fetchVo.getCategory());
						info.setArea(fetchVo.getArea());
						info.setStruct(fetchVo.getStruct());
						info.setRecord(fetchVo.getRecord());
						try {
							if(fetchVo.getPdfFile() != null && fetchVo.getPdfFile().exists() && fetchVo.getPdfFile().isFile()) {
								info.setPdfPath(fetchVo.getPdfFile().getAbsolutePath());
							}
						} catch (Exception e) {
							log.error("?????????????????????PDF????????????", e);
						}
						info.setUpdateTime(nowtime);
						this.estateInfoMapper.updateByPrimaryKey(info);
					}
					
					// 2021-02-24
					// ?????????????????????
					try {
						// PDF????????????
						if(fetchVo.getPdfFile() != null) {
							String holder = fetchVo.getHolder();
							// ????????????????????????????????????????????????????????????????????????
							this.compareHolderAndBuyer(source.getCaseId(), holder);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
	
				} catch (Exception e) {
					log.error("????????????????????????record_id = " + estateBaseVo.getRecord_id(), e);
				}
			}
			
			// ?????????????????????fetch?????????
			// ?????????????????????????????????fetch
			Short caseFetchApi = CaseStepVal.INCLUDE_ERROR;
			if(fetchOkList.size() == list.size()) {
				caseFetchApi = CaseStepVal.COMPLETED;
			}
			try {
				Db.update("update case_estate set fetch_api =? where case_id = ?", caseFetchApi, source.getCaseId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// C: fetch??????
			// A: ??????????????????
			try {
				// ?????????????????????
				String title = MessageUtil.getMessage("judicial.case.notify.reg.title");
				String content = MessageUtil.getMessage("judicial.case.notify.reg.content");
				Record r = Db.findFirst("select case_name, user_judicial_id from case_estate where case_id=?", source.getCaseId());
				String userId = r.getStr("user_judicial_id");
				title = StrUtil.indexedFormat(title, r.getStr("case_name"));
				content = StrUtil.indexedFormat(content, r.getStr("case_name"), DateUtil.formatDateTime(new Date()));
				this.appNotifyService.addNotify(userId, userId, userId, PushRegistrationType.JUD, title, content);

				// ???????????????
				List<Record> users = Db.find("select user_general_id from case_buyer where case_id = ? and `status` = ? ", source.getCaseId(), Constant.STATUS_VALID);
				if(users != null) {
					for (Record user : users) {
						this.appNotifyService.addNotify(userId, userId, user.getStr("user_general_id"), PushRegistrationType.GEN, title, content);
					}
				}
			} catch (Exception e) {
				log.error("??????????????????", e);
			}
			
		}
	}

	private EstateSpiderResultVo fetch(EstateBaseVo baseVo) {
		// fetch??????
		Boolean switchFetch = SysConfigUtil.instance().getValue(ConfigKeys.ESTATE_SPIDER_FETCH_SWITCH, false);
		// ????????????demo??????
		if(!Boolean.TRUE.equals(switchFetch)) {
			return fetchFromDemo(baseVo);
		}
		// 2021-01-05
		EstateSpiderResultVo vo = new EstateSpiderResultVo();
		// fetch??????
		try {
			vo = this.estateSpiderService.fetch(baseVo);
		} catch (Exception e) {
			vo.setErrorMsg("fetch error, " + e.getMessage());
		}
		// ??????????????? ??????
		if(StringUtils.isNotBlank(vo.getErrorMsg())) {
			vo.setEstateNo("??????");
			vo.setCategory("??????");
			vo.setArea("??????");
			vo.setStruct("??????");
			vo.setRecord("??????");
		}
		return vo;
	}
	
	private EstateSpiderResultVo fetchFromDemo(EstateBaseVo baseVo) {
		EstateSpiderResultVo vo = null;
		InputStream inputStream = null;
		try {
			log.info("?????????fetch???????????????demo??????");
			EsateCaseInfo info = this.estateSpiderService.buildInfo(baseVo);
			inputStream = this.getClass().getResourceAsStream("/demo/estate_pdf/" + (buildDemoPdfIndex(baseVo.getHouse_id())) + ".pdf");
			String fullFilePath = this.estateSpiderService.getPdfStoreRootFolder() + "/" + DateUtil.format(info.getCaseStartDate(), "yyyyMMdd") + "/case_" + info.getCaseId()
				+ "/" + baseVo.getAddr() + StringUtils.trimToEmpty(baseVo.getHouse_id()) + "_" + DateUtil.format(new Date(), "yyyyMMdd")
				+ RandomUtil.randomNumbers(8) + ".pdf";
			File pdfFile = FileUtil.writeFromStream(inputStream, fullFilePath);
			vo = this.estateSpiderService.parseEstatePdf(pdfFile);
			vo.setPdfFile(pdfFile);
		} catch (Exception e) {
			e.printStackTrace();
			vo = new EstateSpiderResultVo();
			vo.setEstateNo("??????");
			vo.setCategory("??????");
			vo.setArea("??????");
			vo.setStruct("??????");
			vo.setRecord("??????");
		} finally {
			if(inputStream != null) {
				IoUtil.close(inputStream);
			}
		}
		return vo;
	}
	
	// ?????????????????????????????????????????????demo???????????????????????????
	private int buildDemoPdfIndex(String houseId) {
		// ????????????
		final int maxIndex = 4;
		int index = 0;
		try {
			String str = Convert.toDBC(houseId);
			index = Integer.valueOf(StringUtils.right(str.trim(), 1));
			index = 1 + ((index - 1) % maxIndex);
		} catch (Exception e) {
			// 
		} finally {
			if(index < 1 || index > maxIndex) {
				index = RandomUtil.randomInt(1, maxIndex + 1);
			}
		}
		return index;
	}
	
	private void compareHolderAndBuyer(String caseId, String holder) {
		// ??????????????????
		List<Record> buyerList = Db.find("SELECT fullname, fullname_kana FROM case_buyer WHERE case_id = ? AND `status` = 1", caseId);
		if(buyerList != null) {
			// ??????????????????????????????????????????
			Kv kv = Kv.create();
			List<String> fullnameList = new ArrayList<>();
			for (Record record : buyerList) {
				String fullname = EstateInfoUtil.replaceBlank(StringUtils.trimToEmpty(record.getStr("fullname")));
				String fullnameKana = EstateInfoUtil.replaceBlank(StringUtils.trimToEmpty(record.getStr("fullname_kana")));
				fullnameList.add(fullname);
				String[] names = { fullname, fullnameKana };
				boolean match = false;
				for (String name : names) {
					boolean result = this.compareHolderName(name, holder);
					if(result) {
						match = true;
						break;
					}
				}
				
				// ????????????
				if(!match) {
					kv.set(fullname, true);
				}
			}
			
			// ????????????????????? ??? ????????? ????????????????????????
			if(!kv.isEmpty()) {
				Record r = Db.findFirst("select case_name, user_judicial_id from case_estate where case_id=?", caseId);
				// ????????????ID
				String userId = r.getStr("user_judicial_id");
				// 
				String title = MessageUtil.getMessage("judicial.case.notify.pdf.check.title");
				String content = MessageUtil.getMessage("judicial.case.notify.pdf.check.content");
//				title = StrUtil.indexedFormat(title, r.getStr("case_name"));
				content = StrUtil.indexedFormat(content, r.getStr("case_name"), StringUtils.join(fullnameList, "???"), holder);
				this.appNotifyService.addNotify(userId, userId, userId, PushRegistrationType.JUD, title, content);
			}
		}
		
	}
	
	private boolean compareHolderName(String buyerName, String holder) {
		boolean result = false;
		// ????????????
		if(StringUtils.containsIgnoreCase(holder, buyerName)) {
			result = true;
		}
		
		return result;
	}
	
}
