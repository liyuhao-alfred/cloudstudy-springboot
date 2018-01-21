package com.cloudstudy.log;

import java.util.List;

import com.cloudstudy.dto.OperatelogDto;
import com.cloudstudy.dto.OperatelogQueryParamDto;

public interface OperatelogService {

	OperatelogDto save(OperatelogDto operatelogDto);

	OperatelogDto findById(Integer id);

	List<OperatelogDto> find(OperatelogQueryParamDto operatelogQueryParamDto);

	List<OperatelogDto> find(String operatorNo, String operatorType, String operatorName, String requestIp,
			String operationErrorCode, String startTime, String endTime, Integer index, Integer limit);
}
