package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.HomeworkDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;

public interface HomeworkService {

	HomeworkDto declare(HomeworkDto homeworkDto);

	void delete(Integer primaryKey);

	HomeworkDto update(HomeworkDto homeworkDto);

	HomeworkDto findById(Integer primaryKey);

	PageResultDto<List<HomeworkQueryDto>> find(HomeworkQueryParamDto HomeworkQueryParamDto);

	PageResultDto<List<HomeworkQueryDto>> findByStudent(HomeworkQueryParamDto HomeworkQueryParamDto);

	PageResultDto<List<HomeworkQueryDto>> findByCourse(HomeworkQueryParamDto HomeworkQueryParamDto);

}
