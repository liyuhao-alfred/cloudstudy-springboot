package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.RoleToUserExample;
import com.cloudstudy.bo.RoleToUserExample.Criteria;
import com.cloudstudy.bo.Role;
import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RoleToUserMapper;
import com.cloudstudy.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleToUserMapper roleToUserMapper;

	@Override
	public List<RoleDto> findRoleByUserNo(String no) {
		RoleToUserExample roleToUserExample = new RoleToUserExample();

		Criteria criteria = roleToUserExample.createCriteria();
		criteria.andUserNoEqualTo(no);

		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			return null;
		}

		List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
		for (RoleToUser roleToUser : roleToUserList) {
			Role role = roleMapper.selectByPrimaryKey(roleToUser.getRoleId());
			if (role != null) {
				RoleDto roleDto = new RoleDto();
				BeanUtils.copyProperties(role, roleDto);
				roleDtoList.add(roleDto);
			}
		}
		return roleDtoList;
	}

}
