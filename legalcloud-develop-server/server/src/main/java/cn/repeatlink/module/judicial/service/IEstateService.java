/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.io.File;
import java.util.List;

import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;

/**
 * @author LAI
 * @date 2020-09-24 10:24
 */
public interface IEstateService {

	/**
	 * 通过人脸ID检索房产信息
	 * @param faceId
	 * @return
	 */
	List<EstateUserInfo> getEstateInfoListByUserFaceId(String faceId);

	/**
	 * @param userId
	 * @return
	 */
	List<EstateUserInfo> getEstateInfoListByUserId(String userId);

	/**
	 * @param 
	 * @return
	 */
	List<EstateUserInfo> getEstateInfoList(EstateUserInfo info);

	/**
	 * @param estateId
	 * @return
	 */
	EstateUserInfo getEstateInfo(String estateId);

	/**
	 * @param operUserId
	 * @param estateId
	 */
	void unbindOwner(String operUserId, String estateId);

	/**
	 * @param operUserId
	 * @param estateId
	 * @param userIdList
	 */
	void bindOwner(String operUserId, String estateId, List<String> userIdList);

	/**
	 * @param operaUserId
	 * @param vo
	 * @return
	 */
	EstateInfo createEstate(String operaUserId, EstateBaseVo vo);

	/**
	 * @param estateId
	 * @return
	 */
	File getEstatePdfFile(String estateId);

}
