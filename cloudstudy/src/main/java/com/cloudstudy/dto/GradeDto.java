package com.cloudstudy.dto;

public class GradeDto {
	private Integer id;

	private Integer courseId;

	private String studentNo;

	private Integer grade;

	private Integer homeworkDeclareNum;

	private Integer homeworkAcceptNum;

	private String createTime;

	private String lastModifyTime;

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

	public Integer getHomeworkDeclareNum() {
		return homeworkDeclareNum;
	}

	public void setHomeworkDeclareNum(Integer HomeworkDeclareNum) {
		this.homeworkDeclareNum = HomeworkDeclareNum;
	}

	public Integer getHomeworkAcceptNum() {
		return homeworkAcceptNum;
	}

	public void setHomeworkAcceptNum(Integer HomeworkAcceptNum) {
		this.homeworkAcceptNum = HomeworkAcceptNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}