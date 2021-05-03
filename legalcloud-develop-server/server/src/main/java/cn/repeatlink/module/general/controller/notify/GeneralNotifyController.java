/**
 *
 */
package cn.repeatlink.module.general.controller.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.common.bean.PageLoadMoreVo;
import cn.repeatlink.framework.bean.ButtonVo;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.service.IPushService;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.judicial.vo.notify.NotifyDetailInfo;
import cn.repeatlink.module.judicial.vo.notify.NotifyRowInfo;
import cn.repeatlink.module.usercenter.service.IAppNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-09-11 10:20
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/notify")
@Api(value = "通知管理", tags = "通知")
@ApiSort(50)
public class GeneralNotifyController extends BaseGeneralController {

    @Autowired
    private IAppNotifyService appNotifyService;

    @Autowired
    private IPushService IPushService;

    private static final int RegistrationGenType = 2;

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "通知列表", notes = "获取通知列表")
    @PostMapping("/list")
    public AjaxResult<PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo>> list(@RequestBody PageLoadMoreVo<NotifyRowInfo, NotifyRowInfo> vo) {
        return AjaxResult.success(this.appNotifyService.getPageList(super.generalUserId(), vo));
    }

    @ApiOperationSupport(order = 20)
    @ApiOperation(value = "通知详细", notes = "获取某通知详细信息")
    @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "notify_id", value = "通知ID")
    @GetMapping("/{notify_id}")
    public AjaxResult<NotifyDetailInfo> detail(@PathVariable("notify_id") String notifyId) {
        return AjaxResult.success(this.appNotifyService.getNotifyDetail(notifyId));
    }
    
    @ApiOperationSupport(order = 21)
    @ApiOperation(value = "获取未读通知数", notes = "获取未读通知数")
    @GetMapping("/unread/count")
    public AjaxResult<Integer> getUnreadCount() {
        return AjaxResult.success(this.appNotifyService.getUnreadCount(super.generalUserId()));
    }

    @ApiOperationSupport(order = 30)
    @ApiOperation(value = "标记某条已读", notes = "标记某条已读")
    @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "notify_id", value = "通知ID")
    @PutMapping("/{notify_id}/read")
    public AjaxResult<NotifyDetailInfo> read(@PathVariable("notify_id") String notifyId) {
        this.appNotifyService.markReaded(super.generalUserId(), notifyId);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 40)
    @ApiOperation(value = "标记全部已读", notes = "标记全部已读")
    @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "notify_id", value = "通知ID")
    @PutMapping("/readall")
    public AjaxResult<NotifyDetailInfo> readall() {
        this.appNotifyService.markAllReaded(super.generalUserId());
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 50)
    @ApiOperation(value = "一般用户设备码注册", notes = "一般用户设备码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "registration_id", value = "设备码"),
            @ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "os", value = "操作系统")
    })
    @PutMapping("/{os}/{registration_id}/registeredgeneral")
    public AjaxResult<Object> registeredGeneral(@PathVariable("registration_id") String registration_id, @PathVariable("os") String os) {
        IPushService.VerifyRegistrationId(super.generalUserId(), registration_id, RegistrationGenType, os);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 60)
    @ApiOperation(value = "更新推送通知权限", notes = "更新推送通知权限")
    @PutMapping("/updatenotification")
    public AjaxResult<Object> updateNotification(@RequestBody ButtonVo vo) {
        IPushService.updateNotificationType(super.generalUserId(), vo);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 70)
    @ApiOperation(value = "获取推送通知权限", notes = "获取推送通知权限")
    @GetMapping("/selectnotifications")
    public AjaxResult<ButtonVo> selectNotifications() {
        return AjaxResult.success(IPushService.selectNotificationType(super.generalUserId()));
    }
}
