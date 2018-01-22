package com.cloudstudy.bo;

public class Studyfilereltask {
	private Integer fileId;

	private Integer taskId;

	public Studyfilereltask(Integer fileId, Integer taskId) {
		super();
		this.fileId = fileId;
		this.taskId = taskId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
}