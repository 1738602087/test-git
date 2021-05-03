/**
 * 
 */
package cn.repeatlink.module.judicial.listener;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.framework.common.Constant.PushRegistrationType;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.judicial.event.CaseDealFinishedEvent;
import cn.repeatlink.module.judicial.event.CaseEvent.CaseBaseObj;
import cn.repeatlink.module.usercenter.service.IAppNotifyService;
import lombok.extern.log4j.Log4j2;

/**
 * 案件取引完了事件监听器（异步处理）<br>
 * <br>
 * 房产信息读取
 * @author LAI
 * @date 2020-11-05 17:05
 */

@Log4j2
@Component
public class CaseDealFinishedListener implements ApplicationListener<CaseDealFinishedEvent> {
	
	@Autowired
	private IAppNotifyService appNotifyService;
	
	@Autowired
	private IGeneralUserService generalUserService;
	
	@Async
	@Override
	public void onApplicationEvent(CaseDealFinishedEvent event) {
		CaseBaseObj source = event.getSource();
		// 消息通知
		
		// 发送给司法书士
		String title = MessageUtil.getMessage("judicial.case.notify.deal.title");
		String content = MessageUtil.getMessage("judicial.case.notify.deal.content");
		Record r = Db.findFirst("select case_name, user_judicial_id from case_estate where case_id=?", source.getCaseId());
		String judicialUserId = r.getStr("user_judicial_id");
		title = StrUtil.indexedFormat(title, r.getStr("case_name"));
		content = StrUtil.indexedFormat(content, r.getStr("case_name"), DateUtil.formatDateTime(new Date()));
		try {
			this.appNotifyService.addNotify(judicialUserId, judicialUserId, judicialUserId, PushRegistrationType.JUD, title, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 发送给买主
		List<Record> users = Db.find("select user_general_id from case_buyer where case_id = ? and `status` = ? ", source.getCaseId(), Constant.STATUS_VALID);
		if(users != null) {
			for (Record user : users) {
				String generalUserId = user.getStr("user_general_id");
				try {
					this.appNotifyService.addNotify(judicialUserId, judicialUserId, generalUserId, PushRegistrationType.GEN, title, content);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					this.generalUserService.setOrSkipJudicialAndLawGroup(generalUserId, judicialUserId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

}
