package com.cloudstudy.dto;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 自定义分页bo类。主要设置三个属性。与params中设置的对应
 * 
 * @author alfred
 *
 */
@SuppressWarnings("unused")
public class PageResultDto<T> {

	/**
	 * @CertManagerStatusConstant
	 */
	private Long total;

	private T content;

	public PageResultDto(Long total, T content) {
		super();
		this.total = total;
		this.content = content;
	}

	public PageResultDto() {
		super();
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}