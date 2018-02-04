package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.UserQueryParamDto;

public interface UserService {

	UserDto save(UserDto userDto);

	void deleteByNo(String no) throws IOException;

	UserDto update(UserDto userDto);

	UserDto findUserByNo(String no);

	UserDto findAdminByNo(String no);

	UserDto findTeacherByNo(String no);

	UserDto findStudentByNo(String no);

	UserDto findUserByAccount(String account);

	UserDto findAdminByAccount(String account);

	UserDto findTeacherByAccount(String account);

	UserDto findStudentByAccount(String account);

	UserDto findTeacherByCourseId(Integer courseId);

	List<UserDto> findStudentByCourseId(Integer courseId);

	UserDto findTeacherByTaskId(Integer taskId);

	UserDto findStudentByJobId(Integer jobId);

	PageResultDto<List<UserQueryDto>> find(UserQueryParamDto userQueryDto);

	UserDto login(String account, String password);
}
