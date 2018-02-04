package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.List;

public class UserQueryDto {
	private String no;

	private String account;

	private String name;

	private String phone;

	private String email;

	private String sex;

	private String age;

	private String birthday;

	private String createTime;

	private String lastModifyTime;

	private String registTime;

	private Boolean status;

	private String statusMemo;

	private String description;

	private List<RoleDto> roleDto;

	private ArrayList<String> role;

	private ArrayList<CourseQueryDto> teachCourse = new ArrayList<CourseQueryDto>();

	private ArrayList<GradeQueryDto> studyCourse = new ArrayList<GradeQueryDto>();

	private ShiroDto shiro;

	public UserQueryDto() {
		super();
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no == null ? null : no.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public ShiroDto getShiro() {
		return shiro;
	}

	public void setShiro(ShiroDto shiro) {
		this.shiro = shiro;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getStatusMemo() {
		return statusMemo;
	}

	public void setStatusMemo(String statusMemo) {
		this.statusMemo = statusMemo;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public ArrayList<CourseQueryDto> getTeachCourse() {
		return teachCourse;
	}

	public void setTeachCourse(ArrayList<CourseQueryDto> teachCourse) {
		this.teachCourse = teachCourse;
	}

	public List<RoleDto> getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(List<RoleDto> roleDto) {
		this.roleDto = roleDto;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public ArrayList<String> getRole() {
		return role;
	}

	public ArrayList<GradeQueryDto> getStudyCourse() {
		return studyCourse;
	}

	public void setStudyCourse(ArrayList<GradeQueryDto> studyCourse) {
		this.studyCourse = studyCourse;
	}

}