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
	private Integer status;

	private String message;

	private T contents;

	public WebResult() {

	}

	public WebResult(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContents() {
		return contents;
	}

	public void setContents(T contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WebResult [status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append(", contents=");
		builder.append(contents);
		builder.append("]");
		return builder.toString();
	}

}
