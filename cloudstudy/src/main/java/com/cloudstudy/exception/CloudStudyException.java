package com.cloudstudy.exception;

/**
 * @author liyuhao
 * @since 2017/7/17
 */
public class CloudStudyException extends RuntimeException {
	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;

	public CloudStudyException(String msg) {
		this.msg = msg;
	}

	public CloudStudyException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}