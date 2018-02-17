package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("unused")
public class GradeQueryParamDto {

	private ArrayList<Integer> courseId;

	private ArrayList<String> studentNo;

	private PageDto pageDto;

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public ArrayList<Integer> getCourseId() {
		return courseId;
	}

	public void setCourseId(ArrayList<Integer> courseId) {
		this.courseId = courseId;
	}

	public ArrayList<String> getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(ArrayList<String> studentNo) {
		this.studentNo = studentNo;
	}

}
