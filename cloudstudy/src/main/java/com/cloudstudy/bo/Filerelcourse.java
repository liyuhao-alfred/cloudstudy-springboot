package com.cloudstudy.bo;

public class Filerelcourse {

	private Integer fileId;

	private Integer courseId;

	public Filerelcourse(Integer fileId, Integer courseId) {
		super();
		this.fileId = fileId;
		this.courseId = courseId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}