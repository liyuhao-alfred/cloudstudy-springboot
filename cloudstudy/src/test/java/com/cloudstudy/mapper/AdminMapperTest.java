package com.cloudstudy.mapper;

import java.util.Date;

import javax.validation.Valid;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.mapper.BaseJunit4Test;

public class AdminMapperTest extends BaseJunit4Test {
	@Autowired
	AdminMapper adminMapper;

	@Test
	public void TestInsert() {
		Admin admin = new Admin();
		ddwfwe(admin);
		adminMapper.insert(admin);
	}

	public Admin ddwfwe(@Valid Admin admin) {

		admin.setAccount("88888888");
		admin.setCreateTime(new Date());
		admin.setEmail("88888888");
		admin.setLastModifyTime(new Date());
		admin.setLevel(1);
		admin.setName("88888888");
		admin.setNo("");
		admin.setPassword("88888888");
		admin.setPhone("88888888");
		admin.setSex(0);
		admin.setStatus(0);

		get(admin);
		return admin;
	}

	public Admin get(@Valid Admin admin) {
 
		return admin;
	}

}
