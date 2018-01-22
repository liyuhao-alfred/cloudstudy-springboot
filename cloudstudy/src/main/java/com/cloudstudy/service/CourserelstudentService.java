package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CourserelteacherQueryDto;
import com.cloudstudy.dto.CourserelstudentDto;
import com.cloudstudy.dto.CourserelstudentQueryDto;

public interface CourserelstudentService {

	/**
	 * 教师或者管理员添加课程
	 * 
	 * @param courserelstudentDto
	 * @return
	 */
	CourserelstudentDto add(CourserelstudentDto courserelstudentDto);

	void delete(Integer teacherCourseId);

	CourserelstudentDto update(CourserelstudentDto courserelstudentDto);

	/**
	 * 查找所有的课程
	 * 
	 * @param id
	 * @return
	 */
	CourserelstudentDto findById(Integer id);

	List<CourserelstudentDto> find(CourserelstudentQueryDto courserelstudentQueryDto);

}
