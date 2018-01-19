package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Rolereluser;
import com.cloudstudy.bo.Role;
import com.cloudstudy.bo.example.RolereluserExample;
import com.cloudstudy.bo.example.RolereluserExample.Criteria;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RolereluserMapper;
import com.cloudstudy.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolereluserMapper rolereluserMapper;

	@Override
	public List<Role> findByUserNo(String no) {
		RolereluserExample rolereluserExample = new RolereluserExample();

		Criteria criteria = rolereluserExample.createCriteria();
		criteria.andUserNoEqualTo(no);

		List<Rolereluser> rolereluserList = rolereluserMapper.selectByExample(rolereluserExample);
		if (rolereluserList == null || rolereluserList.isEmpty()) {
			return null;
		}

		List<Role> roleList = new ArrayList<Role>();
		for (Rolereluser rolereluser : rolereluserList) {
			Role role = roleMapper.selectByPrimaryKey(rolereluser.getRoleId());
			roleList.add(role);
		}
		return roleList;
	}

}
