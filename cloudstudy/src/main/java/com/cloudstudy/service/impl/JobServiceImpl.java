package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.example.JobExample;
import com.cloudstudy.bo.example.JobExample;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.HomeworkQueryParamDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.StudyfileMapper;
import com.cloudstudy.mapper.StudyfilereljobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.StudyfilereljobMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private UserService userService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private CourserelteacherMapper courserelteacherMapper;
	@Autowired
	private CourserelstudentMapper courserelstudentMapper;
	@Autowired
	private StudyfileMapper fileMapper;
	@Autowired
	private StudyfilereljobMapper studyfilereljobMapper;

	@Override
	public JobDto add(JobDto jobDto) {
		Job job = new Job();
		BeanUtils.copyProperties(jobDto, job);
		jobMapper.insert(job);

		return jobDto;
	}

	@Override
	public void delete(Integer jobId) {
		Job job = jobMapper.selectByPrimaryKey(jobId);
		if (job == null) {
			return;
		}
		jobMapper.deleteByPrimaryKey(jobId);

		JobExample jobExample = new JobExample();
		com.cloudstudy.bo.example.JobExample.Criteria criteria = jobExample.createCriteria();
		criteria.andIdEqualTo(jobId);
		jobMapper.deleteByExample(jobExample);

	}

	@Override
	public JobDto findById(Integer id) {
		Job course = jobMapper.selectByPrimaryKey(id);
		if (course == null) {
			return null;
		}
		JobDto jobDto = new JobDto();
		BeanUtils.copyProperties(course, jobDto);
		return jobDto;
	}

	@Override
	public List<JobDto> find(HomeworkQueryParamDto homeworkQueryParamDto) {
		JobExample jobExample = new JobExample();
		com.cloudstudy.bo.example.JobExample.Criteria criteria = jobExample.createCriteria();

		String keyword = homeworkQueryParamDto.getKeyword();
		Integer searchType = homeworkQueryParamDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {

		}

		if (!StringUtils.isEmpty(homeworkQueryParamDto.getFromTime())
				&& !StringUtils.isEmpty(homeworkQueryParamDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(homeworkQueryParamDto.getFromTime()),
					DateUtil.stringToDate(homeworkQueryParamDto.getToTime()));

		}

		PageHelper.startPage(homeworkQueryParamDto.getPageDto().getPage(),
				homeworkQueryParamDto.getPageDto().getRows());
		List<Job> jobList = jobMapper.selectByExample(jobExample);
		if (jobList == null || jobList.isEmpty()) {
			return null;
		}

		List<JobDto> jobDtoList = new ArrayList<JobDto>();
		for (Job job : jobList) {
			JobDto jobDto = new JobDto();
			BeanUtils.copyProperties(job, jobDto);
			jobDtoList.add(jobDto);
		}
		return jobDtoList;
	}

	@Override
	public List<JobDto> findByCourseId(Integer id) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(id + "");
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

	@Override
	public List<JobDto> findByTaskId(Integer id) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(id + "");
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

	@Override
	public List<JobDto> findByTeacherNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(no);
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

	@Override
	public List<JobDto> findByStudentNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryParamDto homeworkQueryParamDto = new HomeworkQueryParamDto();
		homeworkQueryParamDto.setKeyword(no);
		homeworkQueryParamDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryParamDto);
	}

}
