package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.UserExample;
import com.cloudstudy.bo.example.UserExample.Criteria;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	@LogPointcut(description = "添加用户", code = "save")
	public UserDto save(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		userMapper.insert(user);
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override
	@LogPointcut(description = "删除用户", code = "deleteByNo")
	public void deleteByNo(String no) {
		userMapper.deleteByPrimaryKey(no);
	}

	@Override
	@LogPointcut(description = "更新用户", code = "update")
	public UserDto update(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		userMapper.update(user);
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override
	@LogPointcut(description = "通过主键查找用户", code = "findByno")
	public UserDto findByno(String no) {
		User user = userMapper.selectByPrimaryKey(no);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override
	@LogPointcut(description = "通过条件查找用户", code = "find")
	public List<UserDto> find(UserQueryParamDto userQueryParamDto, String keyword) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();

		if (!StringUtils.isEmpty(userQueryParamDto.getAccount())) {
			criteria.andAccountLike(keyword);
		} else if (!StringUtils.isEmpty(userQueryParamDto.getEmail())) {
			criteria.andEmailLike(keyword);
		} else if (!StringUtils.isEmpty(userQueryParamDto.getName())) {
			criteria.andNameLike(keyword);
		} else if (!StringUtils.isEmpty(userQueryParamDto.getNo())) {
			criteria.andNoLike(keyword);
		} else if (!StringUtils.isEmpty(userQueryParamDto.getPhone())) {
			criteria.andPhoneLike(keyword);
		}

		if (!StringUtils.isEmpty(userQueryParamDto.getFromTime())
				&& !StringUtils.isEmpty(userQueryParamDto.getToTime())) {
			criteria.andRegistTimeBetween(DateUtil.stringToDate(userQueryParamDto.getFromTime()),
					DateUtil.stringToDate(userQueryParamDto.getToTime()));

		}
		if (userQueryParamDto.getSex() != null && userQueryParamDto.getSex() == 0) {
			criteria.andSexEqualTo(userQueryParamDto.getSex());
		}

		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	@Override
	@LogPointcut(description = "通过账号查找用户", code = "findByAccount")
	public UserDto findByAccount(String account) {

		UserExample userExample = new UserExample();

		Criteria criteria = userExample.createCriteria();
		criteria.andAccountEqualTo(account);

		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		User user = userList.get(0);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override
	@LogPointcut(description = "登录用户", code = "login")
	public UserDto login(String account, String password) {
		UserExample userExample = new UserExample();

		Criteria criteria = userExample.createCriteria();
		if (!StringUtils.isBlank(account)) {
			criteria.andAccountEqualTo(account);
		}
		if (!StringUtils.isBlank(password)) {
			criteria.andPasswordEqualTo(password);
		}

		userExample.setDistinct(true);
		userExample.setOrderByClause("account");

		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		User user = userList.get(0);
		UserDto UserDto = new UserDto();
		BeanUtils.copyProperties(user, UserDto);
		return UserDto;
	}
}
