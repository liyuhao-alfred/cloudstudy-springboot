package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("unused")
public class CourseDto {
	private Integer id;

	private String teacherNo;

	private String name;

	private String createTime;

	private String lastModifyTime;

	private ArrayList<String> dateRangement;

	private Integer declareNum;

	private Integer acceptNum;

	private Integer count;

	private Boolean status;

	private String description;

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
		this.teacherNo = teacherNo == null ? null : teacherNo.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
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

	public ArrayList<String> getDateRangement() {
		return dateRangement;
	}

	public void setDateRangement(ArrayList<String> dateRangement) {
		this.dateRangement = dateRangement;
	}

}