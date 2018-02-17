package com.cloudstudy.dto;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("unused")
public class UserDto {
	private String no;

	private String account;

	private String password;

	private String salt;

	private String token;

	private String name;

	private String phone;

	private String email;

	private String sex;

	private Integer age;

	private String birthday;

	private String createTime;

	private String lastModifyTime;

	private String registTime;

	private Boolean status;

	private String description;

	private ArrayList<String> role;

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public ArrayList<String> getRole() {
		return role;
	}

}