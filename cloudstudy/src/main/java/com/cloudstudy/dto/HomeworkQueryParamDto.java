package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.annotation.Transient;

@SuppressWarnings("unused")
public class HomeworkQueryParamDto {

	private ArrayList<String> dateRangement;

	private String keyword;

	private ArrayList<Integer> courseId;

	private ArrayList<String> studentNo;

	private ArrayList<String> teacherNo;

	private PageDto pageDto;

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public ArrayList<String> getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(ArrayList<String> teacherNo) {
		this.teacherNo = teacherNo;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

}
