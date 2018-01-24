package com.cloudstudy.bo;

public class FileToJob {
	private Integer fileId;

	private Integer jobId;

	public FileToJob(Integer fileId, Integer jobId) {
		this.fileId = fileId;
		this.jobId = jobId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
}