package cn.repeatlink.framework.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.framework.common.RestResponseVo;
import cn.repeatlink.framework.exception.BaseRuntimeException;

@Component
@Aspect
@Order(100)
public class AjaxResultAspectj {

//	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//	public void pointCut() {
//	
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping) "
			+ "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PutMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
		
			Object object = joinPoint.proceed();
			if (object instanceof AjaxResult && !(object instanceof RestResponseVo)) {
				AjaxResult result = (AjaxResult) object;
				return new RestResponseVo(result);
			}
			return object;
		} catch (BaseRuntimeException e) {
			AjaxResult result = AjaxResult.failed(null, e);
			return new RestResponseVo(result);
		}
	}

}
