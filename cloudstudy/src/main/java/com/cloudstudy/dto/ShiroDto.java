package com.cloudstudy.dto;

import java.util.HashSet;

public class ShiroDto {

	private HashSet<String> roleType = new HashSet<String>();
	private HashSet<String> permissionType = new HashSet<String>();

	public HashSet<String> getRoleType() {
		return roleType;
	}

	public void setRoleType(HashSet<String> roleType) {
		this.roleType = roleType;
	}

	public HashSet<String> getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(HashSet<String> permissionType) {
		this.permissionType = permissionType;
	}

}