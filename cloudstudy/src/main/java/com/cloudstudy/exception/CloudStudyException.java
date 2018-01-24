package com.cloudstudy.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class CloudStudyException extends RuntimeException {

	private String errorCode = "-1";
	private String errorMsg;
	private HashMap<Integer, String> errorMap;// 存放错误的map

	public CloudStudyException() {
		super();
	}

	public CloudStudyException(String message) {
		super(message);
		this.errorMsg = message;
	}

	public CloudStudyException(HashMap<Integer, String> errorMap) {
		this.errorMap = errorMap;
	}

	public CloudStudyException(String message, Throwable t) {
		super(message, t);
		this.errorMsg = message;
	}

	public CloudStudyException(String message, String errorCode) {
		super(message);
		this.errorMsg = message;
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public HashMap<Integer, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(HashMap<Integer, String> errorMap) {
		this.errorMap = errorMap;
	}

	public String getErrorMapMsg() {
		String errorMsg = "";
		for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
			errorMsg += "[错误" + entry.getKey() + ":" + entry.getValue() + "];";

		}
		return errorMsg;
	}
}
