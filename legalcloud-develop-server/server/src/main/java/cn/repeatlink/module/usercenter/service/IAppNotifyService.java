/**
 * 
 */
package cn.repeatlink.module.usercenter.service;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.module.judicial.vo.notify.NotifyDetailInfo;
import cn.repeatlink.module.judicial.vo.notify.NotifyRowInfo;

/**
 * @author LAI
 * @date 2020-11-19 14:05
 */
public interface IAppNotifyService {
	
	/**
	 * @param vo
	 * @return
	 */
	PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> getPageList(String userId, PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> vo);

	/**
	 * @param notifyId
	 * @return
	 */
	NotifyDetailInfo getNotifyDetail(String notifyId);

	/**
	 * 标记某条通知为已读
	 * @param userId
	 * @param notifyId
	 */
	void markReaded(String userId, String notifyId);

	/**
	 * 标记用户所有未读为已读
	 * @param userId
	 */
	void markAllReaded(String userId);

	/**
	 * 新增通知
	 * @param operaUserId
	 * @param senderId
	 * @param targetId
	 * @param targetType
	 * @param title
	 * @param content
	 */
	void addNotify(String operaUserId, String senderId, String targetId, int targetType, String title, String content);

	/**
	 * @param userId
	 * @return
	 */
	int getUnreadCount(String userId);

}
