/**
 * 
 */
package cn.repeatlink.framework.event;

import org.springframework.context.ApplicationEvent;

/**
 *  应用基本事件抽象类
 * @author LAI
 * @date 2020-11-05 13:30
 */
public abstract class RLBaseEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public RLBaseEvent(Object source) {
		super(source);
	}

}
