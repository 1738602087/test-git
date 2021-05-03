/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.List;

import cn.repeatlink.module.judicial.vo.estate.FaceVo;
import cn.repeatlink.module.judicial.vo.face.FaceCodeInfo;
import cn.repeatlink.module.judicial.vo.user.FaceUserVo;

/**
 * @author LAI
 * @date 2020-09-23 11:24
 */
public interface IUserFaceService {

	/**
	 * 获取用户已登录颜情报ID集合
	 * @param userId
	 * @return
	 */
	List<String> getUserFaceIds(String userId);

	FaceVo getUserFaceInfo(String userId);

	/**
	 * 根据人脸ID得到用户ID
	 * @param faceId
	 * @return
	 */
	String getUserId(String faceId);

	/**
	 * 登录颜情报
	 * @param operaUserId
	 * @param userId
	 * @param picBase64Data
	 */
	void inputFaceData(String operaUserId, String userId, String picBase64Data);

	/**
	 * @param base64data
	 * @return
	 */
	String getUserIdByImage(String base64data);

	/**
	 * @param base64data
	 * @return
	 */
	FaceUserVo getUserByImage(String base64data);

	/**
	 * 创建人脸标识码并缓存（用于人脸识别后其他操作调用验证）
	 * @param user
	 * @return
	 */
	FaceCodeInfo buildFaceCode(FaceUserVo user);

	/**
	 * 验证人脸识别码
	 * @param userId
	 * @param use_type
	 * @param info
	 */
	FaceUserVo verifyFaceCode(String userId, String use_type, FaceCodeInfo info);

	/**
	 * @param operaUserId
	 * @param userId
	 */
	void deleteFaceByUserId(String operaUserId, String userId);

}
