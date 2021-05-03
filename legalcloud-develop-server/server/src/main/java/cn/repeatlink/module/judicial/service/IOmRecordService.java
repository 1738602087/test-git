/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import cn.repeatlink.common.entity.OmDeductionRecord;

/**
 * @author LAI
 * @date 2020-10-29 11:34
 */
public interface IOmRecordService {

	/**
	 * @param customerId
	 * @param type
	 * @param orderNo
	 * @param amount
	 * 
	 * @return recordId
	 */
	String createRecord(String customerId, Short type, String orderNo, Long amount, String desc);

	/**
	 * @param recordId
	 */
	boolean deductRecord(String recordId);

	/**
	 * @param recordId
	 * @return
	 */
	OmDeductionRecord getRecord(String recordId);

}
