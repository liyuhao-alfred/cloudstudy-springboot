package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;

public interface TaskService {

	TaskDto add(TaskDto taskDto);

	void delete(Integer id);

	TaskDto update(TaskDto taskDto);

	TaskDto findById(Integer id);

	List<TaskDto> find(HomeworkQueryParamDto homeworkQueryParamDto);

}
