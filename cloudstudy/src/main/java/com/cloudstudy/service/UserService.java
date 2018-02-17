package com.cloudstudy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cloudstudy.bo.User;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.UserQueryParamDto;

@SuppressWarnings("unused")
public interface UserService {

	UserDto save(UserDto userDto);

	void deleteByNo(String userNo) throws IOException;

	UserDto update(UserDto userDto);

	UserDto findUserByNo(String no);

	UserDto changePassword(String account, String password);

	UserDto findUserByAccount(String account);

	PageResultDto<List<UserQueryDto>> find(UserQueryParamDto userQueryDto);

	UserDto login(String account, String password);

}
