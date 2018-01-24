package com.cloudstudy.service;

import java.io.IOException;
import java.util.List;

import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.FileOriginQueryDto;

public interface FileOriginService {

	FileOriginDto add(FileOriginDto fileOriginDto) throws IOException;

	void deleteById(Integer id) throws IOException;

	void deleteByIdList(List<Integer> idList) throws IOException;

	FileOriginDto findById(Integer id);

	List<FileOriginDto> findByJobId(Integer jobId, boolean isUpRecursion);

	List<FileOriginDto> findByTaskId(Integer taskId, boolean isUpRecursion, boolean isDownRecursion);

	List<FileOriginDto> findByUserNo(String userNo, boolean isDownRecursion);

	List<FileOriginDto> findByCourseId(Integer courseId, boolean isDownRecursion);

	List<FileOriginDto> find(FileOriginQueryDto fileQueryDto);

}
