package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.StudyFileDto;
import com.cloudstudy.dto.StudyfileQueryParamDto;

public interface StudyFileService {

	StudyFileDto add(StudyFileDto fileDto);

	void delete(Integer id);

	StudyFileDto findById(Integer id);

	List<StudyFileDto> find(StudyfileQueryParamDto fileQueryParamDto);

}
