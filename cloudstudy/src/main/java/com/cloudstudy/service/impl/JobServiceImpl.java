package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToJobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.FileToJobMapper;
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
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private FileToJobMapper fileOriginToJobMapper;

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
		com.cloudstudy.bo.JobExample.Criteria criteria = jobExample.createCriteria();
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
	public List<JobDto> find(HomeworkQueryDto homeworkQueryDto) {
		JobExample jobExample = new JobExample();
		com.cloudstudy.bo.JobExample.Criteria criteria = jobExample.createCriteria();

		String keyword = homeworkQueryDto.getKeyword();
		Integer searchType = homeworkQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {

		}

		if (!StringUtils.isEmpty(homeworkQueryDto.getFromTime())
				&& !StringUtils.isEmpty(homeworkQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(homeworkQueryDto.getFromTime()),
					DateUtil.stringToDate(homeworkQueryDto.getToTime()));

		}

		PageHelper.startPage(homeworkQueryDto.getPageDto().getPage(), homeworkQueryDto.getPageDto().getRows());
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

		HomeworkQueryDto homeworkQueryDto = new HomeworkQueryDto();
		homeworkQueryDto.setKeyword(id + "");
		homeworkQueryDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryDto);
	}

	@Override
	public List<JobDto> findByTaskId(Integer id) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryDto homeworkQueryDto = new HomeworkQueryDto();
		homeworkQueryDto.setKeyword(id + "");
		homeworkQueryDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryDto);
	}

	@Override
	public List<JobDto> findByTeacherNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryDto homeworkQueryDto = new HomeworkQueryDto();
		homeworkQueryDto.setKeyword(no);
		homeworkQueryDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryDto);
	}

	@Override
	public List<JobDto> findByStudentNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		HomeworkQueryDto homeworkQueryDto = new HomeworkQueryDto();
		homeworkQueryDto.setKeyword(no);
		homeworkQueryDto.setSearchTypeSet(searchTypeSet);

		return find(homeworkQueryDto);
	}

}
