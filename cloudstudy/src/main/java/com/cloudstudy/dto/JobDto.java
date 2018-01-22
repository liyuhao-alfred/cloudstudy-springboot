package com.cloudstudy.dto;

import java.util.Date;

public class JobDto {
	private Integer id;

	private String title;

	private Integer courserelstudentId;

	private Integer taskId;

	private Integer grade;

	private Date createTime;

	private Date lastModifyTime;

	private Integer status;

	private String content;

	private StudyFileDto studyFileDto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getCourserelstudentId() {
		return courserelstudentId;
	}

	public void setCourserelstudentId(Integer courserelstudentId) {
		this.courserelstudentId = courserelstudentId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public StudyFileDto getStudyFileDto() {
		return studyFileDto;
	}

	public void setStudyFileDto(StudyFileDto studyFileDto) {
		this.studyFileDto = studyFileDto;
	}
}