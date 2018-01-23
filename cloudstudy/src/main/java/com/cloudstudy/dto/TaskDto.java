package com.cloudstudy.dto;

import java.io.File;
import java.util.Date;

public class TaskDto {
	private Integer id;

	private String title;

	private Integer courserelteacherId;

	private Date deadLine;

	private Date createTime;

	private Date lastModifyTime;

	private Integer status;

	private String content;

	private File studyFile;

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

	public Integer getCourserelteacherId() {
		return courserelteacherId;
	}

	public void setCourserelteacherId(Integer courserelteacherId) {
		this.courserelteacherId = courserelteacherId;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
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

	public File getStudyFile() {
		return studyFile;
	}

	public void setStudyFile(File studyFile) {
		this.studyFile = studyFile;
	}
}