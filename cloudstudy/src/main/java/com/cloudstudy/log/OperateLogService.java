package com.cloudstudy.log;

import java.util.List;

import com.cloudstudy.dto.OperateLogDto;
import com.cloudstudy.dto.OperateLogQueryDto;

public interface OperateLogService {

	OperateLogDto save(OperateLogDto operateLogDto);

	OperateLogDto findById(Integer id);

	List<OperateLogDto> find(OperateLogQueryDto operateLogQueryDto);

	List<OperateLogDto> find(String operatorNo, String operatorType, String operatorName, String requestIp,
			String operationErrorCode, String startTime, String endTime, Integer index, Integer limit);
}
