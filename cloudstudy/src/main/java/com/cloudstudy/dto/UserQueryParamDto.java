package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;

@SuppressWarnings("unused")
public class UserQueryParamDto {

	private ArrayList<String> dateRangement;

	private String keyword;

	private String status;

	private ArrayList<String> role;

	private ArrayList<Integer> courseId;

	private PageDto pageDto;

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

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

	public ArrayList<Integer> getCourseId() {
		return courseId;
	}

	public void setCourseId(ArrayList<Integer> courseId) {
		this.courseId = courseId;
	}

}