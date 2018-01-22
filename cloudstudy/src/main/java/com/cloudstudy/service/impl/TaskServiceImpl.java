package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Courserelteacher;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.CourserelstudentExample;
import com.cloudstudy.bo.example.CourserelteacherExample;
import com.cloudstudy.bo.example.JobExample;
import com.cloudstudy.bo.example.TaskExample;
import com.cloudstudy.bo.example.UserExample;
import com.cloudstudy.bo.example.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourserelteacherDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.StudyfileMapper;
import com.cloudstudy.mapper.StudyfilereljobMapper;
import com.cloudstudy.mapper.StudyfilereltaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

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
	private StudyfileMapper fileMapper;
	@Autowired
	private StudyfilereltaskMapper studyfilereltaskMapper;
	@Autowired
	private StudyfilereljobMapper studyfilereljobMapper;

	@Override
	public TaskDto add(TaskDto taskDto) {
		Task task = new Task();
		BeanUtils.copyProperties(taskDto, task);
		taskMapper.insert(task);

		return taskDto;
	}

	@Override
	public void delete(Integer taskId) {
		Task task = taskMapper.selectByPrimaryKey(taskId);
		if (task == null) {
			return;
		}
		taskMapper.deleteByPrimaryKey(taskId);

		JobExample jobExample = new JobExample();
		com.cloudstudy.bo.example.JobExample.Criteria criteria = jobExample.createCriteria();
		criteria.andIdEqualTo(taskId);
		jobMapper.deleteByExample(jobExample);

	}

	@Override
	public TaskDto update(TaskDto taskDto) {

		Task task = new Task();
		BeanUtils.copyProperties(taskDto, task);
		taskMapper.updateByPrimaryKeyWithBLOBs(task);

		return taskDto;
	}

	@Override
	public TaskDto findById(Integer id) {
		Task course = taskMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		TaskDto taskDto = new TaskDto();
		BeanUtils.copyProperties(course, taskDto);
		return taskDto;
	}

	@Override
	public List<TaskDto> find(HomeworkQueryParamDto homeworkQueryParamDto) {
		TaskExample taskExample = new TaskExample();
		com.cloudstudy.bo.example.TaskExample.Criteria criteria = taskExample.createCriteria();

		String keyword = homeworkQueryParamDto.getKeyword();
		Integer searchType = homeworkQueryParamDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {

		}

		if (!StringUtils.isEmpty(homeworkQueryParamDto.getFromTime())
				&& !StringUtils.isEmpty(homeworkQueryParamDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(homeworkQueryParamDto.getFromTime()),
					DateUtil.stringToDate(homeworkQueryParamDto.getToTime()));

		}

		PageHelper.startPage(homeworkQueryParamDto.getPageDto().getPage(),
				homeworkQueryParamDto.getPageDto().getRows());
		List<Task> taskList = taskMapper.selectByExample(taskExample);
		if (taskList == null || taskList.isEmpty()) {
			return null;
		}

		List<TaskDto> taskDtoList = new ArrayList<TaskDto>();
		for (Task task : taskList) {
			TaskDto taskDto = new TaskDto();
			BeanUtils.copyProperties(task, taskDto);
			taskDtoList.add(taskDto);
		}
		return taskDtoList;
	}

	@Override
	public List<TaskDto> findByCourseId(Integer id) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(id + "");
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

	@Override
	public List<TaskDto> findByTeacherNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(no);
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

	@Override
	public List<TaskDto> findByStudentNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(no);
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

}
