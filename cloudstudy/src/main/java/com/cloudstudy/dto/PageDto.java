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
	private Integer page = 1;

	/**
	 * 每页数据量
	 */
	@Transient
	private Integer rows = 10;

	/**
	 * 排序
	 */
	@Transient
	private String orderBy;

	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@JsonIgnore
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@JsonIgnore
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageDto [page=");
		builder.append(page);
		builder.append(", rows=");
		builder.append(rows);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append("]");
		return builder.toString();
	}
}