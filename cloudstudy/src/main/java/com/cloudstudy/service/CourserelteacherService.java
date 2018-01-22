package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CourserelteacherDto;
import com.cloudstudy.dto.CourserelteacherQueryDto;

public interface CourserelteacherService {

	/**
	 * 教师或者管理员添加课程
	 * 
	 * @param courserelteacherDto
	 * @return
	 */
	CourserelteacherDto add(CourserelteacherDto courserelteacherDto);

	void delete(Integer teacherCourseId);

	CourserelteacherDto update(CourserelteacherDto courserelteacherDto);

	/**
	 * 查找所有的课程
	 * 
	 * @param id
	 * @return
	 */
	CourserelteacherDto findById(Integer id);

	List<CourserelteacherDto> findByTeacherNo(String teacherNo);

	List<CourserelteacherDto> find(CourserelteacherQueryDto courseQueryParamDto);

}
