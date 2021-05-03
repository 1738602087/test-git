/**
 * 
 */
package cn.repeatlink.module.judicial.event;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 案件登记完了事件
 * @author LAI
 * @date 2020-11-05 17:01
 */
public class CaseDealFinishedEvent extends CaseEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @param source
	 */
	public CaseDealFinishedEvent(CaseBaseObj source) {
		super(source);
	}

	@Override
	public CaseBaseObj getSource() {
		return (CaseBaseObj)super.getSource();
	}

}
