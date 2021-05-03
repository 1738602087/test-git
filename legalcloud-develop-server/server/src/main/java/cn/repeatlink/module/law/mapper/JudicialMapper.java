/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import java.util.List;

import cn.repeatlink.module.law.vo.user.JudicialUserApplyVo;
import cn.repeatlink.module.law.vo.user.JudicialUserVo;

/**
 * @author LAI
 * @date 2020-10-14 17:32
 */
public interface JudicialMapper {
	
	List<JudicialUserVo> search(String groupId, JudicialUserVo vo);
	
	List<JudicialUserApplyVo> searchApply(String groupId, JudicialUserApplyVo vo);

}
