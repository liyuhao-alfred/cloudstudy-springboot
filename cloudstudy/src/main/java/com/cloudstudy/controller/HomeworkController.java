package com.cloudstudy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.constant.OptionConstant;
import com.cloudstudy.dto.CalResultDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.HomeworkDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.HomeworkQueryParamDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.HomeworkService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "学生课程模块")
@RestController
@RequestMapping("/cloudstudy/homework")
@CrossOrigin
@SuppressWarnings("unused")
public class HomeworkController {

	@Autowired
	private HomeworkService HomeworkService;

	@ApiOperation(value = "获取单个学生课程", notes = "传入学生课程编号获取单个学生课程")
	@ApiImplicitParam(name = "primaryKey", value = "学生课程编号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{primaryKey}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Homework:view") // 权限管理;
	public @ResponseBody WebResult<HomeworkDto> find(@PathVariable("primaryKey") Integer primaryKey) {
		HomeworkDto HomeworkDto = HomeworkService.findById(primaryKey);
		return WebResultUtil.success(HomeworkDto);
	}

	@ApiOperation(value = "获取学生课程列表", notes = "获取学生课程列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Homework:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<HomeworkQueryDto>>> list(
			@ApiParam(value = "系统用户查询条件") @RequestBody HomeworkQueryParamDto HomeworkQueryParamDto) {
		PageResultDto<List<HomeworkQueryDto>> HomeworkDtoList = HomeworkService.find(HomeworkQueryParamDto);
		if (HomeworkDtoList == null || HomeworkDtoList.getTotal() == null || HomeworkDtoList.getContent() == null
				|| HomeworkDtoList.getContent().isEmpty()) {
			HomeworkDtoList = new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		}
		return WebResultUtil.success(HomeworkDtoList);
	}

	@ApiOperation(value = "获取学生课程列表", notes = "获取学生课程列表")
	@RequestMapping(value = "/option", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Homework:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<HomeworkQueryDto>>> option(
			@ApiParam(value = "系统用户查询条件") @RequestBody HomeworkQueryParamDto HomeworkQueryParamDto) {

		if (OptionConstant.homeworkQueryDtoPageResultDto != null
				&& OptionConstant.homeworkQueryDtoPageResultDto.getTotal() != null
				&& OptionConstant.homeworkQueryDtoPageResultDto.getContent() != null
				&& !OptionConstant.homeworkQueryDtoPageResultDto.getContent().isEmpty()) {
			return WebResultUtil.success(OptionConstant.homeworkQueryDtoPageResultDto);
		}

		PageResultDto<List<HomeworkQueryDto>> HomeworkDtoList = HomeworkService.find(HomeworkQueryParamDto);
		OptionConstant.homeworkQueryDtoPageResultDto = HomeworkDtoList;

		if (HomeworkDtoList == null || HomeworkDtoList.getTotal() == null || HomeworkDtoList.getContent() == null
				|| HomeworkDtoList.getContent().isEmpty()) {
			HomeworkDtoList = new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		}
		return WebResultUtil.success(HomeworkDtoList);
	}

	@ApiOperation(value = "获取学生课程列表", notes = "获取学生课程列表")
	@RequestMapping(value = "/listByStudent", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Homework:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<HomeworkQueryDto>>> listByStudent(
			@ApiParam(value = "系统用户查询条件") @RequestBody HomeworkQueryParamDto HomeworkQueryParamDto) {
		PageResultDto<List<HomeworkQueryDto>> HomeworkDtoList = HomeworkService.findByStudent(HomeworkQueryParamDto);
		if (HomeworkDtoList == null || HomeworkDtoList.getTotal() == null || HomeworkDtoList.getContent() == null
				|| HomeworkDtoList.getContent().isEmpty()) {
			HomeworkDtoList = new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		}
		return WebResultUtil.success(HomeworkDtoList);
	}

	@ApiOperation(value = "获取学生课程列表", notes = "获取学生课程列表")
	@RequestMapping(value = "/listByCourse", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Homework:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<HomeworkQueryDto>>> listByCourse(
			@ApiParam(value = "系统用户查询条件") @RequestBody HomeworkQueryParamDto HomeworkQueryParamDto) {
		PageResultDto<List<HomeworkQueryDto>> HomeworkDtoList = HomeworkService.findByCourse(HomeworkQueryParamDto);
		if (HomeworkDtoList == null || HomeworkDtoList.getTotal() == null || HomeworkDtoList.getContent() == null
				|| HomeworkDtoList.getContent().isEmpty()) {
			HomeworkDtoList = new PageResultDto<List<HomeworkQueryDto>>((long) 0, new ArrayList<HomeworkQueryDto>());
		}
		return WebResultUtil.success(HomeworkDtoList);
	}

	@ApiOperation(value = "新建课程", notes = "新建一个课程")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "homeworkDto", dataType = "homeworkDto", value = "课程数据", required = true, paramType = "body") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/declare", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:add") // 权限管理;
	public @ResponseBody WebResult<HomeworkDto> declare(
			@ApiParam(value = "课程数据", required = true) @RequestBody HomeworkDto homeworkDto) throws IOException {
		homeworkDto = HomeworkService.declare(homeworkDto);
		return WebResultUtil.success(homeworkDto);
	}

	@ApiOperation(value = "更新课程", notes = "更新已存在课程")
	@ApiImplicitParam(name = "homework", value = "课程数据", required = true, paramType = "body", dataType = "Homework")
	@RequestMapping(value = "/update", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Homework:update") // 权限管理;
	public @ResponseBody WebResult<HomeworkDto> update(
			@ApiParam(value = "课程数据", required = true) @RequestBody HomeworkDto homeworkDto) throws IOException {
		homeworkDto = HomeworkService.update(homeworkDto);
		return WebResultUtil.success(homeworkDto);
	}

	@ApiOperation(value = "删除课程", notes = "通过课程工号或者学号删除课程")
	@ApiImplicitParam(name = "primaryKey", value = "课程工号或者学号", required = true, paramType = "body", dataType = "Integer")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Homework:delete") // 权限管理;
	public @ResponseBody WebResult<HomeworkDto> delete(
			@RequestParam(value = "primaryKey", required = true) Integer primaryKey) throws IOException {
		HomeworkDto homeworkDto = HomeworkService.findById(primaryKey);
		HomeworkService.delete(primaryKey);
		return WebResultUtil.success(homeworkDto);
	}

}
