package com.cloudstudy.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.OperateLog;
import com.cloudstudy.bo.OperateLogExample;
import com.cloudstudy.bo.OperateLogExample.Criteria;
import com.cloudstudy.dto.OperateLogDto;
import com.cloudstudy.dto.OperateLogQueryDto;
import com.cloudstudy.log.OperateLogService;
import com.cloudstudy.mapper.OperateLogMapper;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class OperateLogServiceImpl implements OperateLogService {

	@Autowired
	private OperateLogMapper operateLogMapper;

	@Override
	public OperateLogDto save(OperateLogDto operateLogDto) {
		OperateLog operateLog = new OperateLog();
		BeanUtils.copyProperties(operateLogDto, operateLog);
		operateLogMapper.insert(operateLog);
		BeanUtils.copyProperties(operateLog, operateLogDto);
		return operateLogDto;
	}

	@Override
	public OperateLogDto findById(Integer id) {
		OperateLog operateLog = operateLogMapper.selectByPrimaryKey(id);
		OperateLogDto operateLogDto = new OperateLogDto();
		BeanUtils.copyProperties(operateLog, operateLogDto);
		return operateLogDto;
	}

	@Override
	public List<OperateLogDto> find(String operatorNo, String operatorType, String operatorName, String requestIp,
			String operationErrorCode, String startTime, String endTime, Integer index, Integer limit) {
		OperateLogExample operateLogExample = new OperateLogExample();

		Criteria criteria = operateLogExample.createCriteria();

		if (!StringUtils.isEmpty(operatorNo)) {
			criteria.andOperatorNoEqualTo(operatorNo);
		}
		if (operatorType != null) {
			criteria.andOperatorTypeEqualTo(operatorType);
		}
		if (!StringUtils.isEmpty(operatorName)) {
			criteria.andOperatorNameLike("%" + operatorName + "%");
		}
		if (!StringUtils.isEmpty(requestIp)) {
			criteria.andRequestIpLike("%" + requestIp + "%");
		}
		if (!StringUtils.isEmpty(operationErrorCode)) {
			criteria.andOperationErrorCodeEqualTo(operationErrorCode);
		}

		try {
			criteria.andOperationStartTimeGreaterThanOrEqualTo(DateUtil.stringToDate(startTime));
		} catch (Exception e) {
		}

		try {
			criteria.andOperationStartTimeLessThanOrEqualTo(DateUtil.stringToDate(endTime));
		} catch (Exception e) {
		}

		if (index != null && limit != null) {
			PageHelper.offsetPage(index, limit);

		}

		List<OperateLog> operateLogList = operateLogMapper.selectByExampleWithBLOBs(operateLogExample);
		if (operateLogList == null || operateLogList.isEmpty()) {
			return null;
		}

		List<OperateLogDto> operateLogDtoList = new ArrayList<OperateLogDto>();
		for (OperateLog operateLog : operateLogList) {
			OperateLogDto operateLogDto = new OperateLogDto();
			BeanUtils.copyProperties(operateLog, operateLogDto);
			operateLogDtoList.add(operateLogDto);
		}
		return operateLogDtoList;
	}

	@Override
	public List<OperateLogDto> find(OperateLogQueryDto operateLogQueryDto) {
		return find(null, null, null, null, null, operateLogQueryDto.getFromTime(),
				operateLogQueryDto.getToTime(), null, null);
	}
}
