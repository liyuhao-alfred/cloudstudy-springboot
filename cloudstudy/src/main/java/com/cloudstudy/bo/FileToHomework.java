package com.cloudstudy.bo;

public class FileToHomework {
    private Integer fileId;

    private Integer homeworkId;

    private String uploaderno;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUploaderno() {
        return uploaderno;
    }

    public void setUploaderno(String uploaderno) {
        this.uploaderno = uploaderno == null ? null : uploaderno.trim();
    }
}