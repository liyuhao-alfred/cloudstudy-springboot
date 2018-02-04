package com.cloudstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.OperateLogDto;
import com.cloudstudy.dto.OperateLogQueryDto;
import com.cloudstudy.dto.SystemLogDto;
import com.cloudstudy.dto.SystemLogQueryDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.log.OperateLogService;
import com.cloudstudy.log.SystemLogService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "日志模块")
@RestController
@RequestMapping("/cloudstudy/log")
@CrossOrigin
public class LogController {

	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SystemLogService systemlogService;

	/**
	 * 获取单个系统日志
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个系统日志", notes = "传入日志编号获取单个系统日志")
	@ApiImplicitParam(name = "id", value = "系统日志编号", required = true, paramType = "path", dataType = "int")
	@RequestMapping(value = "/single/systemlog/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// //@RequiresPermissions("SystemLog:view")
	public @ResponseBody WebResult<SystemLogDto> findSystemLog(@PathVariable("id") Integer id) {
		SystemLogDto systemlogDto = systemlogService.findById(id);
		return WebResultUtil.success(systemlogDto);
	}

	/**
	 * 获取日志列表
	 * 
	 * @param systemlog
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取系统日志列表", notes = "获取系统日志列表")
	@RequestMapping(value = "/list/systemlog/test", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// //@RequiresPermissions("SystemLog:query")
	public @ResponseBody WebResult<List<SystemLogDto>> findSystemLogTest(
			@RequestParam(value = "index", required = true) Integer index,
			@RequestParam(value = "limit", required = true) Integer limit) {
		List<SystemLogDto> systemlogDtoList = systemlogService.find(null, null, null, null, null, null, null, null,
				index, limit);
		return WebResultUtil.success(systemlogDtoList);
	}

	/**
	 * 获取日志列表
	 * 
	 * @param systemlog
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取系统日志列表", notes = "获取系统日志列表")
	@RequestMapping(value = "/list/systemlog", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// //@RequiresPermissions("SystemLog:query")
	public @ResponseBody WebResult<List<SystemLogDto>> findSystemLog(
			@RequestParam(value = "systemlogQueryDto", required = true) SystemLogQueryDto systemlogQueryDto) {
		List<SystemLogDto> systemlogDtoList = systemlogService.find(systemlogQueryDto);
		return WebResultUtil.success(systemlogDtoList);
	}

	/**
	 * 获取单个日志
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取单个操作员日志", notes = "传入日志编号获取单个操作员日志")
	@ApiImplicitParam(name = "id", value = "操作员日志编号", required = true, paramType = "path", dataType = "int")
	@RequestMapping(value = "/single/operateLog/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// //@RequiresPermissions("OperateLog:view")
	public @ResponseBody WebResult<OperateLogDto> findOperateLog(@PathVariable("id") Integer id) {
		OperateLogDto operateLogDto = operateLogService.findById(id);
		return WebResultUtil.success(operateLogDto);
	}

	/**
	 * 获取日志列表
	 * 
	 * @param operateLog
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取操作员日志列表", notes = "获取操作员日志列表")
	@RequestMapping(value = "/list/operateLog", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// //@RequiresPermissions("OperateLog:query")
	public @ResponseBody WebResult<List<OperateLogDto>> findOperateLog(
			@RequestParam(value = "operateLogQueryDto", required = true) OperateLogQueryDto operateLogQueryDto) {
		List<OperateLogDto> operateLogDtoList = operateLogService.find(operateLogQueryDto);
		return WebResultUtil.success(operateLogDtoList);
	}

}
