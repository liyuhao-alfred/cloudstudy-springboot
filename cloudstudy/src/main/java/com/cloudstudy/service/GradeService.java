package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CalResultDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.GradeQueryParamDto;
import com.cloudstudy.dto.PageResultDto;

public interface GradeService {

	GradeDto changeCommit(String studentNo, Integer courseId);

	GradeDto changeGrade(Integer gradePoint, Integer gradeId);

	GradeDto findById(Integer primaryKey);

	PageResultDto<List<GradeQueryDto>> find(GradeQueryParamDto GradeQueryParamDto);

	CalResultDto calByStudent(String studentNo);

	CalResultDto calByCourse(Integer courseId);

}
