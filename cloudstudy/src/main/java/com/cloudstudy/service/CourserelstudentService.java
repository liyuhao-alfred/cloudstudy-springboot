package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CourserelteacherQueryDto;
import com.cloudstudy.dto.CourserelstudentDto;
import com.cloudstudy.dto.CourserelstudentQueryDto;

public interface CourserelstudentService {

	CourserelstudentDto addGradeByTeacher(CourserelstudentDto courserelstudentDto);

	CourserelstudentDto editGradeByTeacher(CourserelstudentDto courserelstudentDto);

	void deleteGradeByTeacher(Integer teacherCourseId);

	CourserelstudentDto findById(Integer id);

	List<CourserelstudentDto> findByStudentNo(String studentNo);

	List<CourserelstudentDto> find(CourserelstudentQueryDto courserelstudentQueryDto);

}
