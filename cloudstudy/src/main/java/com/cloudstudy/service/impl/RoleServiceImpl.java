package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.RoleToUserExample;
import com.cloudstudy.bo.RoleToUserExample.Criteria;
import com.cloudstudy.constant.RoleConstant;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Role;
import com.cloudstudy.bo.RoleExample;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.PermissionDto;
import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RoleToUserMapper;
import com.cloudstudy.service.PermissionService;
import com.cloudstudy.service.RoleService;

@Service
@SuppressWarnings("unused")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleToUserMapper roleToUserMapper;
	@Autowired
	private PermissionService permissionService;

	@Override
	public List<RoleDto> findRoleByUserNo(String userNo) {
		RoleToUserExample roleToUserExample = new RoleToUserExample();

		Criteria criteria = roleToUserExample.createCriteria();
		criteria.andUserNoEqualTo(userNo);

		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			return null;
		}

		List<Integer> idList = getPrimaryKeyList(roleToUserList);
		RoleExample roleExample = new RoleExample();
		com.cloudstudy.bo.RoleExample.Criteria criteria1 = roleExample.createCriteria();
		criteria1.andIdIn(idList);
		List<Role> roleList = roleMapper.selectByExample(roleExample);
		return generateDto(roleList);
	}

	@Override
	public ArrayList<String> findRoleStringByUserNo(String userNo) {
		ArrayList<String> roleCodeList = new ArrayList<String>();
		List<RoleDto> RoleDtoList = findRoleByUserNo(userNo);
		if (RoleDtoList != null && !RoleDtoList.isEmpty()) {
			for (RoleDto roleDto : RoleDtoList) {
				roleCodeList.add(roleDto.getCode());
			}
		}
		return roleCodeList;
	}

	@Override
	public void deleteRoleByUserNo(String userNo) {
		RoleToUserExample roleToUserExample = new RoleToUserExample();

		Criteria criteria = roleToUserExample.createCriteria();
		criteria.andUserNoEqualTo(userNo);

		roleToUserMapper.deleteByExample(roleToUserExample);
	}

	private List<Integer> getPrimaryKeyList(List<RoleToUser> roleToUserList) {
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> idList = new ArrayList<Integer>();
		for (RoleToUser roleToUser : roleToUserList) {
			idList.add(roleToUser.getRoleId());
		}
		return idList;
	}

	private List<RoleDto> generateDto(List<Role> roleList) {
		if (roleList == null || roleList.isEmpty()) {
			return new ArrayList<RoleDto>();
		}
		List<RoleDto> RoleDtoList = new ArrayList<RoleDto>();
		for (Role role : roleList) {
			RoleDto roleDto = generateDto(role);
			RoleDtoList.add(roleDto);
		}
		return RoleDtoList;
	}

	private List<Role> generate(List<RoleDto> roleDtoList) {
		if (roleDtoList == null || roleDtoList.isEmpty()) {
			return new ArrayList<Role>();
		}
		List<Role> RoleList = new ArrayList<Role>();
		for (RoleDto roleDto : roleDtoList) {
			Role role = generate(roleDto);
			RoleList.add(role);
		}
		return RoleList;
	}

	private RoleDto generateDto(Role role) {
		if (role == null) {
			throw new CloudStudyException();
		}
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(role, roleDto);
		return roleDto;
	}

	private Role generate(RoleDto roleDto) {
		if (roleDto == null) {
			throw new CloudStudyException();
		}
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		return role;
	}

	@Override
	public List<RoleDto> findAllRoleWithPermission() {
		List<Role> roleList = roleMapper.selectByExample(null);
		List<RoleDto> roleDtoList = generateDto(roleList);
		for (int i = 0; i < roleDtoList.size(); i++) {
			RoleDto roleDto = roleDtoList.get(i);
			List<PermissionDto> permissionDtoList = permissionService.findByRoleId(roleDto.getId());
			roleDtoList.get(i).setPermissionDtoList(permissionDtoList);
		}
		return roleDtoList;
	}

	@Override
	public void saveRoleByUserNoAndRoleCode(String roleCode, String userNo) {
		List<RoleToUser> roleToUserList = findRoleByUserNoAndRoleCode(roleCode, userNo);
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			RoleToUser roleToUser = new RoleToUser();
			roleToUser.setRoleId(RoleConstant.getId(roleCode));
			roleToUser.setUserNo(userNo);
			roleToUserMapper.insert(roleToUser);
		}
	}

	@Override
	public List<RoleToUser> findRoleByUserNoAndRoleCode(String roleCode, String userNo) {
		RoleToUserExample roleToUserExample = new RoleToUserExample();

		Criteria criteria = roleToUserExample.createCriteria();
		criteria.andUserNoEqualTo(userNo);
		criteria.andRoleIdEqualTo(RoleConstant.getId(roleCode));

		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			return null;
		}

		return roleToUserList;
	}
}
