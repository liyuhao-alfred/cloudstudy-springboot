package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Courserelstudent;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.example.CourserelstudentExample;
import com.cloudstudy.bo.example.UserExample;
import com.cloudstudy.bo.example.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourserelteacherQueryDto;
import com.cloudstudy.dto.CourserelstudentDto;
import com.cloudstudy.dto.CourserelstudentQueryDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourserelstudentService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class CourserelstudentServiceImpl implements CourserelstudentService {

	@Autowired
	private CourserelteacherMapper courserelteacherMapper;
	@Autowired
	private CourserelstudentMapper courserelstudentMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Override
	public CourserelstudentDto add(CourserelstudentDto courserelstudentDto) {
		userService.findTeacherByNo(courserelstudentDto.getStudentNo());

		Courserelstudent courserelstudent = new Courserelstudent();
		BeanUtils.copyProperties(courserelstudentDto, courserelstudent);
		courserelstudentMapper.insert(courserelstudent);

		return courserelstudentDto;
	}

	@Override
	public void delete(Integer courserelstudentId) {

		Courserelstudent courserelstudent = courserelstudentMapper.selectByPrimaryKey(courserelstudentId);
		if (courserelstudent == null) {
			return;
		}
		courserelstudentMapper.deleteByPrimaryKey(courserelstudentId);
	}

	@Override
	public CourserelstudentDto update(CourserelstudentDto courserelstudentDto) {
		userService.findTeacherByNo(courserelstudentDto.getStudentNo());

		Courserelstudent courserelstudent = new Courserelstudent();
		BeanUtils.copyProperties(courserelstudentDto, courserelstudent);
		courserelstudentMapper.updateByPrimaryKey(courserelstudent);

		return courserelstudentDto;
	}

	@Override
	public CourserelstudentDto findById(Integer id) {
		Courserelstudent course = courserelstudentMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		CourserelstudentDto courserelstudentDto = new CourserelstudentDto();
		BeanUtils.copyProperties(course, courserelstudentDto);
		return courserelstudentDto;
	}

	@Override
	public List<CourserelstudentDto> find(CourserelstudentQueryDto courserelstudentQueryDto) {

		CourserelstudentExample courserelstudentExample = new CourserelstudentExample();
		com.cloudstudy.bo.example.CourserelstudentExample.Criteria criteria = courserelstudentExample.createCriteria();

		String keyword = courserelstudentQueryDto.getKeyword();
		Integer searchType = courserelstudentQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.studentName.getCode()) {

				UserExample userExample = new UserExample();
				Criteria criteria1 = userExample.createCriteria();
				criteria1.andNoEqualTo(keyword);

				List<User> userList = userMapper.selectByExample(userExample);
				if (userList == null || userList.isEmpty()) {
					return null;
				}

				HashSet<String> valuesSet = new HashSet<String>();
				for (User user : userList) {
					valuesSet.add(user.getNo());
				}
				criteria.andStudentNoIn(new ArrayList<String>(valuesSet));
			}
		}

		if (!StringUtils.isEmpty(courserelstudentQueryDto.getFromTime())
				&& !StringUtils.isEmpty(courserelstudentQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(courserelstudentQueryDto.getFromTime()),
					DateUtil.stringToDate(courserelstudentQueryDto.getToTime()));

		}

		if (courserelstudentQueryDto.getFromGrade() != null) {
			criteria.andGradeGreaterThanOrEqualTo(courserelstudentQueryDto.getFromGrade());
		}
		if (courserelstudentQueryDto.getToGrade() != null) {
			criteria.andGradeLessThanOrEqualTo(courserelstudentQueryDto.getToGrade());
		}

		PageHelper.startPage(courserelstudentQueryDto.getPageDto().getPage(),
				courserelstudentQueryDto.getPageDto().getRows());
		List<Courserelstudent> courserelstudentList = courserelstudentMapper.selectByExample(courserelstudentExample);
		if (courserelstudentList == null || courserelstudentList.isEmpty()) {
			return null;
		}

		List<CourserelstudentDto> courserelstudentDtoList = new ArrayList<CourserelstudentDto>();
		for (Courserelstudent courserelstudent : courserelstudentList) {
			CourserelstudentDto courserelstudentDto = new CourserelstudentDto();
			BeanUtils.copyProperties(courserelstudent, courserelstudentDto);
			courserelstudentDtoList.add(courserelstudentDto);
		}
		return courserelstudentDtoList;
	}

}
