package com.cloudstudy.bo;

public class FileToTask {
	private Integer fileId;

	private Integer taskId;

	public FileToTask(Integer fileId, Integer taskId) {
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