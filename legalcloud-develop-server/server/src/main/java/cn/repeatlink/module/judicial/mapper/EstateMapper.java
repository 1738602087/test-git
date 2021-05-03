/**
 * 
 */
package cn.repeatlink.module.judicial.mapper;

import java.util.List;

import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.common.entity.EstateUser;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;
import cn.repeatlink.module.judicial.vo.user.SaleUserInfo;

/**
 * @author LAI
 * @date 2020-09-24 10:27
 */
public interface EstateMapper {
	
	List<EstateUser> getEstateUserByEstateId(String estateId);
	
	List<EstateUserInfo> findEstateListByUserId(String userId);
	
	List<EstateUserInfo> findEstateList(EstateUserInfo info);
	
	List<SaleUserInfo> findEstateUserListByEstateId(String estateId);
	
	List<EstateInfo> getEstateByInfo(String type, String addr, String addr2, String houseId, Short status);

}
