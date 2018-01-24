package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Permission;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.RoleToPermissionExample;
import com.cloudstudy.bo.RoleToPermissionExample.Criteria;
import com.cloudstudy.mapper.PermissionMapper;
import com.cloudstudy.mapper.RoleToPermissionMapper;
import com.cloudstudy.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleToPermissionMapper roleToPermissionMapper;

	@Override
	public List<Permission> findByRoleId(int roleId) {

		RoleToPermissionExample roleToPermissionExample = new RoleToPermissionExample();

		Criteria criteria = roleToPermissionExample.createCriteria();
		criteria.andRoleIdEqualTo(roleId);

		List<RoleToPermission> roleToPermissionList = roleToPermissionMapper.selectByExample(roleToPermissionExample);
		if (roleToPermissionList == null || roleToPermissionList.isEmpty()) {
			return null;
		}

		List<Permission> permissionList = new ArrayList<Permission>();
		for (RoleToPermission roleToPermission : roleToPermissionList) {
			Permission permission = permissionMapper.selectByPrimaryKey(roleToPermission.getPermissionId());
			permissionList.add(permission);
		}
		return permissionList;

	}
}
