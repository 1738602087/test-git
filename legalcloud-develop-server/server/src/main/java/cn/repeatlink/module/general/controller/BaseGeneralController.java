/**
 * 
 */
package cn.repeatlink.module.general.controller;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.LoginUserGeneral;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.framework.util.SecurityUtils;

/**
 * @author LAI
 * @date 2020-09-27 14:10
 */

@RLPermission(userType = UserType.GENERAL)
public class BaseGeneralController extends BaseController {

	protected String generalUserId() {
		return ((LoginUserGeneral)SecurityUtils.getLoginUser()).getUser_id();
	}
	
}
