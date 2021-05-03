/**
 * 
 */
package cn.repeatlink.module.judicial.event;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-11-05 13:28
 */
public class CaseSellerVerifySuccessEvent extends CaseEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public CaseSellerVerifySuccessEvent(CaseSellerVerifyObj source) {
		super(source);
	}
	
	@Override
	public CaseSellerVerifyObj getSource() {
		return (CaseSellerVerifyObj)super.getSource();
	}
	

	@Data
	@Accessors(chain = true)
	public static class CaseSellerVerifyObj {
		
		private String caseId;
		
		private String sellerId;
		
		private String imgBase64Data;
		
	}

}
