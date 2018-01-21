package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;

public interface UserService {

	UserDto save(UserDto userDto);

	void deleteByNo(String no);

	UserDto update(UserDto userDto);

	UserDto findByNo(String no);

	List<UserDto> find(UserQueryParamDto userQueryParamDto);

	/**
	 * 
	 * 
	 * @param account
	 * @return
	 */
	UserDto findByAccount(String account);

	UserDto login(String account, String password);
}
