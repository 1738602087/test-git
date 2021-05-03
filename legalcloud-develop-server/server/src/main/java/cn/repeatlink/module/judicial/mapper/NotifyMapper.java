/**
 * 
 */
package cn.repeatlink.module.judicial.mapper;

import java.util.List;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.module.judicial.vo.notify.NotifyDetailInfo;
import cn.repeatlink.module.judicial.vo.notify.NotifyRowInfo;

/**
 * @author LAI
 * @date 2020-09-24 10:27
 */
public interface NotifyMapper {
	
	List<NotifyRowInfo> findPageList(String userId, PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> vo);
	
	NotifyDetailInfo getNotifyDetail(String notifyId);

}
