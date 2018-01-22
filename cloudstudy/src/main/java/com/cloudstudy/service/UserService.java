package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;

public interface UserService {

	UserDto save(UserDto userDto);

	void deleteByNo(String no);

	UserDto update(UserDto userDto);

	UserDto findUserByNo(String no);

	UserDto findAdminByNo(String no);

	UserDto findTeacherByNo(String no);

	UserDto findStudentByNo(String no);

	UserDto findUserByAccount(String account);

	UserDto findAdminByAccount(String account);

	UserDto findTeacherByAccount(String account);

	UserDto findStudentByAccount(String account);

	List<UserDto> find(UserQueryParamDto userQueryParamDto);

	UserDto login(String account, String password);
}
