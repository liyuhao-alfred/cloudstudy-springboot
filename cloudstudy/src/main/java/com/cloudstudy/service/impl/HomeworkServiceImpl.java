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
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToHomework;
import com.cloudstudy.bo.FileToHomeworkExample;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.HomeworkExample;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.HomeworkDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToHomeworkMapper;
import com.cloudstudy.mapper.HomeworkMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.HomeworkService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
@SuppressWarnings("unused")
public class HomeworkServiceImpl implements HomeworkService {

	@Autowired
	private UserService userService;
	@Autowired
	private HomeworkMapper HomeworkMapper;
	@Autowired
	private CourseMapper CourseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private FileToHomeworkMapper fileToHomeworkMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public HomeworkDto declare(HomeworkDto HomeworkDto) {
		Homework Homework = generate(HomeworkDto);
		HomeworkMapper.insert(Homework);
		return HomeworkDto;
	}

	@Override
	public void delete(Integer HomeworkId) {
		Homework Homework = HomeworkMapper.selectByPrimaryKey(HomeworkId);
		if (Homework == null) {
			return;
		}
		HomeworkMapper.deleteByPrimaryKey(HomeworkId);
	}

	@Override
	public HomeworkDto update(HomeworkDto HomeworkDto) {

		Homework Homework = new Homework();
		BeanUtils.copyProperties(HomeworkDto, Homework);
		HomeworkMapper.updateByPrimaryKeyWithBLOBs(Homework);

		return HomeworkDto;
	}

	@Override
	public HomeworkDto findById(Integer primaryKey) {
		Homework course = HomeworkMapper.selectByPrimaryKey(primaryKey);
		if (course == null) {
			return null;
		}
		HomeworkDto HomeworkDto = new HomeworkDto();
		BeanUtils.copyProperties(course, HomeworkDto);
		return HomeworkDto;
	}

	private List<Integer> getPrimaryKeyList(List<Homework> HomeworkList) {
		if (HomeworkList == null || HomeworkList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (Homework Homework : HomeworkList) {
			primaryKeyList.add(Homework.getId());
		}
		return primaryKeyList;
	}

	public List<HomeworkDto> generateDto(List<Homework> HomeworkList) {
		if (HomeworkList == null || HomeworkList.isEmpty()) {
			return new ArrayList<HomeworkDto>();
		}
		List<HomeworkDto> HomeworkDtoList = new ArrayList<HomeworkDto>();
		for (Homework Homework : HomeworkList) {
			HomeworkDto HomeworkDto = generateDto(Homework);
			HomeworkDtoList.add(HomeworkDto);
		}
		return HomeworkDtoList;
	}

	private List<Homework> generate(List<HomeworkDto> HomeworkDtoList) {
		if (HomeworkDtoList == null || HomeworkDtoList.isEmpty()) {
			return new ArrayList<Homework>();
		}
		List<Homework> HomeworkList = new ArrayList<Homework>();
		for (HomeworkDto HomeworkDto : HomeworkDtoList) {
			Homework Homework = generate(HomeworkDto);
			HomeworkList.add(Homework);
		}
		return HomeworkList;
	}

	private HomeworkDto generateDto(Homework Homework) {
		if (Homework == null) {
			throw new CloudStudyException();
		}
		HomeworkDto HomeworkDto = new HomeworkDto();
		BeanUtils.copyProperties(Homework, HomeworkDto);
		return HomeworkDto;
	}

	private Homework generate(HomeworkDto HomeworkDto) {
		if (HomeworkDto == null) {
			throw new CloudStudyException();
		}
		Homework Homework = new Homework();
		BeanUtils.copyProperties(HomeworkDto, Homework);

		if (HomeworkDto.getStatus()) {// 状态
			Homework.setStatus(0);
		} else {
			Homework.setStatus(1);
		}

		Homework.setLastModifyTime(new Date());

		if (HomeworkDto.getDeadLine() != null) {
			Homework.setDeadLine(DateUtil.stringToDateSpecial(HomeworkDto.getDeadLine()));
		} else {
			Homework.setDeadLine(new Date());
		}

		if (HomeworkDto.getCreateTime() == null) {
			Homework.setCreateTime(new Date());
		} else {
			Homework.setCreateTime(DateUtil.stringToDateSpecial(HomeworkDto.getCreateTime()));
		}

		return Homework;

	}

	@Override
	public PageResultDto<List<HomeworkQueryDto>> find(HomeworkQueryParamDto HomeworkQueryParamDto) {
		HomeworkExample HomeworkExample = new HomeworkExample();
		com.cloudstudy.bo.HomeworkExample.Criteria HomeworkExamplecriteria = HomeworkExample.createCriteria();

		String keyword = HomeworkQueryParamDto.getKeyword();
		if (keyword != null && !keyword.isEmpty()) {
			keyword = "%" + keyword + "%";
			HomeworkExamplecriteria.andTitleLike(keyword);
		}

		HashSet<Integer> homeworkIdSet = new HashSet<Integer>();
		ArrayList<Integer> courseIdList = HomeworkQueryParamDto.getCourseId();
		if (courseIdList != null && !courseIdList.isEmpty()) {
			HomeworkExample HomeworkExample11 = new HomeworkExample();
			HomeworkExample.Criteria Criteria11 = HomeworkExample11.createCriteria();
			Criteria11.andCourseIdIn(courseIdList);
			List<Homework> FileToCourseList = HomeworkMapper.selectByExample(HomeworkExample11);
			if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
				for (Homework Homework : FileToCourseList) {
					homeworkIdSet.add(Homework.getId());
				}
			}
		}

		ArrayList<String> teacherNoList = HomeworkQueryParamDto.getTeacherNo();
		if (teacherNoList != null && !teacherNoList.isEmpty()) {
			CourseExample CourseExample22 = new CourseExample();
			CourseExample.Criteria Criteria22 = CourseExample22.createCriteria();
			Criteria22.andTeacherNoIn(teacherNoList);
			List<Course> CourseList22 = CourseMapper.selectByExample(CourseExample22);
			if (CourseList22 != null && !CourseList22.isEmpty()) {
				ArrayList<Integer> courseIdList22 = new ArrayList<Integer>();
				for (Course Course : CourseList22) {
					courseIdList22.add(Course.getId());
				}

				HomeworkExample HomeworkExample22 = new HomeworkExample();
				HomeworkExample.Criteria HomeworkExampleCriteria = HomeworkExample22.createCriteria();
				HomeworkExampleCriteria.andCourseIdIn(courseIdList22);
				List<Homework> HomeworkList22 = HomeworkMapper.selectByExample(HomeworkExample22);
				if (HomeworkList22 != null && !HomeworkList22.isEmpty()) {
					for (Homework Homework : HomeworkList22) {
						homeworkIdSet.add(Homework.getId());
					}
				}
			}
		}

		ArrayList<String> studentNoList = HomeworkQueryParamDto.getStudentNo();
		if (studentNoList != null && !studentNoList.isEmpty()) {
			GradeExample GradeExample = new GradeExample();
			GradeExample.Criteria Criteria = GradeExample.createCriteria();
			Criteria.andStudentNoIn(studentNoList);
			List<Grade> GeadeList = gradeMapper.selectByExample(GradeExample);

			if (GeadeList != null && !GeadeList.isEmpty()) {
				ArrayList<Integer> courseIdList33 = new ArrayList<Integer>();
				for (Grade grade : GeadeList) {
					courseIdList33.add(grade.getCourseId());
				}

				HomeworkExample HomeworkExample33 = new HomeworkExample();
				HomeworkExample.Criteria Criteria33 = HomeworkExample33.createCriteria();
				Criteria33.andCourseIdIn(courseIdList33);
				List<Homework> HomeworkList33 = HomeworkMapper.selectByExample(HomeworkExample33);
				if (HomeworkList33 != null && !HomeworkList33.isEmpty()) {
					for (Homework Homework : HomeworkList33) {
						homeworkIdSet.add(Homework.getId());
					}
				}
			}
		}

		if (homeworkIdSet != null && !homeworkIdSet.isEmpty()) {
			HomeworkExamplecriteria.andIdIn(new ArrayList<Integer>(homeworkIdSet));
		} else if (courseIdList != null && !courseIdList.isEmpty()) {
			return new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		} else if (teacherNoList != null && !teacherNoList.isEmpty()) {
			return new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		} else if (studentNoList != null && !studentNoList.isEmpty()) {
			return new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		}

		long total = HomeworkMapper.countByExample(HomeworkExample);

		if (HomeworkQueryParamDto.getPageDto() != null) {
			Integer page = HomeworkQueryParamDto.getPageDto().getCurrent();
			Integer rows = HomeworkQueryParamDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<Homework> HomeworkList = HomeworkMapper.selectByExampleWithBLOBs(HomeworkExample);
		if (HomeworkList == null || HomeworkList.isEmpty()) {
			return null;
		}
		List<HomeworkQueryDto> HomeworkQueryDtoList = new ArrayList<HomeworkQueryDto>();
		for (Homework Homework : HomeworkList) {
			HomeworkQueryDto HomeworkQueryDto2 = generateQueryDto(Homework);
			HomeworkQueryDtoList.add(HomeworkQueryDto2);
		}
		return new PageResultDto<List<HomeworkQueryDto>>(total, HomeworkQueryDtoList);

	}

	private HomeworkQueryDto generateQueryDto(Homework Homework) {
		if (Homework == null) {
			throw new CloudStudyException();
		}
		HomeworkQueryDto HomeworkQueryDto = new HomeworkQueryDto();
		BeanUtils.copyProperties(Homework, HomeworkQueryDto);

		if (Homework.getStatus().equals(0)) {
			HomeworkQueryDto.setStatus(true);
			HomeworkQueryDto.setStatusMemo("进行中");
		} else {
			HomeworkQueryDto.setStatus(false);
			HomeworkQueryDto.setStatusMemo("已结束");
		}

		Course Course = CourseMapper.selectByPrimaryKey(HomeworkQueryDto.getCourseId());
		HomeworkQueryDto.setCourseName(Course.getName());

		UserDto teacher = userService.findUserByNo(Course.getTeacherNo());
		HomeworkQueryDto.setTeacherName(teacher.getName());
		HomeworkQueryDto.setTeacherNo(teacher.getNo());

		String createTime;
		try {
			createTime = DateUtil.dateToString(Homework.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		HomeworkQueryDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(Homework.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		HomeworkQueryDto.setLastModifyTime(lastModifyTime);

		String deadline;
		try {
			deadline = DateUtil.dateToString(Homework.getDeadLine());
		} catch (Exception e) {
			deadline = DateUtil.getNowDateAsString();
		}
		HomeworkQueryDto.setDeadLine(deadline);

		return HomeworkQueryDto;
	}

	@Override
	public PageResultDto<List<HomeworkQueryDto>> findByStudent(HomeworkQueryParamDto HomeworkQueryParamDto) {
		return find(HomeworkQueryParamDto);
	}

	@Override
	public PageResultDto<List<HomeworkQueryDto>> findByCourse(HomeworkQueryParamDto HomeworkQueryParamDto) {
		ArrayList<Integer> courseIdList = HomeworkQueryParamDto.getCourseId();
		if (courseIdList == null || courseIdList.isEmpty()) {
			return null;
		}

		HomeworkExample HomeworkExample = new HomeworkExample();
		HomeworkExample.Criteria HomeworkExamplecriteria = HomeworkExample.createCriteria();
		HomeworkExamplecriteria.andCourseIdIn(courseIdList);

		long total = HomeworkMapper.countByExample(HomeworkExample);

		if (HomeworkQueryParamDto.getPageDto() != null) {
			Integer page = HomeworkQueryParamDto.getPageDto().getCurrent();
			Integer rows = HomeworkQueryParamDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<Homework> HomeworkList = HomeworkMapper.selectByExample(HomeworkExample);
		if (HomeworkList == null || HomeworkList.isEmpty()) {
			return null;
		}

		List<HomeworkQueryDto> HomeworkQueryDtoList = new ArrayList<HomeworkQueryDto>();
		for (Homework Homework : HomeworkList) {
			HomeworkQueryDto HomeworkQueryDto2 = generateQueryDto(Homework);
			HomeworkQueryDtoList.add(HomeworkQueryDto2);
		}
		return new PageResultDto<List<HomeworkQueryDto>>(total, HomeworkQueryDtoList);

	}

}
