package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.JobQueryDto;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.exception.CloudStudyException;
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
@SuppressWarnings("unused")
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
	public JobDto findById(Integer primaryKey) {
		Job course = jobMapper.selectByPrimaryKey(primaryKey);
		if (course == null) {
			return null;
		}
		JobDto jobDto = new JobDto();
		BeanUtils.copyProperties(course, jobDto);
		return jobDto;
	}

	@Override
	public List<JobDto> find(JobQueryDto jobQueryDto) {
		JobExample jobExample = new JobExample();
		com.cloudstudy.bo.JobExample.Criteria criteria = jobExample.createCriteria();

		String keyword = jobQueryDto.getKeyword();
		Integer searchType = jobQueryDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {

		}

		if (!StringUtils.isEmpty(jobQueryDto.getFromTime()) && !StringUtils.isEmpty(jobQueryDto.getToTime())) {
			criteria.andLastModifyTimeBetween(DateUtil.stringToDate(jobQueryDto.getFromTime()),
					DateUtil.stringToDate(jobQueryDto.getToTime()));

		}

		Integer page = jobQueryDto.getPageDto().getCurrent();
		Integer rows = jobQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);

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
	public List<JobDto> findByCourseId(Integer primaryKey) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		JobQueryDto jobQueryDto = new JobQueryDto();
		jobQueryDto.setKeyword(primaryKey + "");
		jobQueryDto.setSearchTypeSet(searchTypeSet);

		return find(jobQueryDto);
	}

	@Override
	public List<JobDto> findByTaskId(Integer primaryKey) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		JobQueryDto jobQueryDto = new JobQueryDto();
		jobQueryDto.setKeyword(primaryKey + "");
		jobQueryDto.setSearchTypeSet(searchTypeSet);

		return find(jobQueryDto);
	}

	@Override
	public List<JobDto> findByTeacherNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		JobQueryDto jobQueryDto = new JobQueryDto();
		jobQueryDto.setKeyword(no);
		jobQueryDto.setSearchTypeSet(searchTypeSet);

		return find(jobQueryDto);
	}

	@Override
	public List<JobDto> findByStudentNo(String no) {
		HashSet<SearchType> searchTypeSet = new HashSet<SearchType>();
		searchTypeSet.add(SearchType.courseId);

		JobQueryDto jobQueryDto = new JobQueryDto();
		jobQueryDto.setKeyword(no);
		jobQueryDto.setSearchTypeSet(searchTypeSet);

		return find(jobQueryDto);
	}

	private List<Integer> getPrimaryKeyList(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List<Integer> primaryKeyList = new ArrayList<Integer>();
		for (Job job : jobList) {
			primaryKeyList.add(job.getId());
		}
		return primaryKeyList;
	}

	private List<JobDto> generateDto(List<Job> jobList) {
		if (jobList == null || jobList.isEmpty()) {
			return new ArrayList<JobDto>();
		}
		List<JobDto> JobDtoList = new ArrayList<JobDto>();
		for (Job job : jobList) {
			JobDto jobDto = generateDto(job);
			JobDtoList.add(jobDto);
		}
		return JobDtoList;
	}

	private List<Job> generate(List<JobDto> jobDtoList) {
		if (jobDtoList == null || jobDtoList.isEmpty()) {
			return new ArrayList<Job>();
		}
		List<Job> JobList = new ArrayList<Job>();
		for (JobDto jobDto : jobDtoList) {
			Job job = generate(jobDto);
			JobList.add(job);
		}
		return JobList;
	}

	private JobDto generateDto(Job job) {
		if (job == null) {
			throw new CloudStudyException();
		}
		JobDto jobDto = new JobDto();
		BeanUtils.copyProperties(job, jobDto);
		return jobDto;
	}

	private Job generate(JobDto jobDto) {
		if (jobDto == null) {
			throw new CloudStudyException();
		}
		Job job = new Job();
		BeanUtils.copyProperties(jobDto, job);
		return job;
	}

}
