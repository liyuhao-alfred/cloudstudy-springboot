package com.cloudstudy.constant;

public enum SearchType {

	adminName(1, "管理员名搜索"), //
	teacherName(2, "教师名搜索"), //
	studentName(3, "学生名搜索"), //
	courseName(4, "课程名搜索"), //
	homeworkName(5, "作业名搜索"), //
	no(6, "学号或者工号搜索"), //
	account(7, "账号搜索"), //
	phone(8, "电话搜索"), //
	email(9, "邮箱搜索"), //
	fileName(10, "文件名搜索"), //
	all(0, "全文搜索"); //
	private int code;
	private String description;

	SearchType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}