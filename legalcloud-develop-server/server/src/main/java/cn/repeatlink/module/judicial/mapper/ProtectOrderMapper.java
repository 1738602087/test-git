/**
 * 
 */
package cn.repeatlink.module.judicial.mapper;

import java.util.List;

import cn.repeatlink.common.entity.EstateProtectionOrder;

/**
 * @author LAI
 * @date 2020-12-29 10:44
 */
public interface ProtectOrderMapper {
	
	List<EstateProtectionOrder> findCustomerOrderList(String customerId, String setId, Short status);
	
	List<EstateProtectionOrder> getEstateProtectionOrderByDate(String customerId, String setId, String date, Short status);

}
