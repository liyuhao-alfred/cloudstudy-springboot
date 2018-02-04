package com.cloudstudy.dto;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 自定义分页bo类。主要设置三个属性。与params中设置的对应
 * 
 * @author alfred
 *
 */
public class PageDto {

	/**
	 * 当前页
	 */
	@Transient
	private Integer current = 1;

	/**
	 * 每页数据量
	 */
	@Transient
	private Integer size = 10;

	/**
	 * 总数
	 */
	@Transient
	private Integer total = 0;

	@JsonIgnore
	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	@JsonIgnore
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonIgnore
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}