package cn.repeatlink.module.law.scheduler;

import org.springframework.stereotype.Component;

import cn.repeatlink.framework.scheduler.Job;
import cn.repeatlink.module.law.scheduler.updatepostcode.UpdatePostCodePeriod;

@Component
public class UpdatePostCode implements Job {

	@Override
	public String exec() {
		UpdatePostCodePeriod upcd = new UpdatePostCodePeriod();
		boolean result = upcd.excute();
		if(result) {
			return "success";
		} else {
			return "fail";
		}
	}

}
