/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.util.List;

import cn.repeatlink.module.judicial.vo.estate.EstateAddrTagVo;

/**
 * @author LAI
 * @date 2020-12-08 10:33
 */
public interface IEstateAddrService {

	/**
	 * @return
	 */
	List<EstateAddrTagVo> getAllAreas();

	/**
	 * @param code
	 * @param text
	 * @param fulltext
	 * @return
	 */
	List<EstateAddrTagVo> getNext(String code, String text, String fulltext);

}
