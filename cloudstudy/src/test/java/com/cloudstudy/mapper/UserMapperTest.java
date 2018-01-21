package com.cloudstudy.mapper;

import java.util.Date;

import javax.validation.Valid;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstudy.bo.User;
import com.cloudstudy.mapper.BaseJunit4Test;

public class UserMapperTest extends BaseJunit4Test {
	@Autowired
	UserMapper adminMapper;

	@Test
	public void TestInsert() {
		User user = new User();
		ddwfwe(user);
		adminMapper.insert(user);
	}

	public User ddwfwe(@Valid User user) {

		user.setAccount("88888888");
		user.setCreateTime(new Date());
		user.setEmail("88888888");
		user.setLastModifyTime(new Date());
		user.setName("88888888");
		user.setNo("");
		user.setPassword("88888888");
		user.setPhone("88888888");
		user.setSex(0);
		user.setStatus(0);

		get(user);
		return user;
	}

	public User get(@Valid User user) {

		return user;
	}

}
