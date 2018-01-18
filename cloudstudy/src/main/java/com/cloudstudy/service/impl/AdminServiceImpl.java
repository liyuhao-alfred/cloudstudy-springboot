package com.cloudstudy.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.bo.example.AdminExample;
import com.cloudstudy.bo.example.AdminExample.Criteria;
import com.cloudstudy.dto.AdminDto;
import com.cloudstudy.mapper.AdminMapper;
import com.cloudstudy.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public AdminDto saveAdmin(AdminDto adminDto) {
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminDto, admin);
		adminMapper.insert(admin);
		BeanUtils.copyProperties(admin, adminDto);
		return adminDto;
	}

	@Override
	public Admin loginAdmin(String account, String password) {
		AdminExample adminExample = new AdminExample();

		Criteria criteria = adminExample.createCriteria();
		if (!StringUtils.isBlank(account)) {
			criteria.andAccountEqualTo(account);
		}
		if (!StringUtils.isBlank(password)) {
			criteria.andPasswordEqualTo(password);
		}

		adminExample.setDistinct(true);
		adminExample.setOrderByClause("account");

		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if (adminList == null || adminList.isEmpty()) {
			return null;
		}
		return adminList.get(0);
	}

	@Override
	public Admin findAdmin(String no) {
		Admin admin = adminMapper.selectByPrimaryKey(no);
		return admin;
	}

	@Override
	public List<Admin> findAdmin(Admin admin, String keyword) {
		AdminExample adminExample = new AdminExample();

		Criteria criteria = adminExample.createCriteria();
		if (!StringUtils.isBlank(admin.getAccount())) {
			criteria.andAccountEqualTo(admin.getAccount());
		}
		if (!StringUtils.isBlank(admin.getPassword())) {
			criteria.andPasswordEqualTo(admin.getPassword());
		}

		adminExample.setDistinct(true);
		adminExample.setOrderByClause("account");

		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if (adminList == null || adminList.isEmpty()) {
			return null;
		}
		return adminList;
	}

	@Override
	public void deleteAdmin(String no) {
		adminMapper.deleteByPrimaryKey(no);
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		adminMapper.update(admin);
		return admin;
	}

}
