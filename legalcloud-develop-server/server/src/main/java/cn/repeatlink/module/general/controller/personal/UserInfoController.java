/**
 *
 */
package cn.repeatlink.module.general.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.general.common.Define;
import cn.repeatlink.module.general.controller.BaseGeneralController;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.user.UserAttributeInfo;
import cn.repeatlink.module.general.vo.user.UserAvatarVo;
import cn.repeatlink.module.general.vo.user.UserInfo;
import cn.repeatlink.module.general.vo.user.UserPwdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LAI
 * @date 2020-10-20 15:10
 */

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/user/")
@Api(value = "个人信息", tags = "个人信息")
@ApiSort(60)
public class UserInfoController extends BaseGeneralController {

    @Autowired
    private IGeneralUserService generalUserService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取个人情报", notes = "获取个人情报")
    @GetMapping("/info")
    public AjaxResult<UserInfo> getInfo() {
        return AjaxResult.success(this.generalUserService.getUserInfo(super.generalUserId()));
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "变更个人情报", notes = "变更个人情报")
    @PutMapping("/info")
    public AjaxResult<Object> updateInfo(@RequestBody UserInfo vo) {
        this.generalUserService.updateUserInfo(super.generalUserId(), super.generalUserId(), vo);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取个人附加属性情报", notes = "获取个人附加属性情报")
    @GetMapping("/attributeinfo")
    public AjaxResult<UserAttributeInfo> getAttributeInfo() {
        return AjaxResult.success(this.generalUserService.getAttributeInfo(super.generalUserId()));
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "变更个人附加属性情报", notes = "变更个人附加属性情报")
    @PutMapping("/attributeinfo")
    public AjaxResult<Object> updateAttributeInfo(@RequestBody UserAttributeInfo vo) {
        this.generalUserService.updateAttributeInfo(super.generalUserId(), vo);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "获取个人头像", notes = "获取个人头像")
    @GetMapping("/avatar")
    public AjaxResult<UserAvatarVo> getAvatar() {
        UserInfo userInfo = this.generalUserService.getUserInfo(super.generalUserId());
        UserAvatarVo vo = new UserAvatarVo();
        vo.setAvatar(userInfo.getAvatar());
        return AjaxResult.success(vo);
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "变更个人头像", notes = "变更个人头像")
    @PutMapping("/avatar")
    public AjaxResult<Object> updateAvatar(@RequestBody UserAvatarVo vo) {
        this.generalUserService.updateAvatar(super.generalUserId(), vo);
        return AjaxResult.success();
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "变更密码", notes = "变更密码")
    @PostMapping("/password")
    public AjaxResult<Object> updatePwd(@RequestBody UserPwdVo vo) {
        this.generalUserService.updatePwd(super.generalUserId(), vo);
        return AjaxResult.success();
    }

}
