/**
 * 
 */
package cn.repeatlink.module.judicial.event;

import cn.repeatlink.framework.event.RLBaseEvent;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 案件事件基类
 * @author LAI
 * @date 2020-11-05 13:32
 */
public abstract class CaseEvent extends RLBaseEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public CaseEvent(Object source) {
		super(source);
	}
	
	@Data
	@Accessors(chain = true)
	public static class CaseBaseObj {
		
		private String caseId;
		
	}

}
