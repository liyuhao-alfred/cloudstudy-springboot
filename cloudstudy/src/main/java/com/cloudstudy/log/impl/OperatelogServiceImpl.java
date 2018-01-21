package com.cloudstudy.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Operatelog;
import com.cloudstudy.bo.example.OperatelogExample;
import com.cloudstudy.bo.example.OperatelogExample.Criteria;
import com.cloudstudy.dto.OperatelogDto;
import com.cloudstudy.log.OperatelogService;
import com.cloudstudy.mapper.OperatelogMapper;
import com.cloudstudy.util.DateUtil;

@Service
public class OperatelogServiceImpl implements OperatelogService {

	@Autowired
	private OperatelogMapper operatelogMapper;

	@Override
	public OperatelogDto save(OperatelogDto operatelogDto) {
		Operatelog operatelog = new Operatelog();
		BeanUtils.copyProperties(operatelogDto, operatelog);
		operatelogMapper.insert(operatelog);
		BeanUtils.copyProperties(operatelog, operatelogDto);
		return operatelogDto;
	}

	@Override
	public OperatelogDto findById(Integer id) {
		Operatelog operatelog = operatelogMapper.selectByPrimaryKey(id);
		OperatelogDto operatelogDto = new OperatelogDto();
		BeanUtils.copyProperties(operatelog, operatelogDto);
		return operatelogDto;
	}

	@Override
	public List<OperatelogDto> find(String operatorNo, String operatorType, String operatorName, String requestIp,
			String operationErrorCode, String startTime, String endTime, Integer index, Integer limit) {
		OperatelogExample operatelogExample = new OperatelogExample();
		operatelogExample.setDistinct(true);

		Criteria criteria = operatelogExample.createCriteria();

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

		}

		List<Operatelog> operatelogList = operatelogMapper.selectByExampleWithBLOBs(operatelogExample);
		if (operatelogList == null || operatelogList.isEmpty()) {
			return null;
		}

		List<OperatelogDto> operatelogDtoList = new ArrayList<OperatelogDto>();
		for (Operatelog operatelog : operatelogList) {
			OperatelogDto operatelogDto = new OperatelogDto();
			BeanUtils.copyProperties(operatelog, operatelogDto);
			operatelogDtoList.add(operatelogDto);
		}
		return operatelogDtoList;
	}
}
