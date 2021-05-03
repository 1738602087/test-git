/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import java.util.List;
import java.util.Map;

import cn.repeatlink.module.law.vo.cases.CaseInfo;
import cn.repeatlink.module.law.vo.dash.LawGroupItemVo;
import cn.repeatlink.module.law.vo.dash.SaleRecordInfo;
import cn.repeatlink.module.law.vo.user.GeneralInfo;

/**
 * @author LAI
 * @date 2020-10-12 16:05
 */
public interface DashboardMapper {
	
	Long getNewNumberByMonthByJudicial(String userId, String month);
	
	Long getNewNumberByMonthByGroup(String groupId, String month);
	
	@SuppressWarnings("rawtypes")
	List<Map> getNewNumberTranByJudicial(String userId, String startMonth, String endMonth);
	
	@SuppressWarnings("rawtypes")
	List<Map> getNewNumberTranByGroup(String groupId, String startMonth, String endMonth);
	
	Long getProcessCaseCountByMonthByJudicial(String userId, String month);
	
	Long getProcessCaseCountByMonthByGroup(String groupId, String month);
	
	
	Long getSaleCountByMonthByJudicial(String userId, String month);
	
	Long getSaleCountByMonthByGroup(String groupId, String month);

	List<GeneralInfo> getGeneralInfos(String groupId, String judicialUserId, String star, String end);

    List<CaseInfo> getCaseInfos(String groupId, String judicialUserId, String star, String end);

	List<SaleRecordInfo> getServerInfos(String star, String end);

	List<LawGroupItemVo> getLawGorupInfos(String start_time, String end_time);
}
