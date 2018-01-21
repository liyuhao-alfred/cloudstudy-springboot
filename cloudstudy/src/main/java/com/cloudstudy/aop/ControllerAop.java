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

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;
import com.cloudstudy.dto.OperatelogDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.log.OperatelogService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.Util;

/**
 * 使用前置后置通知
 * 
 * @author alfred
 *
 */
@Component
@Aspect
public class ControllerAop {
	private Log log = LogFactory.getLog(ControllerAop.class);
	@Autowired
	private OperatelogService operatelogService;
	@Autowired
	private UserService userService;

	private OperatelogDto operatelogDto = new OperatelogDto();

	// 匹配com.cloudstudy.controller包及其子包下的所有类的所有方法
	@Pointcut("execution(* com.cloudstudy.controller..*.*(..))")
	public void controllerCut() {

	}

	/**
	 * 操作员请求日志记录
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerCut()")
	public Object operatorRequestAround(ProceedingJoinPoint joinPoint) throws Throwable {
		RequestAttributes requestAttributes = null;
		HttpServletRequest request = null;
		try {
			requestAttributes = RequestContextHolder.getRequestAttributes();// 获取RequestAttributes
			request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);// 从获取RequestAttributes中获取HttpServletRequest的信息
		} catch (Exception e) {
			return joinPoint.proceed();
		}

		HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION); // 如果要获取Session信息
		String loginAccount = (String) session.getAttribute("login_account");
		if (StringUtils.isEmpty(loginAccount)) {
//			Object operationResult = joinPoint.proceed();
//			return operationResult;
		}

//		UserDto userDto = userService.findByAccount(loginAccount);
//		if (userDto != null) {
//			operatelogDto.setOperatorNo(userDto.getNo());
//			operatelogDto.setOperatorName(userDto.getName());
//		}

		String requestContent = null;
		String contentType = request.getContentType();
		if (!StringUtils.isEmpty(contentType) && contentType.contains("multipart/form-data")) {
			Object[] args = joinPoint.getArgs();
			requestContent = args.toString();
		} else {
			Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>(request.getParameterMap());
			requestContent = Util.map2String(parameterMap);
		}
		operatelogDto.setRequestContent(requestContent);// 请求的参数信息

		Signature signature = joinPoint.getSignature();// 用的最多 通知的签名
		signature.getDeclaringType();// AOP代理类的类（class）信息
		String requestMethodName = signature.getName();// 代理的是哪一个方法
		String declaringTypeName = signature.getDeclaringTypeName();// AOP代理类的名字
		operatelogDto.setRequestIp(request.getRemoteAddr() + ":" + request.getRemotePort());
		operatelogDto.setRequestIp(request.getRemoteAddr());
		operatelogDto.setRequestUri(request.getRequestURI());
		operatelogDto.setOperationDescription(declaringTypeName + "." + requestMethodName);

		operatelogDto.setOperationStartTime(new Date());

		joinPoint.getThis();// AOP代理类的信息
		joinPoint.getTarget(); // 代理的目标对象

		Date operationStartTime = new Date();
		try {
			Object operationResult = joinPoint.proceed();

			operatelogDto.setOperationStartTime(operationStartTime);
			operatelogDto.setOperationEndTime(new Date());
			operatelogDto.setOperationResultFlage(0);
			operatelogDto.setOperationResult(operationResult != null ? operationResult.toString() : null);
			operatelogService.save(operatelogDto);
			return operationResult;
		} catch (Throwable e) {

			operatelogDto.setOperationStartTime(operationStartTime);
			operatelogDto.setOperationEndTime(new Date());
			operatelogDto.setOperationResultFlage(1);
			operatelogDto.setOperationErrorCode(e.getClass().getName());
			operatelogDto.setOperationErrorMessage(e.getMessage());
			operatelogService.save(operatelogDto);
			log.error(operatelogDto);
			throw e;
		}
	}

}