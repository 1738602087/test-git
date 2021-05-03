/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.List;

import com.amazonaws.services.rekognition.model.FaceMatch;

import cn.repeatlink.module.judicial.vo.face.FaceInfo;

/**
 * @author LAI
 * @date 2020-09-23 11:46
 */
public interface IFaceService {

	/**
	 * 检测图像中的人脸
	 * @param base64data
	 * @return 置信度
	 */
	Float detectFace(String base64data);

	/**
	 * 将人脸添加到集合
	 * @param operaUserId
	 * @param userId
	 * @param externalImageId
	 * @param base64data
	 * @return
	 */
	FaceInfo saveFaceData(String externalImageId, String base64data);

	/**
	 * 从集合中删除人脸
	 * @param faceIds
	 * @return 已被删除的人脸ID集合
	 */
	List<String> deleteFaceData(String...faceIds);

	/**
	 * 使用图像搜索人脸
	 * @param base64data
	 * @return 匹配的人脸ID
	 */
	String searchFaceByImage(String base64data);

	/**
	 * @param base64data
	 * @return
	 */
	List<FaceMatch> searchFacesByImage(String base64data);

}
