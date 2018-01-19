package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Permission;
import com.cloudstudy.bo.Rolerelpermission;
import com.cloudstudy.bo.example.RolerelpermissionExample;
import com.cloudstudy.bo.example.RolerelpermissionExample.Criteria;
import com.cloudstudy.mapper.PermissionMapper;
import com.cloudstudy.mapper.RolerelpermissionMapper;
import com.cloudstudy.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolerelpermissionMapper rolerelpermissionMapper;

	@Override
	public List<Permission> findByRoleId(int roleId) {

		RolerelpermissionExample rolerelpermissionExample = new RolerelpermissionExample();

		Criteria criteria = rolerelpermissionExample.createCriteria();
		criteria.andRoleIdEqualTo(roleId);

		List<Rolerelpermission> rolerelpermissionList = rolerelpermissionMapper
				.selectByExample(rolerelpermissionExample);
		if (rolerelpermissionList == null || rolerelpermissionList.isEmpty()) {
			return null;
		}

		List<Permission> permissionList = new ArrayList<Permission>();
		for (Rolerelpermission rolerelpermission : rolerelpermissionList) {
			Permission permission = permissionMapper.selectByPrimaryKey(rolerelpermission.getPermissionId());
			permissionList.add(permission);
		}
		return permissionList;

	}
}
