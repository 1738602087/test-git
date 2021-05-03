/**
 * 
 */
package cn.repeatlink.module.law.mapper;

import java.util.List;

import cn.repeatlink.common.entity.LawApply;
import cn.repeatlink.common.entity.LawGroup;
import cn.repeatlink.module.usercenter.vo.LawApplyItemVo;
import cn.repeatlink.module.usercenter.vo.LawGroupItemVo;
import cn.repeatlink.module.usercenter.vo.ReqLawApplySearchVo;
import cn.repeatlink.module.usercenter.vo.ReqLawGroupSearchVo;

/**
 * @author LAI
 * @date 2021-01-14 15:55
 */
public interface LawBizMapper {
	
	List<LawApplyItemVo> searchApplyList(ReqLawApplySearchVo vo);
	
	LawApply getApplyForUpdate(String applyId);
	
	List<LawGroupItemVo> searchGroupList(ReqLawGroupSearchVo vo);
	
	LawGroup getGroupForUpdate(String groupId);

}
