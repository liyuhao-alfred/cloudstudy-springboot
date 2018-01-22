package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.bo.Rolereluser;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.RoleExample;
import com.cloudstudy.bo.example.RolereluserExample;
import com.cloudstudy.bo.example.UserExample;
import com.cloudstudy.bo.example.UserExample.Criteria;
import com.cloudstudy.constant.RoleTypeConstant;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RolereluserMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolereluserMapper rolereluserMapper;

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
	public UserDto findUserByNo(String no) {
		User user = userMapper.selectByPrimaryKey(no);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);

		RolereluserExample rolereluserExample = new RolereluserExample();
		com.cloudstudy.bo.example.RolereluserExample.Criteria criteria = rolereluserExample.createCriteria();
		criteria.andUserNoEqualTo(no);
		List<Rolereluser> rolereluserList = rolereluserMapper.selectByExample(rolereluserExample);
		HashSet<Integer> roleTypeSet = new HashSet<Integer>();
		if (rolereluserList != null && !rolereluserList.isEmpty()) {
			for (Rolereluser rolereluser : rolereluserList) {
				roleTypeSet.add(rolereluser.getRoleId());
			}
		}
		userDto.setRoleType(roleTypeSet);

		return userDto;
	}

	@Override
	@LogPointcut(description = "通过条件查找用户", code = "find")
	public List<UserDto> find(UserQueryParamDto userQueryParamDto) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();

		String keyword = userQueryParamDto.getKeyword();
		Integer searchType = userQueryParamDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.account.getCode()) {
				criteria.andAccountLike(keyword);
			} else if (searchType == SearchType.email.getCode()) {
				criteria.andEmailLike(keyword);
			} else if (searchType == SearchType.adminName.getCode()) {
				criteria.andNameLike(keyword);
			} else if (searchType == SearchType.no.getCode()) {
				criteria.andNoLike(keyword);
			} else if (searchType == SearchType.phone.getCode()) {
				criteria.andPhoneLike(keyword);
			}
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
	public UserDto findUserByAccount(String account) {

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

		RolereluserExample rolereluserExample = new RolereluserExample();
		com.cloudstudy.bo.example.RolereluserExample.Criteria criteria1 = rolereluserExample.createCriteria();
		criteria1.andUserNoEqualTo(userDto.getNo());
		List<Rolereluser> rolereluserList = rolereluserMapper.selectByExample(rolereluserExample);
		HashSet<Integer> roleTypeSet = new HashSet<Integer>();
		if (rolereluserList != null && !rolereluserList.isEmpty()) {
			for (Rolereluser rolereluser : rolereluserList) {
				roleTypeSet.add(rolereluser.getRoleId());
			}
		}
		userDto.setRoleType(roleTypeSet);

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

	@Override
	public UserDto findAdminByNo(String no) {
		UserDto userDto = findUserByNo(no);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.adminType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findTeacherByNo(String no) {
		UserDto userDto = findUserByNo(no);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.teacherType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findStudentByNo(String no) {
		UserDto userDto = findUserByNo(no);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.studentType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findAdminByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.adminType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findTeacherByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.teacherType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findStudentByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		HashSet<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.studentType)) {
			return userDto;
		} else {
			return null;
		}
	}
}
