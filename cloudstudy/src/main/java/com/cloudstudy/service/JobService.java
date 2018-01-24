package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.HomeworkQueryDto;

public interface JobService {

	JobDto add(JobDto jobDto);

	void delete(Integer id);

	JobDto findById(Integer id);

	List<JobDto> findByCourseId(Integer courseId);

	List<JobDto> findByTaskId(Integer taskId);

	List<JobDto> findByTeacherNo(String teacherNo);

	List<JobDto> findByStudentNo(String studentNo);

	List<JobDto> find(HomeworkQueryDto jobQueryDto);

}
