/**
 * 
 */
package cn.repeatlink.module.judicial.service;

/**
 * @author LAI
 * @date 2020-12-01 13:52
 */
public interface IUserFaceImgService {

	/**
	 * @param caseId
	 * @param userId
	 * @param imgbase64data
	 */
	void store(String caseId, String userId, String imgbase64data);

}
