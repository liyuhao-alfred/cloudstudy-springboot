package com.cloudstudy.bo;

public class Studyfilerelcourse {

	private Integer fileId;

	private Integer courseId;

	public Studyfilerelcourse(Integer fileId) {
		super();
		this.fileId = fileId;
	}

	public Studyfilerelcourse(Integer fileId, Integer courseId) {
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