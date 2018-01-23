package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Courserelteacher;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.CourserelstudentExample;
import com.cloudstudy.bo.example.CourserelteacherExample;
import com.cloudstudy.bo.example.UserExample;
import com.cloudstudy.bo.example.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourserelteacherDto;
import com.cloudstudy.dto.CourserelteacherQueryDto;
import com.cloudstudy.dto.StudyfileDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourserelteacherService;
import com.cloudstudy.service.StudyfileService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class CourserelteacherServiceImpl implements CourserelteacherService {

	@Autowired
	private CourserelteacherMapper courserelteacherMapper;
	@Autowired
	private CourserelstudentMapper courserelstudentMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private StudyfileService studyfileService;

	@Override
	public CourserelteacherDto add(CourserelteacherDto courserelteacherDto) throws IOException {

		Courserelteacher courserelteacher = new Courserelteacher();
		BeanUtils.copyProperties(courserelteacherDto, courserelteacher);
		courserelteacherMapper.insert(courserelteacher);

		File studyFile = courserelteacherDto.getStudyFile();
		if (studyFile != null && studyFile.length() > 0) {
			StudyfileDto fileDto = new StudyfileDto(studyFile, courserelteacher.getId(), null, null);
			studyfileService.add(fileDto);
		}
		return courserelteacherDto;
	}

	@Override
	public void delete(Integer courserelteacherId) throws IOException {

		Courserelteacher courserelteacher = courserelteacherMapper.selectByPrimaryKey(courserelteacherId);
		if (courserelteacher == null) {
			return;
		}
		courserelteacherMapper.deleteByPrimaryKey(courserelteacherId);

		CourserelstudentExample courserelstudentExample = new CourserelstudentExample();
		com.cloudstudy.bo.example.CourserelstudentExample.Criteria criteria = courserelstudentExample.createCriteria();
		criteria.andCourserelteacherIdEqualTo(courserelteacherId);
		courserelstudentMapper.deleteByExample(courserelstudentExample);

		studyfileService.deleteByCourserelteacherId(courserelteacherId);
	}

	@Override
	public CourserelteacherDto update(CourserelteacherDto courserelteacherDto) {
		userService.findTeacherByNo(courserelteacherDto.getTeacherNo());

		Courserelteacher courserelteacher = new Courserelteacher();
		BeanUtils.copyProperties(courserelteacherDto, courserelteacher);
		courserelteacherMapper.updateByPrimaryKeyWithBLOBs(courserelteacher);

		return courserelteacherDto;
	}

	@Override
	public CourserelteacherDto findById(Integer id) {
		Courserelteacher course = courserelteacherMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		CourserelteacherDto courserelteacherDto = new CourserelteacherDto();
		BeanUtils.copyProperties(course, courserelteacherDto);
		return courserelteacherDto;
	}

	@Override
	public List<CourserelteacherDto> find(CourserelteacherQueryDto courseQueryParamDto) {

		CourserelteacherExample courserelteacherExample = new CourserelteacherExample();
		com.cloudstudy.bo.example.CourserelteacherExample.Criteria criteria = courserelteacherExample.createCriteria();

		String keyword = courseQueryParamDto.getKeyword();
		Integer searchType = courseQueryParamDto.getSearchType();
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

		if (!StringUtils.isEmpty(courseQueryParamDto.getFromTime())
				&& !StringUtils.isEmpty(courseQueryParamDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(courseQueryParamDto.getFromTime()),
					DateUtil.stringToDate(courseQueryParamDto.getToTime()));

		}

		PageHelper.startPage(courseQueryParamDto.getPageDto().getPage(), courseQueryParamDto.getPageDto().getRows());
		List<Courserelteacher> courserelteacherList = courserelteacherMapper.selectByExample(courserelteacherExample);
		if (courserelteacherList == null || courserelteacherList.isEmpty()) {
			return null;
		}

		List<CourserelteacherDto> courserelteacherDtoList = new ArrayList<CourserelteacherDto>();
		for (Courserelteacher courserelteacher : courserelteacherList) {
			CourserelteacherDto courserelteacherDto = new CourserelteacherDto();
			BeanUtils.copyProperties(courserelteacher, courserelteacherDto);
			courserelteacherDtoList.add(courserelteacherDto);
		}
		return courserelteacherDtoList;
	}

	@Override
	public List<CourserelteacherDto> findByTeacherNo(String teacherNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
