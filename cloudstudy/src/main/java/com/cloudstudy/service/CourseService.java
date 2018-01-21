package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryParamDto;

public interface CourseService {

	CourseDto add(CourseDto courseDto);

	void delete(Integer id);

	CourseDto update(CourseDto courseDto);

	CourseDto findById(Integer id);

	List<CourseDto> find(CourseQueryParamDto courseQueryParamDto);

}
