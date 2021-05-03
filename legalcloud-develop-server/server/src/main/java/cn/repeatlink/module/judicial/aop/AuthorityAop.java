/**
 * 
 */
package cn.repeatlink.module.judicial.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.UserJudicial;
import cn.repeatlink.common.mapper.UserJudicialMapper;
import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.bean.LoginUserJudicial;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.controller.auth.AuthController;
import cn.repeatlink.module.judicial.controller.auth.EmailCodeController;

/**
 * 司法书士API权限拦截
 * @author LAI
 * @date 2020-09-25 10:24
 */

@Component
@Aspect
@Order(10)
public class AuthorityAop {
	
	@Autowired
	private UserJudicialMapper userJudicialMapper;
	
	@Around("execution(* cn.repeatlink.module.judicial.controller..*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object target = pjp.getTarget();
		// 无须登录，直接执行
		if(target instanceof AuthController || target instanceof EmailCodeController) {
			return pjp.proceed();
		}
		// 当前登录用户是不是司法书士
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if(loginUser == null || !(loginUser instanceof LoginUserJudicial)) {
			// log.warn("非法访问");
			throw JudicialRuntimeException.build("judicial.auth.not.permission");
		}
		// 2021-02-07
		// 废弃状态立即生效
		if(loginUser instanceof LoginUserJudicial) {
			String userId = ((LoginUserJudicial) loginUser).getUser_id();
			UserJudicial judicial = this.userJudicialMapper.selectByPrimaryKey(userId);
			if(!Constant.STATUS_VALID.equals(judicial.getStatus())) {
				throw JudicialRuntimeException.build(BaseResultCode.LOGIN_INFO_EMPTY);
			}
		}
		// 往后执行
		return pjp.proceed();
	}

}
