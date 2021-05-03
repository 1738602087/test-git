/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;

import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.general.vo.payment.CreditCardVo;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IOmiseService;
import cn.repeatlink.module.judicial.vo.order.OmiseResultVo;
import cn.repeatlink.module.law.common.Constant.OmRecordStatus;
import co.omise.Client;
import co.omise.models.Card;
import co.omise.models.Charge;
import co.omise.models.ChargeStatus;
import co.omise.models.Customer;
import co.omise.models.Token;
import co.omise.requests.Request;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-10-29 11:18
 */

@Log4j2
@Service
public class OmiseServiceImpl implements IOmiseService {
	
	private Client client = null;
	
	@Override
	public OmiseResultVo deduct(String customerCode, String cardCode, Long amount, String desc, Kv metadata) {
		Client client = this.getOmiseClient();
		try {
			Request<Charge> request = new Charge.CreateRequestBuilder()
					.amount(amount)
					.customer(customerCode)
					.card(cardCode)
					.currency("JPY")
					.description(desc)
					.metadata(this.formatMetadata(metadata))
					.capture(true)
					.build();
			Charge charge = client.sendRequest(request);
			if(charge != null && StringUtils.isNotBlank(charge.getId())) {
				OmiseResultVo resultVo = new OmiseResultVo();
				resultVo.setAmount(charge.getAmount());
				resultVo.setDeduction_date(charge.getPaidAt() == null ? null : charge.getPaidAt().toDate());
				resultVo.setDeduction_time(resultVo.getDeduction_date());
				resultVo.setDeduction_orderno(charge.getId());
				resultVo.setDeduction_error_code(charge.getFailureCode());
				resultVo.setDeduction_error_msg(charge.getFailureMessage());
				resultVo.setOrigin_deduction_orderno(charge.getTransaction());
				resultVo.setStatus(ChargeStatus.Successful.equals(charge.getStatus()) ? OmRecordStatus.SUCCESS : (ChargeStatus.Pending.equals(charge.getStatus()) ? OmRecordStatus.REQ_OM : OmRecordStatus.FAIL));
				
				return resultVo;
			}
		} catch (Exception e) {
			log.error("Omise请求异常", e);
		}
		
		return null;
	}
	
	@Override
	public CreditCardVo getCard(String tokenId) {
		try {
			Client client = this.getOmiseClient();
			Request<Token> request = new Token.GetRequestBuilder(tokenId).build();
			Token token = client.sendRequest(request);
			Card card = token.getCard();
			if(card != null) {
				CreditCardVo vo = new CreditCardVo();
				vo.setCard_code(card.getId());
				vo.setCard_no(card.getLastDigits());
				vo.setCompany_name(card.getBrand());
				vo.setHolder_name(card.getName());
				vo.setValid_date(card.getExpirationYear() + "" + card.getExpirationMonth());
				return vo;
			}
		} catch (Exception e) {
			throw JudicialRuntimeException.build("general.user.credit.card.bind.error.save.fail");
		}
		
		return null;
	}

	@Override
	public CreditCardVo bindCard(String email, String customerCode, String tokenId, String desc, Kv metadata) {
		try {
			Client client = this.getOmiseClient();
			Request<Token> request = new Token.GetRequestBuilder(tokenId).build();
			Token token = client.sendRequest(request);
			Card card = token.getCard();
			Customer customer = null;
			
			if(StringUtils.isBlank(customerCode)) {
				Request<Customer> request2 = new Customer.CreateRequestBuilder()
						.card(tokenId)
						.metadata(metadata)
						.description(desc)
						.email(email)
						.build();
				customer = client.sendRequest(request2);
			} else {
				Request<Customer> request2 = new Customer.UpdateRequestBuilder(customerCode)
						.card(tokenId)
						.metadata(metadata)
						.description(desc)
						.email(email)
						.build();
				customer = client.sendRequest(request2);
			}
			
			if(card != null && StringUtils.isNotBlank(card.getId()) && customer != null && StringUtils.isNotBlank(customer.getId())) {
				CreditCardVo vo = new CreditCardVo();
				vo.setCard_code(card.getId());
				vo.setCard_no(card.getLastDigits());
				vo.setCompany_name(card.getBrand());
				vo.setCustomer_code(customer.getId());
				vo.setHolder_name(card.getName());
				vo.setValid_date(card.getExpirationYear() + "" + card.getExpirationMonth());
				
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw JudicialRuntimeException.build("general.user.credit.card.bind.error.save.fail");
		}
		
		return null;
	}
	
	@Override
	public void unbindCard(String customerCode, String cardCode) {
		try {
			Client client = this.getOmiseClient();
			Request<Card> request = new Card.DeleteRequestBuilder(customerCode, cardCode).build();
			Card card = client.sendRequest(request);
			if(card != null) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw JudicialRuntimeException.build("general.user.credit.card.unbind.error.save.fail");
	}
	
	
	private Client getOmiseClient() {
		if(this.client == null) {
			try {
				this.client = new Client.Builder().publicKey(this.getOmisePublicKey()).secretKey(this.getOmiseSecretKey()).build();
			} catch (Exception e) {
				log.error("创建Omise客户端失败", e);
				throw new RuntimeException(e);
			}
		}
		return this.client;
	}

	private String getOmiseSecretKey() {
		return SysConfigCacheUtil.instance().getValue(Define.ConfigKeys.OMISE_SECRET_KEY, StringUtils.EMPTY);
	}

	@Override
	public String getOmisePublicKey() {
		return SysConfigCacheUtil.instance().getValue(Define.ConfigKeys.OMISE_PUBLIC_KEY, StringUtils.EMPTY);
	}
	
	private Map<String, Object> formatMetadata(Kv kv) {
		Map<String, Object> map = new HashMap<>();
		if(kv != null) {
			for(Object key : kv.keySet()) {
				Object value = kv.get(key);
				if(value != null) {
					map.put(key.toString(), value);
				}
			}
		}
		return map;
	}
}
