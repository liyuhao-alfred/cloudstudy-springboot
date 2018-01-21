package com.cloudstudy.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.cloudstudy.aop.annotation.LogPointcut;

public class Util {

	/**
	 * 生成订单id
	 * 
	 * @return
	 */
	public static String generateSerialNo() {
		String ExpressOrderRandom = generateRandom(4);
		String ExpressOrderDate = DateUtil.dateToString(new Date(), DateUtil.formatYMDHMSSStr);
		return ExpressOrderDate + ExpressOrderRandom;
	}

	/**
	 * 产生指定长度的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandom(int length) {
		if (length <= 0)
			throw new RuntimeException("随机数生成：无效长度(" + length + ")");
		int bound = 0;
		if (length >= 10)
			bound = Integer.MAX_VALUE;
		else {
			for (int i = 0; i < length; i++)
				bound = bound * 10 + 9;
		}
		Random random = new Random();
		int randomInt = random.nextInt(bound);
		StringBuffer sb = new StringBuffer();
		sb.append(randomInt);
		for (int i = 0; i < length - sb.length(); i++) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	/**
	 * 将一个值为数组的Map对象转化为字符串 格式：[key1=[value1, value2, ...], key2=[value1, value2,
	 * ...], key3=value, ...]
	 * 
	 * @param mapObj
	 * @return
	 */
	public static <K, V> String map2String(Map<K, V[]> mapObj) {
		if (MapUtils.isEmpty(mapObj)) {
			return "[]";
		}
		StringBuffer mapObjToStringBuffer = new StringBuffer();
		mapObjToStringBuffer.append("[");
		for (Map.Entry<K, V[]> entry : mapObj.entrySet()) {
			mapObjToStringBuffer
					.append(entry.getKey() + "=" + (entry.getValue().length > 1 ? Arrays.toString(entry.getValue())
							: entry.getValue().length == 0 ? null : entry.getValue()[0]) + ", ");
		}
		mapObjToStringBuffer.replace(mapObjToStringBuffer.length() - 2, mapObjToStringBuffer.length(), "]");
		return mapObjToStringBuffer.toString();
	}

	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getIp(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null) {
			if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
				int index = ip.indexOf(",");
				if (index != -1) {
					return ip.substring(0, index);
				} else {
					return ip;
				}
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (ip != null) {
			if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (ip != null) {
			if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip != null) {
			if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		ip = request.getRemoteAddr();
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * 通过反射获取注解的code
	 * 
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unused")
	public static String getOpTypeByAnnotation(ProceedingJoinPoint joinPoint)
			throws NoSuchMethodException, SecurityException {

		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();

		Class<?> classTarget = joinPoint.getTarget().getClass();
		Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
		Method objMethod = classTarget.getMethod(methodName, par);
		if (objMethod.isAnnotationPresent(LogPointcut.class)) {// 查看某个方法是否存在该注解
			LogPointcut myAnnotation = objMethod.getAnnotation(LogPointcut.class);
			String code = myAnnotation.code();
			String description = myAnnotation.description();
			return code;
		} else {
			return null;
		}
	}
}
