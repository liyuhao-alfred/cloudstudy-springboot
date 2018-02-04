package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.TaskQueryDto;

public interface TaskService {

	TaskDto add(TaskDto taskDto);

	void delete(Integer primaryKey);

	TaskDto update(TaskDto taskDto);

	TaskDto findById(Integer primaryKey);

	TaskDto findByJobId(Integer jobId);

	List<TaskDto> findByCourseId(Integer courseId);

	List<TaskDto> findByTeacherNo(String teacherNo);

	List<TaskDto> findByStudentNo(String studentNo);

	List<TaskDto> find(TaskQueryDto taskQueryDto);

}
