package com.cloudstudy.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.SystemLog;
import com.cloudstudy.bo.SystemLogExample;
import com.cloudstudy.bo.SystemLogExample.Criteria;
import com.cloudstudy.dto.SystemLogDto;
import com.cloudstudy.dto.SystemLogQueryDto;
import com.cloudstudy.log.SystemLogService;
import com.cloudstudy.mapper.SystemLogMapper;
import com.cloudstudy.util.DateUtil;
import com.github.pagehelper.PageHelper;

@Service
public class SystemLogServiceImpl implements SystemLogService {

	@Autowired
	private SystemLogMapper systemlogMapper;

	@Override
	public SystemLogDto save(SystemLogDto systemlogDto) {
		SystemLog systemlog = new SystemLog();
		BeanUtils.copyProperties(systemlogDto, systemlog);
		systemlogMapper.insert(systemlog);
		BeanUtils.copyProperties(systemlog, systemlogDto);
		return systemlogDto;
	}

	@Override
	public SystemLogDto findById(Integer id) {
		SystemLog systemlog = systemlogMapper.selectByPrimaryKey(id);
		SystemLogDto systemlogDto = new SystemLogDto();
		BeanUtils.copyProperties(systemlog, systemlogDto);
		return systemlogDto;
	}

	@Override
	public List<SystemLogDto> find(String logLevel, String logType, String remoteCallIp, String serviceClass,
			String serviceMethod, String serviceErrorCode, String startTime, String endTime, Integer index,
			Integer limit) {
		SystemLogExample systemlogExample = new SystemLogExample();
		systemlogExample.setDistinct(true);

		Criteria criteria = systemlogExample.createCriteria();

		if (!StringUtils.isEmpty(logLevel)) {
			criteria.andLogLevelEqualTo(logLevel);
		}
		if (logType != null) {
			criteria.andLogTypeEqualTo(logType);
		}
		if (!StringUtils.isEmpty(remoteCallIp)) {
			criteria.andRemoteCallIpLike("%" + remoteCallIp + "%");
		}
		if (!StringUtils.isEmpty(serviceClass)) {
			criteria.andServiceClassLike("%" + serviceClass + "%");
		}
		if (!StringUtils.isEmpty(serviceMethod)) {
			criteria.andServiceMethodLike("%" + serviceMethod + "%");
		}
		if (!StringUtils.isEmpty(serviceErrorCode)) {
			criteria.andServiceErrorCodeEqualTo(serviceErrorCode);
		}

		try {
			criteria.andLogTimeGreaterThanOrEqualTo(DateUtil.stringToDate(startTime));
		} catch (Exception e) {
		}

		try {
			criteria.andLogTimeLessThanOrEqualTo(DateUtil.stringToDate(endTime));
		} catch (Exception e) {
		}

		if (index != null && limit != null) {
			PageHelper.startPage(index, limit);
		}

		List<SystemLog> systemlogList = systemlogMapper.selectByExampleWithBLOBs(systemlogExample);
		if (systemlogList == null || systemlogList.isEmpty()) {
			return null;
		}

		System.out.println(systemlogList.size());

		List<SystemLogDto> systemlogDtoList = new ArrayList<SystemLogDto>();
		for (SystemLog systemlog : systemlogList) {
			SystemLogDto systemlogDto = new SystemLogDto();
			BeanUtils.copyProperties(systemlog, systemlogDto);
			systemlogDtoList.add(systemlogDto);
		}
		return systemlogDtoList;
	}

	@Override
	public List<SystemLogDto> find(SystemLogQueryDto systemlogQueryDto) {
		return find(null, null, null, null, null, null, systemlogQueryDto.getFromTime(),
				systemlogQueryDto.getToTime(), null, null);
	}

}
