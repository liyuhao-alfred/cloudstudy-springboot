package com.cloudstudy.util;

import com.cloudstudy.dto.WebResult;

public class WebResultUtil {

	public static <T> WebResult<T> success(T t) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setStatus(0);
		WebResult.setMessage("操作成功");
		WebResult.setContents(t);
		return WebResult;
	}

	public static <T> WebResult<T> commmonGenResult(Integer code, String msg, T t) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setStatus(code);
		WebResult.setMessage(msg);
		WebResult.setContents(t);
		return WebResult;
	}

	public static <T> WebResult<T> success() {
		return success(null);
	}

	public static <T> WebResult<T> error(Integer code, String msg) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setStatus(code);
		WebResult.setMessage(msg);
		return WebResult;
	}

	public static <T> WebResult<T> fail(Integer code, String msg) {
		WebResult<T> WebResult = new WebResult<T>();
		WebResult.setStatus(code);
		WebResult.setMessage(msg);
		return WebResult;
	}
}
