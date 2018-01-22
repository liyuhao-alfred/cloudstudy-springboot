package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;

public interface JobService {

	JobDto add(JobDto jobDto);

	void delete(Integer id);

	JobDto findById(Integer id);

	List<JobDto> findByCourseId(Integer id);

	List<JobDto> findByTaskId(Integer id);

	List<JobDto> findByTeacherNo(String no);

	List<JobDto> findByStudentNo(String no);

	List<JobDto> find(HomeworkQueryParamDto jobQueryParamDto);

}
