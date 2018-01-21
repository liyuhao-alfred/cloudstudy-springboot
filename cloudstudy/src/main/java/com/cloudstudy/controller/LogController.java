package com.cloudstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.OperatelogDto;
import com.cloudstudy.dto.OperatelogQueryParamDto;
import com.cloudstudy.dto.SystemlogDto;
import com.cloudstudy.dto.SystemlogQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.log.OperatelogService;
import com.cloudstudy.log.SystemlogService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "日志模块")
@RestController
@RequestMapping("/cloudstudy/log")
public class LogController {

	@Autowired
	private OperatelogService operatelogService;
	@Autowired
	private SystemlogService systemlogService;

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
	// @RequiresPermissions("Systemlog:view")
	public @ResponseBody WebResult<SystemlogDto> findSystemlog(@PathVariable("id") Integer id) {
		SystemlogDto systemlogDto = systemlogService.findById(id);
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
	// @RequiresPermissions("Systemlog:query")
	public @ResponseBody WebResult<List<SystemlogDto>> findSystemlogTest(
			@RequestParam(value = "index", required = true) Integer index,
			@RequestParam(value = "limit", required = true) Integer limit) {
		List<SystemlogDto> systemlogDtoList = systemlogService.find(null, null, null, null, null, null, null, null,
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
	// @RequiresPermissions("Systemlog:query")
	public @ResponseBody WebResult<List<SystemlogDto>> findSystemlog(
			@RequestParam(value = "systemlogQueryParamDto", required = true) SystemlogQueryParamDto systemlogQueryParamDto) {
		List<SystemlogDto> systemlogDtoList = systemlogService.find(systemlogQueryParamDto);
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
	@RequestMapping(value = "/single/operatelog/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Operatelog:view")
	public @ResponseBody WebResult<OperatelogDto> findOperatelog(@PathVariable("id") Integer id) {
		OperatelogDto operatelogDto = operatelogService.findById(id);
		return WebResultUtil.success(operatelogDto);
	}

	/**
	 * 获取日志列表
	 * 
	 * @param operatelog
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取操作员日志列表", notes = "获取操作员日志列表")
	@RequestMapping(value = "/list/operatelog", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Operatelog:query")
	public @ResponseBody WebResult<List<OperatelogDto>> findOperatelog(
			@RequestParam(value = "operatelogQueryParamDto", required = true) OperatelogQueryParamDto operatelogQueryParamDto) {
		List<OperatelogDto> operatelogDtoList = operatelogService.find(operatelogQueryParamDto);
		return WebResultUtil.success(operatelogDtoList);
	}

}
