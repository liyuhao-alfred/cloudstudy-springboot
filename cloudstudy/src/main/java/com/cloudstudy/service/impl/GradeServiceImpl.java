package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Override
	public GradeDto addGradeByTeacher(GradeDto gradeDto) {
		userService.findTeacherByNo(gradeDto.getStudentNo());

		Grade grade = new Grade();
		BeanUtils.copyProperties(gradeDto, grade);
		gradeMapper.insert(grade);

		return gradeDto;
	}

	@Override
	public GradeDto editGradeByTeacher(GradeDto gradeDto) {
		userService.findTeacherByNo(gradeDto.getStudentNo());

		Grade grade = new Grade();
		BeanUtils.copyProperties(gradeDto, grade);
		gradeMapper.insert(grade);

		return gradeDto;
	}

	@Override
	public void deleteGradeByTeacher(Integer courseId) {

		Grade grade = gradeMapper.selectByPrimaryKey(courseId);
		if (grade == null) {
			return;
		}
		gradeMapper.deleteByPrimaryKey(courseId);
	}

	@Override
	public GradeDto findById(Integer id) {
		Grade course = gradeMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		GradeDto gradeDto = new GradeDto();
		BeanUtils.copyProperties(course, gradeDto);
		return gradeDto;
	}

	@Override
	public List<GradeDto> find(GradeQueryDto gradeQueryDto) {

		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();

		String keyword = gradeQueryDto.getKeyword();
		Integer searchType = gradeQueryDto.getSearchType();
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

		if (!StringUtils.isEmpty(gradeQueryDto.getFromTime()) && !StringUtils.isEmpty(gradeQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(gradeQueryDto.getFromTime()),
					DateUtil.stringToDate(gradeQueryDto.getToTime()));

		}

		if (gradeQueryDto.getFromGrade() != null) {
			criteria.andGradeGreaterThanOrEqualTo(gradeQueryDto.getFromGrade());
		}
		if (gradeQueryDto.getToGrade() != null) {
			criteria.andGradeLessThanOrEqualTo(gradeQueryDto.getToGrade());
		}

		PageHelper.startPage(gradeQueryDto.getPageDto().getPage(), gradeQueryDto.getPageDto().getRows());
		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		if (gradeList == null || gradeList.isEmpty()) {
			return null;
		}

		List<GradeDto> gradeDtoList = new ArrayList<GradeDto>();
		for (Grade grade : gradeList) {
			GradeDto gradeDto = new GradeDto();
			BeanUtils.copyProperties(grade, gradeDto);
			gradeDtoList.add(gradeDto);
		}
		return gradeDtoList;
	}

	@Override
	public List<GradeDto> findByStudentNo(String studentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeDto> findByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeDto> findByJobId(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeDto> findByTeacherNo(String teacherNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeDto> findByCourseId(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
