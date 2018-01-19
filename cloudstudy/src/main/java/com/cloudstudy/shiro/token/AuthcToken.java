package com.cloudstudy.shiro.token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import com.cloudstudy.dto.UserDto;

public class AuthcToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String password;
	private String salt;

	private boolean rememberMe = false;
	private String host;

	public AuthcToken() {
		super();
	}

	public AuthcToken(String account, String password, String salt) {
		super();
		this.account = account;
		this.password = password;
		this.salt = salt;
	}

	public AuthcToken(String account, String password, String salt, boolean rememberMe, String host) {
		super();
		this.account = account;
		this.password = password;
		this.salt = salt;
		this.rememberMe = rememberMe;
		this.host = host;
	}

	@Override
	public Object getPrincipal() {
		return new UserDto(account, password, salt);
	}

	@Override
	public Object getCredentials() {
		return account + salt;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
