package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Permission;
import com.cloudstudy.bo.PermissionExample;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.RoleToPermissionExample;
import com.cloudstudy.bo.RoleToPermissionExample.Criteria;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.PermissionDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.PermissionMapper;
import com.cloudstudy.mapper.RoleToPermissionMapper;
import com.cloudstudy.service.PermissionService;

@Service
@SuppressWarnings("unused")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleToPermissionMapper roleToPermissionMapper;

	@Override
	public List<PermissionDto> findByRoleId(int roleId) {

		RoleToPermissionExample roleToPermissionExample = new RoleToPermissionExample();
		Criteria criteria = roleToPermissionExample.createCriteria();
		criteria.andRoleIdEqualTo(roleId);

		List<RoleToPermission> roleToPermissionList = roleToPermissionMapper.selectByExample(roleToPermissionExample);
		if (roleToPermissionList == null || roleToPermissionList.isEmpty()) {
			return null;
		}

		List<Integer> primaryKeyList = getPrimaryKeyList(roleToPermissionList);
		PermissionExample permissionExample = new PermissionExample();
		com.cloudstudy.bo.PermissionExample.Criteria criteria1 = permissionExample.createCriteria();
		criteria1.andIdIn(primaryKeyList);
		List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
		return generateDto(permissionList);

	}

	private List<Integer> getPrimaryKeyList(List<RoleToPermission> roleToPermissionList) {
		if (roleToPermissionList == null || roleToPermissionList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (RoleToPermission roleToPermission : roleToPermissionList) {
			primaryKeyList.add(roleToPermission.getPermissionId());
		}
		return primaryKeyList;
	}

	private List<PermissionDto> generateDto(List<Permission> permissionList) {
		if (permissionList == null || permissionList.isEmpty()) {
			return new ArrayList<PermissionDto>();
		}
		List<PermissionDto> PermissionDtoList = new ArrayList<PermissionDto>();
		for (Permission permission : permissionList) {
			PermissionDto permissionDto = generateDto(permission);
			PermissionDtoList.add(permissionDto);
		}
		return PermissionDtoList;
	}

	private List<Permission> generate(List<PermissionDto> permissionDtoList) {
		if (permissionDtoList == null || permissionDtoList.isEmpty()) {
			return new ArrayList<Permission>();
		}
		List<Permission> PermissionList = new ArrayList<Permission>();
		for (PermissionDto permissionDto : permissionDtoList) {
			Permission permission = generate(permissionDto);
			PermissionList.add(permission);
		}
		return PermissionList;
	}

	private PermissionDto generateDto(Permission permission) {
		if (permission == null) {
			throw new CloudStudyException();
		}
		PermissionDto permissionDto = new PermissionDto();
		BeanUtils.copyProperties(permission, permissionDto);
		return permissionDto;
	}

	private Permission generate(PermissionDto permissionDto) {
		if (permissionDto == null) {
			throw new CloudStudyException();
		}
		Permission permission = new Permission();
		BeanUtils.copyProperties(permissionDto, permission);
		return permission;
	}
}
