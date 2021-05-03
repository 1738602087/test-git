/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import java.util.List;

import cn.repeatlink.module.law.vo.cases.CaseItemVo;
import cn.repeatlink.module.law.vo.cases.ReqCaseSearchVo;

/**
 * @author LAI
 * @date 2020-10-13 14:52
 */
public interface LawCaseMapper {
	
	List<CaseItemVo> search(String groupId, String userId, Boolean finished, ReqCaseSearchVo vo);

}
