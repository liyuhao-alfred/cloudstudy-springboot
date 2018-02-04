package com.cloudstudy.dto;

/**
 * 
 * controller响应类
 *
 */
public class WebResult<T> {

	/**
	 * @CertManagerStatusConstant
	 */
	private Integer code;

	private String message;

	private T data;

	public WebResult() {

	}

	public WebResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
