/**
 * 
 */
package cn.repeatlink.module.judicial.controller;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.LoginUserJudicial;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.framework.util.SecurityUtils;

/**
 * @author LAI
 * @date 2020-09-10 14:01
 */

@RLPermission(userType = UserType.JUDICIAL)
public class BaseJudicialController extends BaseController {
	
	
	protected String judicialUserId() {
		return ((LoginUserJudicial)SecurityUtils.getLoginUser()).getUser_id();
	}

	protected String groupId() {
		return ((LoginUserJudicial)SecurityUtils.getLoginUser()).getGroup_id();
	}

}
