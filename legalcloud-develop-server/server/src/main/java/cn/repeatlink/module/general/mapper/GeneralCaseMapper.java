/**
 * 
 */
package cn.repeatlink.module.general.mapper;

import java.util.List;

import cn.repeatlink.module.general.vo.main.CaseInfo;

/**
 * @author LAI
 * @date 2020-10-23 13:10
 */
public interface GeneralCaseMapper {

	List<CaseInfo> getProcessCaseList(String userId);
	
}
