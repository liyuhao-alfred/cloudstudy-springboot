package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.PageResultDto;

public interface FileOriginService {

	FileOriginQueryDto add(MultipartFile multipartFile, Integer courseId, Integer homeworkId, String uploaderNo)
			throws IOException;

	void deleteById(Integer fileOriginId) throws IOException;

	byte[] getFileByte(Integer fileOriginId) throws IOException;

	FileOriginQueryDto findById(Integer fileOriginId);

	PageResultDto<List<FileOriginQueryDto>> find(FileOriginQueryParamDto fileOriginQueryDto);

}
