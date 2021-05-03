/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import com.jfinal.kit.Kv;

import cn.repeatlink.module.general.vo.payment.CreditCardVo;
import cn.repeatlink.module.judicial.vo.order.OmiseResultVo;

/**
 * @author LAI
 * @date 2020-10-29 11:18
 */
public interface IOmiseService {

	/**
	 * @param customerCode
	 * @param cardCode
	 * @param amount
	 * @param props
	 * @return
	 */
	OmiseResultVo deduct(String customerCode, String cardCode, Long amount, String desc, Kv metadata);

	/**
	 * @return
	 */
	String getOmisePublicKey();

	/**
	 * @param email
	 * @param customerCode
	 * @param tokenId
	 * @param desc
	 * @param metadata
	 * @return
	 */
	CreditCardVo bindCard(String email, String customerCode, String tokenId, String desc, Kv metadata);

	/**
	 * @param tokenId
	 * @return
	 */
	CreditCardVo getCard(String tokenId);

	/**
	 * @param customerCode
	 * @param cardCode
	 */
	void unbindCard(String customerCode, String cardCode);

}
