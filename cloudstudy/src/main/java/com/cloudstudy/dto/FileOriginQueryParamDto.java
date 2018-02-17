package com.cloudstudy.dto;

import java.util.ArrayList;

public class FileOriginQueryParamDto {

	private ArrayList<String> dateRangement;

	private String keyword;

	private ArrayList<String> userNo;

	private ArrayList<Integer> courseId;

	private ArrayList<Integer> homeworkId;

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

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

	public ArrayList<String> getUserNo() {
		return userNo;
	}

	public void setUserNo(ArrayList<String> userNo) {
		this.userNo = userNo;
	}

	public ArrayList<Integer> getCourseId() {
		return courseId;
	}

	public void setCourseId(ArrayList<Integer> courseId) {
		this.courseId = courseId;
	}

	public ArrayList<Integer> getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(ArrayList<Integer> homeworkId) {
		this.homeworkId = homeworkId;
	}

}
