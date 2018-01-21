package com.cloudstudy.log;

import java.util.List;

import com.cloudstudy.dto.SystemlogDto;
import com.cloudstudy.dto.SystemlogQueryParamDto;

public interface SystemlogService {

	SystemlogDto save(SystemlogDto systemlogDto);

	SystemlogDto findById(Integer id);

	List<SystemlogDto> find(SystemlogQueryParamDto systemlogQueryParamDto);

	List<SystemlogDto> find(String logLevel, String logType, String remoteCallIp, String serviceClass,
			String serviceMethod, String serviceErrorCode, String startTime, String endTime, Integer index,
			Integer limit);

}
