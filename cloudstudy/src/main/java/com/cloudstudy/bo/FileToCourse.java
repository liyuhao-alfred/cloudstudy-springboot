package com.cloudstudy.bo;

public class FileToCourse {
    private Integer fileId;

    private Integer courseId;

    private String uploaderno;

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

    public String getUploaderno() {
        return uploaderno;
    }

    public void setUploaderno(String uploaderno) {
        this.uploaderno = uploaderno == null ? null : uploaderno.trim();
    }
}