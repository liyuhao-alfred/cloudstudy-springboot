package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryParamDto;

public interface GradeService {

	GradeDto addGradeByTeacher(GradeDto gradeDto);

	GradeDto editGradeByTeacher(GradeDto gradeDto);

	void deleteGradeByTeacher(Integer courseId);

	GradeDto findById(Integer primaryKey);

	List<GradeDto> findByStudentNo(String studentNo);

	List<GradeDto> findByTeacherNo(String teacherNo);

	List<GradeDto> findByCourseId(Integer courseId);

	List<GradeDto> find(GradeQueryParamDto gradeQueryDto);

}
