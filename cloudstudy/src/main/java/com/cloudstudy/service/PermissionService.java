package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.bo.Permission;

public interface PermissionService {

	List<Permission> findByRoleId(int roleId);

}
