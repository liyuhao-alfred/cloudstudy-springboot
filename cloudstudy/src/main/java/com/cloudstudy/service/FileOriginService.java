package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.PageResultDto;

public interface FileOriginService {

	FileOriginDto add(MultipartFile file) throws IOException;

	FileOriginDto add(FileOriginDto fileOriginDto) throws IOException;

	void deleteById(Integer fileOriginId) throws IOException;

	void deleteByIdList(List<Integer> fileOriginIdList) throws IOException;

	FileOriginQueryDto findById(Integer fileOriginId);

	List<FileOriginDto> findByJobId(Integer jobId, boolean isUpRecursion);

	List<FileOriginDto> findByTaskId(Integer taskId, boolean isUpRecursion, boolean isDownRecursion);

	List<FileOriginDto> findByUserNo(String userNo, boolean isDownRecursion);

	List<FileOriginDto> findByCourseId(Integer courseId, boolean isDownRecursion);

	PageResultDto<List<FileOriginQueryDto>> find(FileOriginQueryParamDto fileOriginQueryDto);

	void deleteByUserNo(String userNo) throws IOException;

	List<FileOriginDto> findByIdList(List<Integer> primaryKeyList);

	FileOriginQueryDto add(FileToCourse fileToCourse) throws IOException;

}
