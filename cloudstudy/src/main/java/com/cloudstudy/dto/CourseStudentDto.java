package com.cloudstudy.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


@SuppressWarnings("unused")
public class CourseStudentDto {
	private Integer id;

	private String teacherNo;

	private String name;

	private String createTime;

	private String lastModifyTime;

	private String beginTime;

	private String endTime;

	private Integer declareNum;

	private Integer acceptNum;

	private Integer count;

	private Boolean status;

	private Boolean isCommit;

	private String statusMemo;

	private String description;

	private UserDto teacher;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDeclareNum() {
		return declareNum;
	}

	public void setDeclareNum(Integer declareNum) {
		this.declareNum = declareNum;
	}

	public Integer getAcceptNum() {
		return acceptNum;
	}

	public void setAcceptNum(Integer acceptNum) {
		this.acceptNum = acceptNum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getStatusMemo() {
		return statusMemo;
	}

	public void setStatusMemo(String statusMemo) {
		this.statusMemo = statusMemo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDto getTeacher() {
		return teacher;
	}

	public void setTeacher(UserDto teacher) {
		this.teacher = teacher;
	}

	public Boolean getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(Boolean isCommit) {
		this.isCommit = isCommit;
	}

}