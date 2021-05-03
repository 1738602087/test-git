/**
 * 
 */
package cn.repeatlink.module.judicial.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.repeatlink.module.judicial.event.CaseSellerVerifySuccessEvent;
import cn.repeatlink.module.judicial.event.CaseSellerVerifySuccessEvent.CaseSellerVerifyObj;
import cn.repeatlink.module.judicial.service.IUserFaceImgService;
import lombok.extern.log4j.Log4j2;

/**
 * 案件卖主颜情报验证通过事件监听器<br>
 * <br>
 * 主要用于保存相关图片<br>
 * @author LAI
 * @date 2020-11-05 13:49
 */

@Log4j2
@Component
public class CaseSellerVerifySuccessListener implements ApplicationListener<CaseSellerVerifySuccessEvent> {
	
	@Autowired
	private IUserFaceImgService userFaceImgService;

	@Override
	public void onApplicationEvent(CaseSellerVerifySuccessEvent event) {
		CaseSellerVerifyObj obj = event.getSource();
		// System.out.println(JSONUtil.toJsonPrettyStr(obj));
		this.userFaceImgService.store(obj.getCaseId(), obj.getSellerId(), obj.getImgBase64Data());
		log.info("卖主人脸验证图片已储存");
	}

}
