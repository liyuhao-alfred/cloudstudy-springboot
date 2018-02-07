package com.cloudstudy.dto;

import java.util.ArrayList;

import org.springframework.data.annotation.Transient;

public class FileOriginQueryParamDto {

	/**
	 * 文件大小
	 */
	private ArrayList<String> fileSizeRangement;

	/**
	 * 时间范围
	 */
	private ArrayList<String> dateRangement;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 排序
	 */
	@Transient
	private String orderBy;

	private PageDto pageDto = new PageDto();

	public ArrayList<String> getFileSizeRangement() {
		return fileSizeRangement;
	}

	public void setFileSizeRangement(ArrayList<String> fileSizeRangement) {
		this.fileSizeRangement = fileSizeRangement;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

}
