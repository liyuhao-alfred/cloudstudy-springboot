package com.cloudstudy.util;

import com.cloudstudy.dto.WebResult;

public class WebResultUtil {

	private static Integer successCode = 0;
	private static Integer failCode = 1;
	private static Integer errorCode = 2;

	private static String successMsg = "操作成功";
	private static String failMsg = "操作失败";
	private static String errorMsg = "操作错误";

	public static <T> WebResult<T> success() {
		return success(null);
	}

	public static <T> WebResult<T> success(T t) {
		return success(t, successMsg);
	}

	public static <T> WebResult<T> success(T t, String msg) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setCode(successCode);
		WebResult.setMessage(msg);
		WebResult.setData(t);
		return WebResult;
	}

	public static <T> WebResult<T> fail() {
		return fail(null);
	}

	public static <T> WebResult<T> fail(T t) {
		return fail(t, failMsg);
	}

	public static <T> WebResult<T> fail(T t, String msg) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setCode(failCode);
		WebResult.setMessage(msg);
		WebResult.setData(t);
		return WebResult;
	}

	public static <T> WebResult<T> error() {
		return error(null);
	}

	public static <T> WebResult<T> error(T t) {
		return error(t, errorMsg);
	}

	public static <T> WebResult<T> error(T t, String msg) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setCode(errorCode);
		WebResult.setMessage(msg);
		WebResult.setData(t);
		return WebResult;
	}
}
