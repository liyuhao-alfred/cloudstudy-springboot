package com.cloudstudy.log;

import java.util.List;

import com.cloudstudy.dto.SystemLogDto;
import com.cloudstudy.dto.SystemLogQueryDto;

public interface SystemLogService {

	SystemLogDto save(SystemLogDto systemlogDto);

	SystemLogDto findById(Integer id);

	List<SystemLogDto> find(SystemLogQueryDto systemlogQueryDto);

	List<SystemLogDto> find(String logLevel, String logType, String remoteCallIp, String serviceClass,
			String serviceMethod, String serviceErrorCode, String startTime, String endTime, Integer index,
			Integer limit);

}
