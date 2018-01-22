package com.cloudstudy.dto;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class UserDto {
	private String no;

	private String account;

	private String password;

	private String salt;

	private String name;

	private String phone;

	private String email;

	private Integer sex;

	private Integer age;

	private Date birthday;

	private Date createTime;

	private Date lastModifyTime;

	private Date registTime;

	private Integer status;

	private String description;

	private ArrayList<Integer> roleType = new ArrayList<Integer>();

	private ShiroDto shiro;

	public UserDto() {
		super();
	}

	public UserDto(String account, String password, String salt) {
		super();
		this.account = account;
		this.password = password;
		this.salt = salt;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	/**
	 * 密码盐.
	 * 
	 * @return
	 */
	public String getCredentialsSalt() {
		return this.getAccount() + this.salt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [no=");
		builder.append(no);
		builder.append(", account=");
		builder.append(account);
		builder.append(", password=");
		builder.append(password);
		builder.append(", salt=");
		builder.append(salt);
		builder.append(", name=");
		builder.append(name);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", age=");
		builder.append(age);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", lastModifyTime=");
		builder.append(lastModifyTime);
		builder.append(", registTime=");
		builder.append(registTime);
		builder.append(", status=");
		builder.append(status);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

	public ShiroDto getShiro() {
		return shiro;
	}

	public void setShiro(ShiroDto shiro) {
		this.shiro = shiro;
	}

	public ArrayList<Integer> getRoleType() {
		return roleType;
	}

	public void setRoleType(ArrayList<Integer> roleType) {
		this.roleType = roleType;
	}

}