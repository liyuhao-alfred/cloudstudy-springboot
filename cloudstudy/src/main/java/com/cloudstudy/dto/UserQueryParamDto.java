package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;

import com.cloudstudy.constant.SearchType;

@SuppressWarnings("unused")
public class UserQueryParamDto {

	/**
	 * 时间范围
	 */
	private ArrayList<String> dateRangement;

	/**
	 * 关键字
	 */
	private String keyword;

	private String status;

	private ArrayList<String> role;

	/**
	 * 排序
	 */
	@Transient
	private String orderBy;

	private PageDto pageDto = new PageDto();

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public ArrayList<String> getRole() {
		return role;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

}