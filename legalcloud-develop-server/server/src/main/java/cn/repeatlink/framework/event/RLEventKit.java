/**
 * 
 */
package cn.repeatlink.framework.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.util.SpringBeanFactory;

/**
 * @author LAI
 * @date 2020-11-05 13:39
 * 事件工具
 */

@Component
public class RLEventKit {
	
	@Autowired
    private ApplicationEventPublisher publisher;
	
	private static RLEventKit instance = null;
    
    private static RLEventKit getInstance() {
    	if(instance == null) {
    		instance = SpringBeanFactory.getBean(RLEventKit.class);
    	}
    	return instance;
    }
    
    public static void publish(RLBaseEvent event) {
    	getInstance().publisher.publishEvent(event);
    }
	
}
