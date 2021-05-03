package cn.repeatlink.framework.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.util.SysOperLogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

	
	/**
	 * 记录日志
	 *
	 * @param joinPoint 切点
	 */
	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping) "
			+ "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PutMapping)"
			+ "||@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object result = null;
		SysOperLogUtil logUtil = null;
		try {
			logUtil = SysOperLogUtil.getInstance().initbuildOperLog().buildRequestParam(joinPoint);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		try {
			result = joinPoint.proceed();
			if(logUtil!=null) {
				try {
					logUtil.buildRequestResult(result);
				}catch(Exception e) {
					log.error(e.getMessage());
				}
			}
		} catch (Throwable t) {
			if(logUtil!=null) {
				try {
					logUtil.buildRequestResultWithExeption(t);
				}catch(Exception e) {
					log.error(e.getMessage());
				}
			}
			throw t;
		}finally {
			if(logUtil!=null) {
				try {
					logUtil.buildEnd().save();
				}catch(Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
		
		return result;
	}



}
