/**
 * 
 */
package cn.repeatlink.module.general.mapper;

import java.util.List;

import cn.repeatlink.module.general.vo.main.EstateInfo;
import cn.repeatlink.module.general.vo.main.EstateUserInfo;

/**
 * @author LAI
 * @date 2020-10-23 13:24
 */
public interface GeneralEstateMapper {

	List<EstateInfo> getEstateListByUserId(String userId);
	
	List<EstateUserInfo> getEstateUserByEstateId(String estateId);
	
}
