/**
 * 
 */
package cn.repeatlink.framework.scheduler;

import org.springframework.stereotype.Component;

/**
 * @author LAI
 * @date 2020-08-25 10:03
 */

@Component
public class TestJob implements Job {

	@Override
	public String exec() {
		System.out.println("TestJob.exec()");
		return "success";
	}

}
