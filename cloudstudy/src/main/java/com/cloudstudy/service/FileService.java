package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.FileDto;
import com.cloudstudy.dto.FileQueryParamDto;

public interface FileService {

	FileDto add(FileDto fileDto);

	void delete(Integer id);

	FileDto update(FileDto fileDto);

	FileDto findById(Integer id);

	List<FileDto> find(FileQueryParamDto fileQueryParamDto);

}
