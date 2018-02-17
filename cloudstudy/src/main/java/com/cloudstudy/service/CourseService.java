package com.cloudstudy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cloudstudy.bo.Course;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.CourseStudentDto;
import com.cloudstudy.dto.PageResultDto;

@SuppressWarnings("unused")
public interface CourseService {

	/**
	 * 教师或者管理员添加课程
	 * 
	 * @param courseDto
	 * @return
	 */
	CourseDto declare(CourseDto courseDto) throws IOException;

	void delete(Integer courseId) throws IOException;

	CourseDto update(CourseDto courseDto) throws IOException;

	CourseDto findById(Integer primaryKey);

	PageResultDto<List<CourseQueryDto>> find(CourseQueryParamDto courseQueryDto);

	PageResultDto<List<CourseStudentDto>> findForStudent(CourseQueryParamDto courseQueryDto);

}
