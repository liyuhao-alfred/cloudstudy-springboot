package com.cloudstudy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cloudstudy.bo.Course;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryParamDto;

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

	CourseDto findByTaskId(Integer taskId);

	CourseDto findByJobId(Integer jobId);

	CourseDto findByFileOriginId(Integer fileOriginId);

	List<CourseDto> findByStudentNo(String studentNo);

	List<CourseDto> findByTeacherNo(String teacherNo);

	List<CourseDto> find(CourseQueryParamDto courseQueryDto);

	List<CourseDto> generateDto(List<Course> courseList);
}
