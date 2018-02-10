package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToCourseExample;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.UserDto;
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
import com.cloudstudy.util.Util;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
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

	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public CourseDto declare(CourseDto courseDto) throws IOException {

		Course course = generate(courseDto);
		courseMapper.insert(course);

		ArrayList<String> studyFileList = courseDto.getStudyFileList();
		if (studyFileList != null && !studyFileList.isEmpty()) {
			for (String fileId : studyFileList) {
				FileToCourse fileToCourse = new FileToCourse();
				fileToCourse.setCourseId(course.getId());
				fileToCourse.setFileId(Integer.valueOf(fileId));
				fileOriginService.add(fileToCourse);
			}
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

		List<Integer> primaryKeyList = new ArrayList<Integer>();
		if (FileOriginDtoList != null && !FileOriginDtoList.isEmpty()) {
			for (FileOriginDto fileOriginDto : FileOriginDtoList) {
				primaryKeyList.add(fileOriginDto.getId());
			}
		}

		fileOriginService.deleteByIdList(primaryKeyList);

		courseMapper.deleteByPrimaryKey(courseId);
	}

	@Override
	public CourseDto update(CourseDto courseDto) throws IOException {
		ArrayList<String> studyFileList = courseDto.getStudyFileList();
		if (studyFileList != null && !studyFileList.isEmpty()) {
			List<FileOriginDto> fileOriginDtoList = fileOriginService.findByCourseId(courseDto.getId(), false);
			if (fileOriginDtoList != null && !fileOriginDtoList.isEmpty()) {
				List<Integer> primaryKeyList = new ArrayList<Integer>();
				for (FileOriginDto fileOriginDto : fileOriginDtoList) {
					primaryKeyList.add(fileOriginDto.getId());
				}
				fileOriginService.deleteByIdList(primaryKeyList);
			}

			for (String fileId : studyFileList) {
				FileToCourse fileToCourse = new FileToCourse();
				fileToCourse.setCourseId(courseDto.getId());
				fileToCourse.setFileId(Integer.valueOf(fileId));
				fileOriginService.add(fileToCourse);
			}
		}

		userService.findTeacherByNo(courseDto.getTeacherNo());
		Course course = generate(courseDto);
		courseMapper.updateByPrimaryKeyWithBLOBs(course);

		return courseDto;
	}

	@Override
	public CourseDto findById(Integer primaryKey) {
		Course course = courseMapper.selectByPrimaryKey(primaryKey);
		if (course == null) {
			return null;
		}
		CourseDto courseDto = new CourseDto();
		BeanUtils.copyProperties(course, courseDto);
		return courseDto;
	}

	@Override
	public List<CourseDto> find(CourseQueryParamDto courseQueryDto) {

		CourseExample courseExample = new CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria = courseExample.createCriteria();

		String keyword = courseQueryDto.getKeyword();
		Integer searchType = courseQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.teacherName.getCode()) {

				UserExample userExample = new UserExample();
				Criteria criteria1 = userExample.createCriteria();
				criteria1.andNameLike("%" + keyword + "%");

				Integer page = courseQueryDto.getPageDto().getCurrent();
				Integer rows = courseQueryDto.getPageDto().getSize();
				PageHelper.startPage(page, rows);

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

		Integer page = courseQueryDto.getPageDto().getCurrent();
		Integer rows = courseQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);

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

	@Override
	public CourseDto findByTaskId(Integer taskId) {
		Task task = taskMapper.selectByPrimaryKey(taskId);
		Course course = courseMapper.selectByPrimaryKey(task.getCourseId());
		CourseDto courseDto = generateDto(course);
		return courseDto;
	}

	@Override
	public CourseDto findByJobId(Integer jobId) {
		Job job = jobMapper.selectByPrimaryKey(jobId);
		Task task = taskMapper.selectByPrimaryKey(job.getTaskId());
		Course course = courseMapper.selectByPrimaryKey(task.getCourseId());
		CourseDto courseDto = generateDto(course);
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
		List<CourseDto> CourseDtoList = generateDto(CourseList);

		return CourseDtoList;
	}

	@Override
	public List<CourseDto> findByTeacherNo(String teacherNo) {
		com.cloudstudy.bo.CourseExample CourseExample = new com.cloudstudy.bo.CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria3 = CourseExample.createCriteria();
		criteria3.andTeacherNoEqualTo(teacherNo);
		List<Course> CourseList = courseMapper.selectByExample(CourseExample);

		List<CourseDto> CourseDtoList = generateDto(CourseList);

		return CourseDtoList;
	}

	private List<Integer> getPrimaryKeyList(List<Course> courseList) {
		if (courseList == null || courseList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (Course course : courseList) {
			primaryKeyList.add(course.getId());
		}
		return primaryKeyList;
	}

	public List<CourseDto> generateDto(List<Course> courseList) {
		if (courseList == null || courseList.isEmpty()) {
			return new ArrayList<CourseDto>();
		}
		List<CourseDto> CourseDtoList = new ArrayList<CourseDto>();
		for (Course course : courseList) {
			CourseDto courseDto = generateDto(course);
			CourseDtoList.add(courseDto);
		}
		return CourseDtoList;
	}

	private List<Course> generate(List<CourseDto> courseDtoList) {
		if (courseDtoList == null || courseDtoList.isEmpty()) {
			return new ArrayList<Course>();
		}
		List<Course> CourseList = new ArrayList<Course>();
		for (CourseDto courseDto : courseDtoList) {
			Course course = generate(courseDto);
			CourseList.add(course);
		}
		return CourseList;
	}

	private CourseDto generateDto(Course course) {
		if (course == null) {
			throw new CloudStudyException();
		}
		CourseDto courseDto = new CourseDto();
		BeanUtils.copyProperties(course, courseDto);
		return courseDto;
	}

	private Course generate(CourseDto courseDto) {
		if (courseDto == null) {
			throw new CloudStudyException();
		}

		Course dbCourse = null;
		Course course = new Course();
		boolean isExist = false;
		if (courseDto.getId() != null) {
			dbCourse = courseMapper.selectByPrimaryKey(courseDto.getId());
			if (dbCourse != null) {
				isExist = true;
			}
		}

		BeanUtils.copyProperties(courseDto, course);

		if (courseDto.getStatus()) {// 状态
			course.setStatus(0);
		} else {
			course.setStatus(1);
		}

		course.setLastModifyTime(new Date());

		if (courseDto.getDateRangement() != null && courseDto.getDateRangement().size() == 2) {
			course.setBeginTime(DateUtil.stringToDateSpecial(courseDto.getDateRangement().get(0)));
			course.setEndTime(DateUtil.stringToDateSpecial(courseDto.getDateRangement().get(1)));
		} else {
			course.setBeginTime(new Date());
			course.setEndTime(DateUtil.dateAddMonth(new Date(), 3));
		}

		if (isExist) {
			return course;
		} else {
			course.setCreateTime(new Date());
			course.setAcceptNum(0);

			return course;
		}
	}

	@Override
	public CourseDto findByFileOriginId(Integer fileOriginId) {

		FileToCourseExample fileToCourseExample = new FileToCourseExample();// 设置课程信息
		FileToCourseExample.Criteria fileToCourseCriteria = fileToCourseExample.createCriteria();
		fileToCourseCriteria.andFileIdEqualTo(fileOriginId);
		List<FileToCourse> fileToCourseList = fileToCourseMapper.selectByExample(fileToCourseExample);
		if (fileToCourseList != null && !fileToCourseList.isEmpty()) {
			ArrayList<Integer> idList = new ArrayList<Integer>();
			for (FileToCourse fileToCourse : fileToCourseList) {
				idList.add(fileToCourse.getCourseId());
			}

			CourseExample courseExample = new CourseExample();
			CourseExample.Criteria criteria = courseExample.createCriteria();
			criteria.andIdIn(idList);
			List<Course> courseList = courseMapper.selectByExample(courseExample);
			return generateDto(courseList).get(0);
		}
		return null;
	}
}
