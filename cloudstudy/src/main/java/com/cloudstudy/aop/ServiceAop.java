/******************************************************************
 *    
 *    Package:     com.cloudstudy.aop
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月16日 下午5:36:34
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月16日 下午5:36:34 
 * @version 1.0.0  
 */
package com.cloudstudy.aop;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudstudy.dto.SystemlogDto;
import com.cloudstudy.log.SystemlogService;
import com.cloudstudy.util.Util;

/**
 * 使用环绕通知
 * 
 * @author alfred
 *
 */
@Component
@Aspect
public class ServiceAop {
	private Log log = LogFactory.getLog(ServiceAop.class);
	@Autowired
	private SystemlogService systemlogService;

	@Pointcut("(execution(* com.cloudstudy.service.impl.*.*(..)))")
	public void serviceMethodCut() {

	}

	/**
	 * 环绕通知： 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
	 * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
	 * 
	 * @throws Throwable
	 */
	@Around("serviceMethodCut()")
	public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = null;
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return joinPoint.proceed();
		}

		String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		String argsStr = Arrays.toString(joinPoint.getArgs());
		Long startTime = System.currentTimeMillis();

		SystemlogDto systemlogDto = new SystemlogDto();
		systemlogDto.setRemoteCallIp(Util.getIp(request));
		systemlogDto.setLogTime(new Date());
		systemlogDto.setLogType(Util.getOpTypeByAnnotation(joinPoint));
		systemlogDto.setThreadId(Long.toString(Thread.currentThread().getId()));
		systemlogDto.setThreadName(Thread.currentThread().getName());
		systemlogDto.setServiceClass(declaringTypeName);
		systemlogDto.setServiceMethod(methodName);
		systemlogDto.setServiceArgs(argsStr);

		try {
			Object retVal = joinPoint.proceed();

			systemlogDto.setLogLevel("INFO");
			systemlogDto.setServiceResultFlage(0);
			systemlogDto.setServiceResult(retVal != null ? retVal.toString() : null);
			systemlogDto.setServiceRunningTime((System.currentTimeMillis() - startTime) + "ms");
			systemlogService.save(systemlogDto);

			return retVal;
		} catch (Throwable e) {

			systemlogDto.setLogLevel("ERROR");
			systemlogDto.setServiceResultFlage(1);
			systemlogDto.setServiceErrorCode(e.getClass().getTypeName());
			systemlogDto.setServiceErrorMessage(e.getMessage());
			systemlogDto.setServiceRunningTime((System.currentTimeMillis() - startTime) + "ms");
			systemlogService.save(systemlogDto);
			log.error(systemlogDto, e);
			throw e;
		}
	}

}