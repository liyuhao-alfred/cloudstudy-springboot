package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.PermissionDto;

public interface PermissionService {

	List<PermissionDto> findByRoleId(int roleId);

}
