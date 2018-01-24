package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToCourseMapper;
import com.cloudstudy.mapper.FileToJobMapper;
import com.cloudstudy.mapper.FileToTaskMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.cloudstudy.util.FileUtil;
import com.github.pagehelper.PageHelper;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private FileOriginService fileOriginService;

	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private FileToCourseMapper fileToCourseMapper;
	@Autowired
	private FileToTaskMapper fileToTaskMapper;
	@Autowired
	private FileToJobMapper fileToJobMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private JobService jobService;
	@Autowired
	private CourseService courseService;
	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public CourseDto add(CourseDto courseDto) throws IOException {

		Course course = new Course();
		BeanUtils.copyProperties(courseDto, course);
		courseMapper.insert(course);

		File studyFile = courseDto.getFileOrigin();
		if (studyFile != null && studyFile.length() > 0) {
			FileOriginDto fileDto = new FileOriginDto(studyFile, course.getId(), null, null);
			fileOriginService.add(fileDto);
		}
		return courseDto;
	}

	@Override
	public void delete(Integer courseId) throws IOException {

		Course course = courseMapper.selectByPrimaryKey(courseId);
		if (course == null) {
			return;
		}

		List<FileOriginDto> FileOriginDtoList = fileOriginService.findByCourseId(courseId, true);

		List<Integer> idList = new ArrayList<Integer>();
		if (FileOriginDtoList != null) {
			for (FileOriginDto fileOriginDto : FileOriginDtoList) {
				idList.add(fileOriginDto.getId());
			}
		}

		fileOriginService.deleteByIdList(idList);

		courseMapper.deleteByPrimaryKey(courseId);
	}

	@Override
	public CourseDto update(CourseDto courseDto) {
		userService.findTeacherByNo(courseDto.getTeacherNo());

		Course course = new Course();
		BeanUtils.copyProperties(courseDto, course);
		courseMapper.updateByPrimaryKeyWithBLOBs(course);

		return courseDto;
	}

	@Override
	public CourseDto findById(Integer id) {
		Course course = courseMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		CourseDto courseDto = new CourseDto();
		BeanUtils.copyProperties(course, courseDto);
		return courseDto;
	}

	@Override
	public List<CourseDto> find(CourseQueryDto courseQueryDto) {

		CourseExample courseExample = new CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria = courseExample.createCriteria();

		String keyword = courseQueryDto.getKeyword();
		Integer searchType = courseQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.teacherName.getCode()) {

				UserExample userExample = new UserExample();
				Criteria criteria1 = userExample.createCriteria();
				criteria1.andNameLike(keyword);

				List<User> userList = userMapper.selectByExample(userExample);
				if (userList == null || userList.isEmpty()) {
					return null;
				}

				HashSet<String> valuesSet = new HashSet<String>();
				for (User user : userList) {
					valuesSet.add(user.getNo());
				}
				criteria.andTeacherNoIn(new ArrayList<String>(valuesSet));
			} else if (searchType == SearchType.courseName.getCode()) {
				criteria.andNameLike("%" + keyword + "%");
			}
		}

		if (!StringUtils.isEmpty(courseQueryDto.getFromTime()) && !StringUtils.isEmpty(courseQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(courseQueryDto.getFromTime()),
					DateUtil.stringToDate(courseQueryDto.getToTime()));

		}

		PageHelper.startPage(courseQueryDto.getPageDto().getPage(), courseQueryDto.getPageDto().getRows());
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		if (courseList == null || courseList.isEmpty()) {
			return null;
		}

		List<CourseDto> courseDtoList = new ArrayList<CourseDto>();
		for (Course course : courseList) {
			CourseDto courseDto = new CourseDto();
			BeanUtils.copyProperties(course, courseDto);
			courseDtoList.add(courseDto);
		}
		return courseDtoList;
	}

	private List<CourseDto> generate(List<Course> CourseList) {
		List<CourseDto> CourseDtoList = new ArrayList<CourseDto>();
		if (CourseList != null) {
			for (Course course : CourseList) {
				CourseDto CourseDto = generate(course);
				CourseDtoList.add(CourseDto);
			}
		}
		return CourseDtoList;
	}

	private CourseDto generate(Course course) {
		if (course == null) {
			throw new CloudStudyException();
		}

		CourseDto CourseDto = new CourseDto();
		BeanUtils.copyProperties(course, CourseDto);
		return CourseDto;
	}

	@Override
	public CourseDto findByTaskId(Integer taskId) {
		Task task = taskMapper.selectByPrimaryKey(taskId);
		Course course = courseMapper.selectByPrimaryKey(task.getCourseId());
		CourseDto courseDto = generate(course);
		return courseDto;
	}

	@Override
	public CourseDto findByJobId(Integer jobId) {
		Job job = jobMapper.selectByPrimaryKey(jobId);
		Task task = taskMapper.selectByPrimaryKey(job.getTaskId());
		Course course = courseMapper.selectByPrimaryKey(task.getCourseId());
		CourseDto courseDto = generate(course);
		return courseDto;
	}

	@Override
	public List<CourseDto> findByStudentNo(String studentNo) {
		com.cloudstudy.bo.GradeExample GradeExample = new com.cloudstudy.bo.GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria3 = GradeExample.createCriteria();
		criteria3.andStudentNoEqualTo(studentNo);
		List<Grade> GradeList = gradeMapper.selectByExample(GradeExample);

		List<Integer> courseIdList = new ArrayList<Integer>();
		for (Grade grade : GradeList) {
			courseIdList.add(grade.getCourseId());
		}

		com.cloudstudy.bo.CourseExample CourseExample = new com.cloudstudy.bo.CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria4 = CourseExample.createCriteria();
		criteria4.andIdIn(courseIdList);
		List<Course> CourseList = courseMapper.selectByExample(CourseExample);
		List<CourseDto> CourseDtoList = generate(CourseList);

		return CourseDtoList;
	}

	@Override
	public List<CourseDto> findByTeacherNo(String teacherNo) {
		com.cloudstudy.bo.CourseExample CourseExample = new com.cloudstudy.bo.CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria3 = CourseExample.createCriteria();
		criteria3.andTeacherNoEqualTo(teacherNo);
		List<Course> CourseList = courseMapper.selectByExample(CourseExample);

		List<CourseDto> CourseDtoList = generate(CourseList);

		return CourseDtoList;
	}

}
