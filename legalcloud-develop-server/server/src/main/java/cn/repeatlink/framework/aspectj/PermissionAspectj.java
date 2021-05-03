package cn.repeatlink.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.bean.LoginUser;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.framework.util.SecurityUtils;

@Component
@Aspect
@Order(200)
public class PermissionAspectj {

//	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//	public void pointCut() {
//	
//	}

	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping) "
			+ "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PutMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		RLPermission permission = null;
		permission = method.getAnnotation(RLPermission.class);
		if(permission == null) {
			Object target = joinPoint.getTarget();
			permission = target.getClass().getAnnotation(RLPermission.class);
		}
		boolean passed = true;
		if(permission != null) {
			if(!permission.noCheck()) {
				UserType[] userTypes = permission.userType();
				if(userTypes != null && userTypes.length >0) {
					List<UserType> userTypeList = Arrays.asList(userTypes);
					LoginUser loginUser = SecurityUtils.getLoginUser();
					if(loginUser == null) {
						passed = false;
					}
					else {
						UserType userType = UserType.userType(loginUser.getUserType());
						if(userType == null || !userTypeList.contains(userType)) {
							passed = false;
						}
					}
				}
			}
		}
		if(!passed) {
			throw new BaseRuntimeException(BaseResultCode.NOT_PERMISSION);
		}
		
		return joinPoint.proceed();
	}

}
