package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;

public interface CourseService {

	/**
	 * 教师或者管理员添加课程
	 * 
	 * @param courseDto
	 * @return
	 */
	CourseDto add(CourseDto courseDto) throws IOException;

	void delete(Integer courseId) throws IOException;

	CourseDto update(CourseDto courseDto);

	CourseDto findById(Integer id);

	CourseDto findByTaskId(Integer taskId);

	CourseDto findByJobId(Integer jobId);

	List<CourseDto> findByStudentNo(String studentNo);

	List<CourseDto> findByTeacherNo(String teacherNo);

	List<CourseDto> find(CourseQueryDto courseQueryDto);
}
