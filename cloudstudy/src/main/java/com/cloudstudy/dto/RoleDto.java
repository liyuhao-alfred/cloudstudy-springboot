package com.cloudstudy.dto;

import java.util.List;

public class RoleDto {
	private Integer id;

	private String name;

	private String code;

	private String description;

	private Integer status;

	private List<PermissionDto> permissionDtoList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<PermissionDto> getPermissionDtoList() {
		return permissionDtoList;
	}

	public void setPermissionDtoList(List<PermissionDto> permissionDtoList) {
		this.permissionDtoList = permissionDtoList;
	}
}