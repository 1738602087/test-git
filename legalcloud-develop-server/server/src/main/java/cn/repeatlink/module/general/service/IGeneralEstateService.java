/**
 * 
 */
package cn.repeatlink.module.general.service;

import java.util.List;

import cn.repeatlink.module.general.vo.main.EstateSetInfo;

/**
 * @author LAI
 * @date 2020-10-23 10:07
 */
public interface IGeneralEstateService {

	/**
	 * @param userId
	 * @return
	 */
	List<EstateSetInfo> getUserEstateList(String userId);

}
