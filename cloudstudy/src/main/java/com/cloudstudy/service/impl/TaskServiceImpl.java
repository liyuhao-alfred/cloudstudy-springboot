package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.TaskExample;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.TaskQueryDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.TaskQueryDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToJobMapper;
import com.cloudstudy.mapper.FileToTaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private UserService userService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private FileToTaskMapper fileOriginToTaskMapper;
	@Autowired
	private FileToJobMapper fileOriginToJobMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private GradeService gradeService;

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
		com.cloudstudy.bo.JobExample.Criteria criteria = jobExample.createCriteria();
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
	public TaskDto findById(Integer primaryKey) {
		Task course = taskMapper.selectByPrimaryKey(primaryKey);
		if (course == null) {
			return null;
		}
		TaskDto taskDto = new TaskDto();
		BeanUtils.copyProperties(course, taskDto);
		return taskDto;
	}

	@Override
	public List<TaskDto> find(TaskQueryDto taskQueryDto) {
		TaskExample taskExample = new TaskExample();
		com.cloudstudy.bo.TaskExample.Criteria criteria = taskExample.createCriteria();

		String keyword = taskQueryDto.getKeyword();
		Integer searchType = taskQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {

			if (searchType == SearchType.homeworkName.getCode()) {
				criteria.andTitleLike("%" + keyword + "%");
			} else if (searchType == SearchType.courseId.getCode()) {
				criteria.andCourseIdEqualTo(Integer.valueOf(keyword));
			}

		}

		if (!StringUtils.isEmpty(taskQueryDto.getFromTime()) && !StringUtils.isEmpty(taskQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(taskQueryDto.getFromTime()),
					DateUtil.stringToDate(taskQueryDto.getToTime()));

		}

		Integer page = taskQueryDto.getPageDto().getCurrent();
		Integer rows = taskQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);

		List<Task> taskList = taskMapper.selectByExample(taskExample);
		return generateDto(taskList);
	}

	@Override
	public List<TaskDto> findByCourseId(Integer primaryKey) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		TaskQueryDto taskQueryDto = new TaskQueryDto();
		taskQueryDto.setKeyword(primaryKey + "");
		taskQueryDto.setSearchTypeSet(searchTypeSet);

		return find(taskQueryDto);
	}

	@Override
	public List<TaskDto> findByTeacherNo(String no) {
		List<CourseDto> courseDtoList = courseService.findByTeacherNo(no);
		if (courseDtoList == null || courseDtoList.isEmpty()) {
			return null;
		}

		List<Integer> taskDtoIdList = new ArrayList<Integer>();
		for (CourseDto courseDto : courseDtoList) {
			taskDtoIdList.add(courseDto.getId());
		}

		TaskExample taskExample = new TaskExample();
		com.cloudstudy.bo.TaskExample.Criteria criteria = taskExample.createCriteria();
		criteria.andCourseIdIn(taskDtoIdList);
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
	public List<TaskDto> findByStudentNo(String no) {

		List<GradeDto> gradeDtoList = gradeService.findByStudentNo(no);
		if (gradeDtoList == null || gradeDtoList.isEmpty()) {
			return null;
		}

		List<Integer> courseDtoIdList = new ArrayList<Integer>();
		for (GradeDto gradeDto : gradeDtoList) {
			courseDtoIdList.add(gradeDto.getCourseId());
		}

		TaskExample taskExample = new TaskExample();
		com.cloudstudy.bo.TaskExample.Criteria criteria = taskExample.createCriteria();
		criteria.andCourseIdIn(courseDtoIdList);
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
	public TaskDto findByJobId(Integer jobId) {
		Job job = jobMapper.selectByPrimaryKey(jobId);
		Task task = taskMapper.selectByPrimaryKey(job.getTaskId());
		return generateDto(task);
	}

	private List<Integer> getPrimaryKeyList(List<Task> taskList) {
		if (taskList == null || taskList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (Task task : taskList) {
			primaryKeyList.add(task.getId());
		}
		return primaryKeyList;
	}

	private List<TaskDto> generateDto(List<Task> taskList) {
		if (taskList == null || taskList.isEmpty()) {
			return new ArrayList<TaskDto>();
		}
		List<TaskDto> TaskDtoList = new ArrayList<TaskDto>();
		for (Task task : taskList) {
			TaskDto taskDto = generateDto(task);
			TaskDtoList.add(taskDto);
		}
		return TaskDtoList;
	}

	private List<Task> generate(List<TaskDto> taskDtoList) {
		if (taskDtoList == null || taskDtoList.isEmpty()) {
			return new ArrayList<Task>();
		}
		List<Task> TaskList = new ArrayList<Task>();
		for (TaskDto taskDto : taskDtoList) {
			Task task = generate(taskDto);
			TaskList.add(task);
		}
		return TaskList;
	}

	private TaskDto generateDto(Task task) {
		if (task == null) {
			throw new CloudStudyException();
		}
		TaskDto taskDto = new TaskDto();
		BeanUtils.copyProperties(task, taskDto);
		return taskDto;
	}

	private Task generate(TaskDto taskDto) {
		if (taskDto == null) {
			throw new CloudStudyException();
		}
		Task task = new Task();
		BeanUtils.copyProperties(taskDto, task);
		return task;
	}

}
