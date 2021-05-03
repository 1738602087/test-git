/**
 * 
 */
package cn.repeatlink.module.manage.controller;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.controller.base.BaseController;

/**
 * @author LAI
 * @date 2021-01-15 15:07
 */

@RLPermission(userType = UserType.SYS)
public class BaseSysController extends BaseController {

}
