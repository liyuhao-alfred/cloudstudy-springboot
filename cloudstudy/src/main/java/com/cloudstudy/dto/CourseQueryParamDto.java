package com.cloudstudy.dto;

import java.util.ArrayList;

public class CourseQueryParamDto {

	private String keyword;

	private ArrayList<String> studentNo;

	private ArrayList<String> teacherNo;

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

	public ArrayList<String> getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(ArrayList<String> studentNo) {
		this.studentNo = studentNo;
	}

	public ArrayList<String> getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(ArrayList<String> teacherNo) {
		this.teacherNo = teacherNo;
	}

}
