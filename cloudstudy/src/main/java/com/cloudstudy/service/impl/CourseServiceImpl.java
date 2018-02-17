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
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.HomeworkExample;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToCourseExample;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.RoleConstant;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.CourseStudentDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.dto.HomeworkDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.HomeworkMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToCourseMapper;
import com.cloudstudy.mapper.FileToHomeworkMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.HomeworkService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.cloudstudy.util.Util;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper CourseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Autowired
	private GradeService gradeService;
	@Autowired
	private FileOriginService fileOriginService;
	@Autowired
	private HomeworkMapper homeworkMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private FileToCourseMapper fileToCourseMapper;
	@Autowired
	private FileToHomeworkMapper fileToHomeworkMapper;
	@Autowired
	private HomeworkService HomeworkService;

	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public CourseDto declare(CourseDto CourseDto) throws IOException {

		Course Course = generate(CourseDto);
		CourseMapper.insert(Course);
		return CourseDto;
	}

	private void deleteFileByCourse(Integer CourseId) throws IOException {
		ArrayList<Integer> courseIdList = new ArrayList<Integer>();
		courseIdList.add(CourseId);

		FileOriginQueryParamDto FileOriginQueryParamDto = new FileOriginQueryParamDto();
		FileOriginQueryParamDto.setPageDto(null);
		FileOriginQueryParamDto.setCourseId(courseIdList);

		PageResultDto<List<FileOriginQueryDto>> FileOriginQueryPageResultDto = fileOriginService
				.find(FileOriginQueryParamDto);
		List<FileOriginQueryDto> content = FileOriginQueryPageResultDto.getContent();
		if (content != null && !content.isEmpty()) {
			for (FileOriginQueryDto fileOriginQueryDto : content) {
				fileOriginService.deleteById(fileOriginQueryDto.getId());
			}
		}
	}

	@Override
	public void delete(Integer CourseId) throws IOException {

		Course Course = CourseMapper.selectByPrimaryKey(CourseId);
		if (Course == null) {
			return;
		}

		deleteFileByCourse(CourseId);

		CourseMapper.deleteByPrimaryKey(CourseId);
	}

	@Override
	public CourseDto update(CourseDto CourseDto) throws IOException {
		userService.findUserByNo(CourseDto.getTeacherNo());
		Course Course = generate(CourseDto);
		CourseMapper.updateByPrimaryKeyWithBLOBs(Course);

		return CourseDto;
	}

	@Override
	public CourseDto findById(Integer primaryKey) {
		Course Course = CourseMapper.selectByPrimaryKey(primaryKey);
		if (Course == null) {
			return null;
		}
		CourseDto CourseDto = generateDto(Course);
		return CourseDto;
	}

	@Override
	public PageResultDto<List<CourseQueryDto>> find(CourseQueryParamDto CourseQueryParamDto) {

		CourseExample CourseExample = new CourseExample();
		CourseExample.Criteria CourseExampleCriteria = CourseExample.createCriteria();

		HashSet<Integer> courseIdSet = new HashSet<Integer>();
		String keyword = CourseQueryParamDto.getKeyword();
		if (keyword != null && !keyword.isEmpty()) {
			keyword = "%" + keyword + "%";
			CourseExampleCriteria.andNameLike(keyword);

			UserExample UserExample = new UserExample();
			UserExample.Criteria UserExamplecriteria = UserExample.createCriteria();
			UserExamplecriteria.andNameLike(keyword);
			List<User> userList = userMapper.selectByExample(UserExample);
			ArrayList<String> userNoList = new ArrayList<String>();
			if (userList != null && !userList.isEmpty()) {
				for (User user : userList) {
					userNoList.add(user.getNo());
				}

				CourseExample CourseExample11 = new CourseExample();
				CourseExample.Criteria CourseExamplecriteria11 = CourseExample.createCriteria();
				CourseExamplecriteria11.andTeacherNoIn(userNoList);
				List<Course> CourseList11 = CourseMapper.selectByExample(CourseExample11);
				if (CourseList11 != null && !CourseList11.isEmpty()) {
					for (Course course : CourseList11) {
						courseIdSet.add(course.getId());
					}
				}
			}
		}

		ArrayList<String> teacherNoList = CourseQueryParamDto.getTeacherNo();
		if (teacherNoList != null && !teacherNoList.isEmpty()) {
			CourseExample CourseExample22 = new CourseExample();
			CourseExample.Criteria Criteria22 = CourseExample22.createCriteria();
			Criteria22.andTeacherNoIn(teacherNoList);
			List<Course> CourseList22 = CourseMapper.selectByExample(CourseExample22);
			if (CourseList22 != null && !CourseList22.isEmpty()) {
				ArrayList<Integer> courseIdList22 = new ArrayList<Integer>();
				for (Course Course : CourseList22) {
					courseIdSet.add(Course.getId());
				}
			}
		}

		ArrayList<String> studentNoList = CourseQueryParamDto.getStudentNo();
		if (studentNoList != null && !studentNoList.isEmpty()) {
			GradeExample GradeExample = new GradeExample();
			GradeExample.Criteria Criteria = GradeExample.createCriteria();
			Criteria.andStudentNoIn(studentNoList);
			List<Grade> GeadeList = gradeMapper.selectByExample(GradeExample);

			if (GeadeList != null && !GeadeList.isEmpty()) {
				ArrayList<Integer> courseIdList33 = new ArrayList<Integer>();
				for (Grade grade : GeadeList) {
					courseIdSet.add(grade.getCourseId());
				}
			}
		}

		if (courseIdSet != null && !courseIdSet.isEmpty()) {
			CourseExampleCriteria.andIdIn(new ArrayList<Integer>(courseIdSet));
		} else if (teacherNoList != null && !teacherNoList.isEmpty()) {
			return new PageResultDto<List<CourseQueryDto>>((long) 0, new ArrayList<CourseQueryDto>());
		} else if (studentNoList != null && !studentNoList.isEmpty()) {
			return new PageResultDto<List<CourseQueryDto>>((long) 0, new ArrayList<CourseQueryDto>());
		}

		long total = CourseMapper.countByExample(CourseExample);

		if (CourseQueryParamDto.getPageDto() != null) {
			Integer page = CourseQueryParamDto.getPageDto().getCurrent();
			Integer rows = CourseQueryParamDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<Course> CourseList = CourseMapper.selectByExample(CourseExample);
		if (CourseList == null || CourseList.isEmpty()) {
			return new PageResultDto<List<CourseQueryDto>>((long) 0, new ArrayList<CourseQueryDto>());
		}
		List<CourseQueryDto> CourseQueryDtoList = new ArrayList<CourseQueryDto>();
		for (Course Course : CourseList) {
			CourseQueryDto CourseQueryDto2 = generateQueryDto(Course);
			CourseQueryDtoList.add(CourseQueryDto2);
		}
		return new PageResultDto<List<CourseQueryDto>>(total, CourseQueryDtoList);
	}

	@Override
	public PageResultDto<List<CourseStudentDto>> findForStudent(CourseQueryParamDto CourseQueryParamDto) {

		CourseExample CourseExample = new CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria CourseExamplecriteria = CourseExample.createCriteria();

		HashSet<Integer> courseIdSet = new HashSet<Integer>();
		String keyword = CourseQueryParamDto.getKeyword();
		if (keyword != null && !keyword.isEmpty()) {
			keyword = "%" + keyword + "%";
			CourseExamplecriteria.andNameLike(keyword);

			UserExample UserExample = new UserExample();
			com.cloudstudy.bo.UserExample.Criteria UserExamplecriteria = UserExample.createCriteria();
			UserExamplecriteria.andNameLike(keyword);

			List<User> userList = userMapper.selectByExample(UserExample);
			ArrayList<String> userNoList = new ArrayList<String>();
			if (userList != null && !userList.isEmpty()) {
				for (User user : userList) {
					userNoList.add(user.getNo());
				}

				CourseExample CourseExample11 = new CourseExample();
				CourseExample.Criteria CourseExamplecriteria11 = CourseExample.createCriteria();
				CourseExamplecriteria11.andTeacherNoIn(userNoList);
				List<Course> CourseList11 = CourseMapper.selectByExample(CourseExample11);
				if (CourseList11 != null && !CourseList11.isEmpty()) {
					for (Course course : CourseList11) {
						courseIdSet.add(course.getId());
					}
				}
			}
			if (courseIdSet != null && !courseIdSet.isEmpty()) {
				CourseExamplecriteria.andIdIn(new ArrayList<Integer>(courseIdSet));
			}
		}

		long total = CourseMapper.countByExample(CourseExample);

		if (CourseQueryParamDto.getPageDto() != null) {
			Integer page = CourseQueryParamDto.getPageDto().getCurrent();
			Integer rows = CourseQueryParamDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<Course> CourseList = CourseMapper.selectByExample(CourseExample);
		if (CourseList == null || CourseList.isEmpty()) {
			return null;
		}
		List<CourseStudentDto> CourseQueryDtoList = new ArrayList<CourseStudentDto>();
		for (Course Course : CourseList) {
			CourseStudentDto CourseQueryDto2 = generateCourseStudentDto(Course, CourseQueryParamDto.getStudentNo());
			CourseQueryDtoList.add(CourseQueryDto2);
		}
		return new PageResultDto<List<CourseStudentDto>>(total, CourseQueryDtoList);
	}

	private CourseStudentDto generateCourseStudentDto(Course Course, ArrayList<String> studentNo) {
		if (Course == null) {
			throw new CloudStudyException();
		}
		CourseStudentDto CourseStudentDto = new CourseStudentDto();
		BeanUtils.copyProperties(Course, CourseStudentDto);

		if (Course.getStatus().equals(0)) {
			CourseStudentDto.setStatus(true);
			CourseStudentDto.setStatusMemo("进行中");
		} else {
			CourseStudentDto.setStatus(false);
			CourseStudentDto.setStatusMemo("已结束");
		}

		UserDto teacher = userService.findUserByNo(CourseStudentDto.getTeacherNo());
		CourseStudentDto.setTeacher(teacher);

		String beginTime;
		try {
			beginTime = DateUtil.dateToString(Course.getBeginTime());
		} catch (Exception e) {
			beginTime = DateUtil.getNowDateAsString();
		}
		CourseStudentDto.setBeginTime(beginTime);

		String endTime;
		try {
			endTime = DateUtil.dateToString(Course.getEndTime());
		} catch (Exception e) {
			endTime = DateUtil.getNowDateAsString();
		}
		CourseStudentDto.setBeginTime(endTime);

		String createTime;
		try {
			createTime = DateUtil.dateToString(Course.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		CourseStudentDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(Course.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		CourseStudentDto.setLastModifyTime(lastModifyTime);

		if (CourseStudentDto.getDescription() == null || CourseStudentDto.getDescription().isEmpty()) {
			CourseStudentDto.setDescription("暂无个人介绍...");
		}

		com.cloudstudy.bo.GradeExample GradeExample = new com.cloudstudy.bo.GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria3 = GradeExample.createCriteria();
		criteria3.andStudentNoIn(studentNo);
		criteria3.andCourseIdEqualTo(CourseStudentDto.getId());
		Long count = gradeMapper.countByExample(GradeExample);
		if (count == null || count == 0) {
			CourseStudentDto.setIsCommit(false);
		} else {
			CourseStudentDto.setIsCommit(true);
		}

		return CourseStudentDto;

	}

	private CourseQueryDto generateQueryDto(Course Course) {
		if (Course == null) {
			throw new CloudStudyException();
		}
		CourseQueryDto CourseQueryDto = new CourseQueryDto();
		BeanUtils.copyProperties(Course, CourseQueryDto);

		if (Course.getStatus().equals(0)) {
			CourseQueryDto.setStatus(true);
			CourseQueryDto.setStatusMemo("进行中");
		} else {
			CourseQueryDto.setStatus(false);
			CourseQueryDto.setStatusMemo("已结束");
		}

		UserDto teacher = userService.findUserByNo(CourseQueryDto.getTeacherNo());
		CourseQueryDto.setTeacher(teacher);

		String beginTime;
		try {
			beginTime = DateUtil.dateToString(Course.getBeginTime());
		} catch (Exception e) {
			beginTime = DateUtil.getNowDateAsString();
		}
		CourseQueryDto.setBeginTime(beginTime);

		String endTime;
		try {
			endTime = DateUtil.dateToString(Course.getEndTime());
		} catch (Exception e) {
			endTime = DateUtil.getNowDateAsString();
		}
		CourseQueryDto.setBeginTime(endTime);

		String createTime;
		try {
			createTime = DateUtil.dateToString(Course.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		CourseQueryDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(Course.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		CourseQueryDto.setLastModifyTime(lastModifyTime);

		if (CourseQueryDto.getDescription() == null || CourseQueryDto.getDescription().isEmpty()) {
			CourseQueryDto.setDescription("暂无个人介绍...");
		}

		return CourseQueryDto;
	}

	private CourseDto generateDto(Course Course) {
		if (Course == null) {
			throw new CloudStudyException();
		}
		CourseDto CourseDto = new CourseDto();
		BeanUtils.copyProperties(Course, CourseDto);

		if (Course.getStatus() == 0) {
			CourseDto.setStatus(true);
		} else {
			CourseDto.setStatus(false);
		}

		CourseDto.setCreateTime(DateUtil.dateToString(Course.getCreateTime()));
		CourseDto.setLastModifyTime(DateUtil.dateToString(Course.getLastModifyTime()));

		ArrayList<String> dateRangement = new ArrayList<String>();
		dateRangement.add(DateUtil.dateToString(Course.getBeginTime()));
		dateRangement.add(DateUtil.dateToString(Course.getEndTime()));
		CourseDto.setDateRangement(dateRangement);

		return CourseDto;
	}

	private Course generate(CourseDto CourseDto) {
		if (CourseDto == null) {
			throw new CloudStudyException();
		}

		Course Course = new Course();
		if (CourseDto.getId() != null) {
			Course dbCourse = CourseMapper.selectByPrimaryKey(CourseDto.getId());
			if (dbCourse == null) {
				throw new CloudStudyException();
			}
		}

		BeanUtils.copyProperties(CourseDto, Course);

		if (CourseDto.getStatus()) {// 状态
			Course.setStatus(0);
		} else {
			Course.setStatus(1);
		}

		Course.setLastModifyTime(new Date());

		if (CourseDto.getDateRangement() != null && CourseDto.getDateRangement().size() == 2) {
			Course.setBeginTime(DateUtil.stringToDateSpecial(CourseDto.getDateRangement().get(0)));
			Course.setEndTime(DateUtil.stringToDateSpecial(CourseDto.getDateRangement().get(1)));
		} else {
			Course.setBeginTime(new Date());
			Course.setEndTime(DateUtil.dateAddMonth(new Date(), 3));
		}

		if (CourseDto.getCreateTime() == null) {
			Course.setCreateTime(new Date());
		} else {
			Course.setCreateTime(DateUtil.stringToDateSpecial(CourseDto.getCreateTime()));
		}

		if (Course.getAcceptNum() == null) {
			Course.setAcceptNum(0);
		}

		return Course;

	}

}
