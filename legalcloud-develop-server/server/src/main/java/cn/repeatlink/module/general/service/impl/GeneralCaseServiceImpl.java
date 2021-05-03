/**
 * 
 */
package cn.repeatlink.module.general.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.common.Constant;
import cn.repeatlink.module.general.mapper.GeneralCaseMapper;
import cn.repeatlink.module.general.service.IGeneralCaseService;
import cn.repeatlink.module.general.vo.main.CaseInfo;
import cn.repeatlink.module.judicial.common.Constant.UserFaceStatus;

/**
 * @author LAI
 * @date 2020-10-23 10:05
 */

@Service
public class GeneralCaseServiceImpl implements IGeneralCaseService {
	
	@Autowired
	private GeneralCaseMapper generalCaseMapper;
	
	@Override
	public List<CaseInfo> getProcessCaseList(String userId) {
		return this.generalCaseMapper.getProcessCaseList(userId);
	}
	
	@Override
	public Integer getUserCaseStatus(String userId, List<CaseInfo> notConfirmCaseList) {
		Integer status = 99;
		String sql = " SELECT "
				+ "	ce.* "
				+ " FROM "
				+ "	( SELECT * FROM case_buyer WHERE user_general_id = ? AND `status` = 1 ) b "
				+ "	LEFT JOIN case_estate ce ON ce.case_id = b.case_id  "
				+ " WHERE "
				+ "	ce.`status` = 1  "
				+ " ORDER BY "
				+ "	ce.create_time ASC  "
				+ "	LIMIT 1 ";
		Record r = Db.findFirst(sql, userId);
		String firstCaseId = r == null ? null : r.getStr("case_id");
		if(StringUtils.isNotBlank(firstCaseId)) {
			status = 1;
			List<?> faceList = Db.find("select id from user_general_face where user_general_id=? and `status`=? ", userId, UserFaceStatus.VALID);
			if(faceList != null && faceList.size() > 0) {
				status = 2;
			}
			Short confirm = Db.queryShort("select fetch_confirm from case_buyer where case_id=? and user_general_id=? and `status`=? "
					, firstCaseId, userId, Constant.STATUS_VALID);
			if(Constant.ENABLE.equals(confirm)) {
				status = 99;
			}
		} else {
			status = 0;
		}
		return status;
	}
	
	@Override
	public void confirm(String userId, String caseId) {
		Db.update("update case_buyer set fetch_confirm = ?, updated_by=?, update_time=? where case_id=? and user_general_id=? and `status`=? ", 
				Constant.ENABLE, userId, new Date(), caseId, userId, Constant.STATUS_VALID);
	}

}
