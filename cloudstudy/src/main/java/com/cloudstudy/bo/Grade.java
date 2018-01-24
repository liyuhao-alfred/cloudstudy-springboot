package com.cloudstudy.bo;

import java.util.Date;

public class Grade {
    private Integer id;

    private Integer courseId;

    private String studentNo;

    private Integer grade;

    private Integer taskDeclareNum;

    private Integer taskAcceptNum;

    private Date createTime;

    private Date lastModifyTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo == null ? null : studentNo.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getTaskDeclareNum() {
        return taskDeclareNum;
    }

    public void setTaskDeclareNum(Integer taskDeclareNum) {
        this.taskDeclareNum = taskDeclareNum;
    }

    public Integer getTaskAcceptNum() {
        return taskAcceptNum;
    }

    public void setTaskAcceptNum(Integer taskAcceptNum) {
        this.taskAcceptNum = taskAcceptNum;
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
}