package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.bo.Job;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.JobQueryDto;

public interface JobService {

	JobDto add(JobDto jobDto);

	void delete(Integer primaryKey);

	JobDto findById(Integer primaryKey);

	List<JobDto> findByCourseId(Integer courseId);

	List<JobDto> findByTaskId(Integer taskId);

	List<JobDto> findByTeacherNo(String teacherNo);

	List<JobDto> findByStudentNo(String studentNo);

	List<JobDto> find(JobQueryDto jobQueryDto);

	List<JobDto> generateDto(List<Job> jobList);

	JobDto findByFileOriginId(Integer fileOriginId);
}
