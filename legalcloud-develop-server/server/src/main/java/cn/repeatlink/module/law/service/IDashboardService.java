/**
 * 
 */
package cn.repeatlink.module.law.service;

import java.util.List;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.SeachVo;
import cn.repeatlink.module.law.vo.cases.CaseInfo;
import cn.repeatlink.module.law.vo.dash.DashInfoVo;
import cn.repeatlink.module.law.vo.dash.LawGroupItemVo;
import cn.repeatlink.module.law.vo.dash.SaleRecordInfo;
import cn.repeatlink.module.law.vo.user.GeneralInfo;

/**
 * @author LAI
 * @date 2020-10-12 15:53
 */
public interface IDashboardService {

	/**
	 * @param userInfo
	 * @return
	 */
	DashInfoVo getNewNumberAndPercent(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @return
	 */
	List<Kv> getNewNumberTran(LawLoginUserInfo userInfo, String type);

	/**
	 * @param userInfo
	 * @return
	 */
	DashInfoVo getProcessCaseCountAndPercent(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @return
	 */
	String getProcessCaseCompletePercent(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @return
	 */
	List<Kv> getCaseCompletedRank(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @return
	 */
	DashInfoVo getSaleCountAndPercent(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @param type
	 * @return
	 */
	List<Kv> getSaleTran(LawLoginUserInfo userInfo, String type);

	/**
	 *
	 * @param vo
	 * @return
	 */
    List<GeneralInfo> getGeneralInfos(LawLoginUserInfo userInfo, SeachVo date);

	/**
	 *
	 * @param vo
	 * @return
	 */
	List<CaseInfo> getCaseInfos(LawLoginUserInfo userInfo, SeachVo data);

	List<SaleRecordInfo> getServerInfos(LawLoginUserInfo userInfo, SeachVo data);

	/**
	 * @param userInfo
	 * @return
	 */
	DashInfoVo getLawGroupDashInfo(LawLoginUserInfo userInfo);

	/**
	 * @param userInfo
	 * @param type
	 * @return
	 */
	List<Kv> getLawNumberTran(LawLoginUserInfo userInfo, String type);

	/**
	 * @param userInfo
	 * @param date
	 * @return
	 */
	List<LawGroupItemVo> getLawGorupInfos(LawLoginUserInfo userInfo, SeachVo date);
}
