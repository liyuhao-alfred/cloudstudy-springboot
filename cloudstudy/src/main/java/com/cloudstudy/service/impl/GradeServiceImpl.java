package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.HomeworkExample;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.dto.CalResultDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.EveryCourseGradeObject;
import com.cloudstudy.dto.ExpextAndRealityGradeObject;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.GradeQueryParamDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.HomeworkMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.fasterxml.classmate.members.RawMethod;
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
	private HomeworkMapper homeworkMapper;

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

	private GradeQueryDto generateGradeQueryDto(Grade Grade) {
		if (Grade == null) {
			throw new CloudStudyException();
		}
		GradeQueryDto GradeQueryDto = new GradeQueryDto();
		BeanUtils.copyProperties(Grade, GradeQueryDto);

		String createTime;
		try {
			createTime = DateUtil.dateToString(Grade.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		GradeQueryDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(Grade.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		GradeQueryDto.setLastModifyTime(lastModifyTime);

		Course Course = courseMapper.selectByPrimaryKey(GradeQueryDto.getCourseId());
		GradeQueryDto.setCourseName(Course.getName());

		User teacher = userMapper.selectByPrimaryKey(Course.getTeacherNo());
		GradeQueryDto.setTeacherName(teacher.getName());
		GradeQueryDto.setTeacherNo(teacher.getNo());

		User student = userMapper.selectByPrimaryKey(GradeQueryDto.getStudentNo());
		GradeQueryDto.setStudentName(student.getName());

		return GradeQueryDto;
	}

	private GradeDto generateDto(Grade Grade) {
		if (Grade == null) {
			throw new CloudStudyException();
		}
		GradeDto GradeDto = new GradeDto();
		BeanUtils.copyProperties(Grade, GradeDto);

		String createTime;
		try {
			createTime = DateUtil.dateToString(Grade.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		GradeDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(Grade.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		GradeDto.setLastModifyTime(lastModifyTime);
		return GradeDto;
	}

	@Override
	public GradeDto changeCommit(String studentNo, Integer courseId) {
		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andStudentNoEqualTo(studentNo);
		criteria.andCourseIdEqualTo(courseId);

		List<Grade> GradeList = gradeMapper.selectByExample(gradeExample);
		if (GradeList != null && GradeList.size() > 0) {
			gradeMapper.deleteByExample(gradeExample);
			return generateDto(GradeList.get(0));
		} else {

			HomeworkExample HomeworkExample = new HomeworkExample();
			com.cloudstudy.bo.HomeworkExample.Criteria HomeworkExamplecriteria = HomeworkExample.createCriteria();
			HomeworkExamplecriteria.andCourseIdEqualTo(courseId);
			long count = homeworkMapper.countByExample(HomeworkExample);

			Grade Grade = new Grade();
			Grade.setCourseId(courseId);
			Grade.setCreateTime(new Date());
			Grade.setGrade(0);
			Grade.setId(null);
			Grade.setLastModifyTime(new Date());
			Grade.setStatus(0);
			Grade.setStudentNo(studentNo);
			Grade.setHomeworkAcceptNum(0);
			Grade.setHomeworkDeclareNum((int) count);
			gradeMapper.insert(Grade);

			return generateDto(Grade);

		}
	}

	@Override
	public GradeDto changeGrade(Integer gradePoint, Integer gradeId) {
		Grade Grade = gradeMapper.selectByPrimaryKey(gradeId);
		if (Grade == null) {
			throw new CloudStudyException();
		}

		Grade.setGrade(gradePoint);
		Grade.setLastModifyTime(new Date());
		gradeMapper.updateByPrimaryKeySelective(Grade);

		return generateDto(Grade);
	}

	@Override
	public CalResultDto calByStudent(String studentNo) {
		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andStudentNoEqualTo(studentNo);

		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		if (gradeList == null || gradeList.isEmpty()) {
			return null;
		} else {
			CalResultDto CalResultDto = new CalResultDto();

			ArrayList<EveryCourseGradeObject> everyCourseGradeArray = new ArrayList<EveryCourseGradeObject>();
			ArrayList<Integer> realityValueList = new ArrayList<Integer>();
			ArrayList<Integer> expectValueList = new ArrayList<Integer>();

			List<GradeQueryDto> GradeQueryDtoList = new ArrayList<GradeQueryDto>();
			for (Grade grade : gradeList) {
				GradeQueryDto GradeQueryDto = generateGradeQueryDto(grade);

				EveryCourseGradeObject EveryCourseGradeObject = new EveryCourseGradeObject();
				EveryCourseGradeObject.setMax(GradeQueryDto.getGrade());
				EveryCourseGradeObject.setName(GradeQueryDto.getCourseName());
				everyCourseGradeArray.add(EveryCourseGradeObject);

				realityValueList.add(GradeQueryDto.getGrade());
				expectValueList.add((int) (GradeQueryDto.getGrade() * Math.random()));
			}

			CalResultDto.setEveryCourseGradeArray(everyCourseGradeArray);

			ExpextAndRealityGradeObject ExpextAndRealityGradeObject1 = new ExpextAndRealityGradeObject(realityValueList,
					"实际成绩");
			ExpextAndRealityGradeObject ExpextAndRealityGradeObject2 = new ExpextAndRealityGradeObject(expectValueList,
					"预期成绩");
			ArrayList<ExpextAndRealityGradeObject> expextAndRealityGradeArray = new ArrayList<ExpextAndRealityGradeObject>();
			expextAndRealityGradeArray.add(ExpextAndRealityGradeObject1);
			expextAndRealityGradeArray.add(ExpextAndRealityGradeObject2);
			CalResultDto.setExpextAndRealityGradeArray(expextAndRealityGradeArray);

			return CalResultDto;
		}

	}

	@Override
	public CalResultDto calByCourse(Integer courseId) {
		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
		criteria.andCourseIdEqualTo(courseId);

		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		if (gradeList == null || gradeList.isEmpty()) {
			return null;
		} else {

			ArrayList<String> studentNameArray = new ArrayList<String>();
			ArrayList<Double> gradeArray = new ArrayList<Double>();
			ArrayList<Double> averageArray = new ArrayList<Double>();

			List<GradeQueryDto> GradeQueryDtoList = new ArrayList<GradeQueryDto>();
			for (Grade grade : gradeList) {
				GradeQueryDto GradeQueryDto = generateGradeQueryDto(grade);
				studentNameArray.add(GradeQueryDto.getStudentName());
				gradeArray.add(Double.valueOf(GradeQueryDto.getGrade()));
				averageArray.add(Double.valueOf(GradeQueryDto.getGrade()));
			}

			CalResultDto CalResultDto = new CalResultDto();
			CalResultDto.setAverageArray(averageArray);
			CalResultDto.setGradeArray(gradeArray);
			CalResultDto.setStudentNameArray(studentNameArray);

			return CalResultDto;
		}
	}

	@Override
	public PageResultDto<List<GradeQueryDto>> find(GradeQueryParamDto GradeQueryParamDto) {
		GradeExample gradeExample = new GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();

		ArrayList<String> studentNoList = GradeQueryParamDto.getStudentNo();
		if (studentNoList != null && !studentNoList.isEmpty()) {
			criteria.andStudentNoIn(studentNoList);
		}

		ArrayList<Integer> courseIdList = GradeQueryParamDto.getCourseId();
		if (courseIdList != null && !courseIdList.isEmpty()) {
			criteria.andCourseIdIn(courseIdList);
		}

		long count = gradeMapper.countByExample(gradeExample);

		if (GradeQueryParamDto.getPageDto() != null) {
			Integer page = GradeQueryParamDto.getPageDto().getCurrent();
			Integer rows = GradeQueryParamDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
		if (gradeList == null || gradeList.isEmpty()) {
			return null;
		}

		List<GradeQueryDto> GradeQueryDtoList = new ArrayList<GradeQueryDto>();
		for (Grade grade : gradeList) {
			GradeQueryDtoList.add(generateGradeQueryDto(grade));
		}

		return new PageResultDto<List<GradeQueryDto>>(count, GradeQueryDtoList);
	}
}
