package com.cloudstudy.log;

import java.util.List;

import com.cloudstudy.dto.SystemlogDto;

public interface SystemlogService {

	SystemlogDto save(SystemlogDto systemlogDto);

	SystemlogDto findById(Integer id);

	List<SystemlogDto> find(String logLevel, String logType, String remoteCallIp, String serviceClass,
			String serviceMethod, String serviceErrorCode, Integer index, Integer limit);

}
