package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryParamDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
public class GradeServiceImpl implements GradeService {

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;

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
	public GradeDto findById(Integer primaryKey) {
		Grade course = gradeMapper.selectByPrimaryKey(primaryKey);
		if (course == null) {
			return null;
		}
		GradeDto gradeDto = new GradeDto();
		BeanUtils.copyProperties(course, gradeDto);
		return gradeDto;
	}

	@Override
	public List<GradeDto> find(GradeQueryParamDto gradeQueryDto) {

		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();

		String keyword = gradeQueryDto.getKeyword();
		Integer searchType = gradeQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.studentName.getCode()) {

				UserExample userExample = new UserExample();
				Criteria criteria1 = userExample.createCriteria();
				criteria1.andNoEqualTo(keyword);

				Integer page = gradeQueryDto.getPageDto().getCurrent();
				Integer rows = gradeQueryDto.getPageDto().getSize();
				PageHelper.startPage(page, rows);

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

		Integer page = gradeQueryDto.getPageDto().getCurrent();
		Integer rows = gradeQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);

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

		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andStudentNoEqualTo(studentNo);
		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);

		return generateDto(gradeList);
	}

	@Override
	public List<GradeDto> findByTeacherNo(String teacherNo) {

		List<CourseDto> CourseDtoList = courseService.findByTeacherNo(teacherNo);
		if (CourseDtoList == null || CourseDtoList.isEmpty()) {
			return null;
		}

		List<Integer> integerList = new ArrayList<Integer>();
		for (CourseDto courseDto : CourseDtoList) {
			integerList.add(courseDto.getId());
		}

		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andCourseIdIn(integerList);
		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		return generateDto(gradeList);
	}

	@Override
	public List<GradeDto> findByCourseId(Integer courseId) {
		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andCourseIdEqualTo(courseId);
		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		return generateDto(gradeList);
	}

	private List<Integer> getPrimaryKeyList(List<Grade> gradeList) {
		if (gradeList == null || gradeList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (Grade grade : gradeList) {
			primaryKeyList.add(grade.getId());
		}
		return primaryKeyList;
	}

	private List<GradeDto> generateDto(List<Grade> gradeList) {
		if (gradeList == null || gradeList.isEmpty()) {
			return new ArrayList<GradeDto>();
		}
		List<GradeDto> GradeDtoList = new ArrayList<GradeDto>();
		for (Grade grade : gradeList) {
			GradeDto gradeDto = generateDto(grade);
			GradeDtoList.add(gradeDto);
		}
		return GradeDtoList;
	}

	private List<Grade> generate(List<GradeDto> gradeDtoList) {
		if (gradeDtoList == null || gradeDtoList.isEmpty()) {
			return new ArrayList<Grade>();
		}
		List<Grade> GradeList = new ArrayList<Grade>();
		for (GradeDto gradeDto : gradeDtoList) {
			Grade grade = generate(gradeDto);
			GradeList.add(grade);
		}
		return GradeList;
	}

	private GradeDto generateDto(Grade grade) {
		if (grade == null) {
			throw new CloudStudyException();
		}
		GradeDto gradeDto = new GradeDto();
		BeanUtils.copyProperties(grade, gradeDto);
		return gradeDto;
	}

	private Grade generate(GradeDto gradeDto) {
		if (gradeDto == null) {
			throw new CloudStudyException();
		}
		Grade grade = new Grade();
		BeanUtils.copyProperties(gradeDto, grade);
		return grade;
	}

}
