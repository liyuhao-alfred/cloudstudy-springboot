package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;

public interface GradeService {

	GradeDto addGradeByTeacher(GradeDto gradeDto);

	GradeDto editGradeByTeacher(GradeDto gradeDto);

	void deleteGradeByTeacher(Integer courseId);

	GradeDto findById(Integer id);

	List<GradeDto> findByTaskId(String taskId);

	List<GradeDto> findByJobId(String jobId);

	List<GradeDto> findByStudentNo(String studentNo);

	List<GradeDto> findByTeacherNo(String teacherNo);

	List<GradeDto> findByCourseId(String courseId);

	List<GradeDto> find(GradeQueryDto gradeQueryDto);

}
