/**
 * 
 */
package cn.repeatlink.module.usercenter.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;

import cn.hutool.core.date.DateUtil;
import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.common.entity.AppNotify;
import cn.repeatlink.common.mapper.AppNotifyMapper;
import cn.repeatlink.framework.common.Constant.PushRegistrationType;
import cn.repeatlink.framework.service.IPushService;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.judicial.mapper.NotifyMapper;
import cn.repeatlink.module.judicial.vo.notify.NotifyDetailInfo;
import cn.repeatlink.module.judicial.vo.notify.NotifyRowInfo;
import cn.repeatlink.module.usercenter.common.Constant.NotifyStatus;
import cn.repeatlink.module.usercenter.service.IAppNotifyService;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-11-19 14:05
 */

@Log4j2
@Service
public class AppNotifyServiceImpl implements IAppNotifyService {
	
	@Autowired
	private AppNotifyMapper appNotifyMapper;
	
	@Autowired
	private NotifyMapper notifyMapper;

	@Autowired
	private IPushService pushService;
	
	@Override
	public PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> getPageList(String userId, PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> vo) {
		List<NotifyRowInfo> list = this.notifyMapper.findPageList(userId, vo);
		PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> page = new PageLoadMoreVo<>();
		page.setSearch_vo(vo.getSearch_vo());
		page.setDatas(list);
		if(list != null && list.size() > 0) {
			page.setId(list.get(list.size() - 1).getNotify_id());
			page.setTime(DateUtil.formatDateTime(list.get(list.size() - 1).getSend_time()));
		}
		
		return page;
	}

	@Override
	public NotifyDetailInfo getNotifyDetail(String notifyId) {
		NotifyDetailInfo info = this.notifyMapper.getNotifyDetail(notifyId);
		return info;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public void addNotify(String operaUserId, String senderId, String targetId, int targetType, String title, String content) {
		try {
			AppNotify notify = new AppNotify();
			notify.setNotifyId(IDUtil.nextID());
			notify.setReceiverId(targetId);
			notify.setSenderId(senderId);
			notify.setTitle(title);
			notify.setContent(content);
			notify.setSendTime(new Date());
			notify.setCreatedBy(operaUserId);
			notify.setCreateTime(notify.getSendTime());
			notify.setStatus(NotifyStatus.UNREAD);
			this.appNotifyMapper.insert(notify);
		} catch (Exception e) {
			log.error("add notify error", e);
			// 数据未保存，不推送
			return;
		}
		try {
			if(targetType == PushRegistrationType.JUD) {
				this.pushService.PushJudicialNotification(targetId,content,"sys",title);
			} else if(targetType == PushRegistrationType.GEN) {
				this.pushService.PushGeneralNotification(targetId,content,"sys",title);
			}
		} catch (Exception e) {
			log.error("push notify error, target="+ targetId + ", type=" + targetType + ", errorMsg=" + (e == null ? "null" : e.getMessage()));
			// 不再向外抛出异常，打印日志即可
		}
		
	}
	
	@Override
	public void markReaded(String userId, String notifyId) {
		AppNotify notify = this.appNotifyMapper.selectByPrimaryKey(notifyId);
		if(notify != null && notify.getReceiverId() != null && notify.getReceiverId().equals(userId)) {
			if(NotifyStatus.UNREAD.equals(notify.getStatus())) {
				notify.setStatus(NotifyStatus.READED);
				notify.setUpdatedBy(userId);
				notify.setUpdateTime(new Date());
				this.appNotifyMapper.updateByPrimaryKey(notify);
			}
		}
	}
	
	@Override
	public void markAllReaded(String userId) {
		Db.update("update app_notify set `status` = ?, updated_by = ?, update_time = ? where receiver_id = ? and `status` = ? "
				, NotifyStatus.READED, userId, new Date(), userId, NotifyStatus.UNREAD);
	}
	
	@Override
	public int getUnreadCount(String userId) {
		Integer count = Db.queryInt("select count(*) from app_notify where receiver_id = ? and `status` = ? ", userId, NotifyStatus.UNREAD);
		return count == null ? 0 : count;
	}

}
