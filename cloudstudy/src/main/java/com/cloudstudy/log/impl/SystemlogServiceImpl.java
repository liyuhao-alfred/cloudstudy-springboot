package com.cloudstudy.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.Systemlog;
import com.cloudstudy.bo.example.SystemlogExample;
import com.cloudstudy.bo.example.SystemlogExample.Criteria;
import com.cloudstudy.dto.SystemlogDto;
import com.cloudstudy.log.SystemlogService;
import com.cloudstudy.mapper.SystemlogMapper;

@Service
public class SystemlogServiceImpl implements SystemlogService {

	@Autowired
	private SystemlogMapper systemlogMapper;

	@Override
	public SystemlogDto save(SystemlogDto systemlogDto) {
		Systemlog systemlog = new Systemlog();
		BeanUtils.copyProperties(systemlogDto, systemlog);
		systemlogMapper.insert(systemlog);
		BeanUtils.copyProperties(systemlog, systemlogDto);
		return systemlogDto;
	}

	@Override
	public SystemlogDto findById(Integer id) {
		Systemlog systemlog = systemlogMapper.selectByPrimaryKey(id);
		SystemlogDto systemlogDto = new SystemlogDto();
		BeanUtils.copyProperties(systemlog, systemlogDto);
		return systemlogDto;
	}

	@Override
	public List<SystemlogDto> find(String logLevel, String logType, String remoteCallIp, String serviceClass,
			String serviceMethod, String serviceErrorCode, Integer index, Integer limit) {
		SystemlogExample systemlogExample = new SystemlogExample();
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

		if (index != null && limit != null) {

		}

		List<Systemlog> systemlogList = systemlogMapper.selectByExampleWithBLOBs(systemlogExample);
		if (systemlogList == null || systemlogList.isEmpty()) {
			return null;
		}

		List<SystemlogDto> systemlogDtoList = new ArrayList<SystemlogDto>();
		for (Systemlog systemlog : systemlogList) {
			SystemlogDto systemlogDto = new SystemlogDto();
			BeanUtils.copyProperties(systemlog, systemlogDto);
			systemlogDtoList.add(systemlogDto);
		}
		return systemlogDtoList;
	}

}
