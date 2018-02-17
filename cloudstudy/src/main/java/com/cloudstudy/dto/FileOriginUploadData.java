package com.cloudstudy.dto;

public class FileOriginUploadData {

	private Integer courseId;

	private Integer homeworkId;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileOriginUploadData [courseId=");
		builder.append(courseId);
		builder.append(", homeworkId=");
		builder.append(homeworkId);
		builder.append("]");
		return builder.toString();
	}

}