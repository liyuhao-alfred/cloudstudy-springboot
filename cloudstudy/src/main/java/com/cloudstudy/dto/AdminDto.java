package com.cloudstudy.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class AdminDto {

	@NotBlank(message = "工号为空")
	private String no;

	@NotBlank(message = "账号为空")
	private String account;

	@NotBlank(message = "密码为空")
	private String password;

	@NotBlank(message = "姓名为空")
	private String name;

	@NotBlank(message = "电话为空")
	private String phone;

	@NotBlank(message = "邮箱为空")
	private String email;

	@NotBlank(message = "等级非法")
	private Integer level;

	@NotBlank(message = "性别为空")
	private Integer sex;

	@NotBlank(message = "创建时间为空")
	private Date createTime;

	@NotBlank(message = "最后修改时间为空")
	private Date lastModifyTime;

	@NotBlank(message = "状态为空")
	private Integer status;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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