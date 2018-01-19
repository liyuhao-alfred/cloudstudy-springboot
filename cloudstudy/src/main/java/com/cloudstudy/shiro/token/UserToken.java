package com.cloudstudy.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.cloudstudy.dto.UserDto;

public class UserToken extends UsernamePasswordToken {

	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;
	private String userAccount;
	private String userPassword;
	private String userSalt;

	public UserToken() {
		super();
	}

	public UserToken(String userAccount, String userPassword) {
		super();
		super.setUsername(userAccount);
		super.setPassword(userPassword.toCharArray());
		this.userAccount = userAccount;
		this.userPassword = userPassword;
	}

	public UserToken(String userAccount, String userPassword, String userSalt) {
		super();
		super.setUsername(userAccount);
		super.setPassword(userPassword.toCharArray());
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userSalt = userSalt;
	}

	public Object getPrincipal() {
		return new UserDto(userAccount, userPassword, userSalt);
	}

	public Object getCredentials() {
		return getUserAccount();
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
