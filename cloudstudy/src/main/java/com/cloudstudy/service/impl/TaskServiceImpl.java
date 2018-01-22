package com.cloudstudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.dto.HomeworkQueryParamDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.FileMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private UserService userService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private CourserelteacherMapper courserelteacherMapper;
	@Autowired
	private CourserelstudentMapper courserelstudentMapper;
	@Autowired
	private FileMapper fileMapper;

	@Override
	public TaskDto add(TaskDto taskDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public TaskDto update(TaskDto taskDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskDto findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskDto> find(HomeworkQueryParamDto homeworkQueryParamDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
