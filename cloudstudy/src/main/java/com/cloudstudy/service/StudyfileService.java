package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import com.cloudstudy.dto.StudyfileDto;
import com.cloudstudy.dto.StudyfileQueryParamDto;

public interface StudyfileService {

	StudyfileDto add(StudyfileDto fileDto) throws IOException;

	void deleteById(Integer id) throws IOException;

	void deleteByCourserelteacherId(Integer id) throws IOException;

	void deleteByTaskId(Integer id) throws IOException;

	void deleteByJobId(Integer id) throws IOException;

	StudyfileDto findById(Integer id);

	List<StudyfileDto> find(StudyfileQueryParamDto fileQueryParamDto);

}
