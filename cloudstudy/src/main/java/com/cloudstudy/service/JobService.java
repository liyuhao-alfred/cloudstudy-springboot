package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;

public interface JobService {

	JobDto add(JobDto jobDto);

	void delete(Integer id);

	JobDto update(JobDto jobDto);

	JobDto findById(Integer id);

	List<JobDto> find(HomeworkQueryParamDto jobQueryParamDto);

}
