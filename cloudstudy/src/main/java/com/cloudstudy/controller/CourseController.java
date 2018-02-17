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
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.CourseStudentDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "课程模块")
@RestController
@RequestMapping("/cloudstudy/teacher/course")
@CrossOrigin
@SuppressWarnings("unused")
public class CourseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private GradeService GradeService;

	@ApiOperation(value = "获取单个课程", notes = "传入课程编号获取单个课程")
	@ApiImplicitParam(name = "primaryKey", value = "课程工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{primaryKey}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Course:view") // 权限管理;
	public @ResponseBody WebResult<CourseDto> find(@PathVariable("primaryKey") Integer primaryKey) {
		CourseDto courseDto = courseService.findById(primaryKey);
		return WebResultUtil.success(courseDto);
	}

	@ApiOperation(value = "获取课程列表", notes = "获取课程列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<CourseQueryDto>>> list(
			@ApiParam(value = "系统用户查询条件") @RequestBody CourseQueryParamDto CourseQueryParamDto) {
		PageResultDto<List<CourseQueryDto>> courseDtoList = courseService.find(CourseQueryParamDto);
		if (courseDtoList == null || courseDtoList.getTotal() == null || courseDtoList.getContent() == null
				|| courseDtoList.getContent().isEmpty()) {
			courseDtoList = new PageResultDto<List<CourseQueryDto>>((long) 0, new ArrayList<CourseQueryDto>());
		}
		return WebResultUtil.success(courseDtoList);
	}

	@ApiOperation(value = "获取课程列表", notes = "获取课程列表")
	@RequestMapping(value = "/option", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<CourseQueryDto>>> option(
			@ApiParam(value = "系统用户查询条件") @RequestBody CourseQueryParamDto CourseQueryParamDto) {

		if (OptionConstant.courseQueryDtoPageResultDto != null
				&& OptionConstant.courseQueryDtoPageResultDto.getTotal() != null
				&& OptionConstant.courseQueryDtoPageResultDto.getContent() != null
				&& !OptionConstant.courseQueryDtoPageResultDto.getContent().isEmpty()) {
			return WebResultUtil.success(OptionConstant.courseQueryDtoPageResultDto);
		}

		PageResultDto<List<CourseQueryDto>> courseDtoList = courseService.find(CourseQueryParamDto);
		OptionConstant.courseQueryDtoPageResultDto = courseDtoList;

		if (courseDtoList == null || courseDtoList.getTotal() == null || courseDtoList.getContent() == null
				|| courseDtoList.getContent().isEmpty()) {
			courseDtoList = new PageResultDto<List<CourseQueryDto>>((long) 0, new ArrayList<CourseQueryDto>());
		}
		return WebResultUtil.success(courseDtoList);
	}

	@ApiOperation(value = "获取课程列表", notes = "获取课程列表")
	@RequestMapping(value = "/listForStudent", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Course:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<CourseStudentDto>>> listForStudent(
			@ApiParam(value = "系统用户查询条件") @RequestBody CourseQueryParamDto CourseQueryParamDto) {
		PageResultDto<List<CourseStudentDto>> courseDtoList = courseService.findForStudent(CourseQueryParamDto);
		if (courseDtoList == null || courseDtoList.getTotal() == null || courseDtoList.getContent() == null
				|| courseDtoList.getContent().isEmpty()) {
			courseDtoList = new PageResultDto<List<CourseStudentDto>>((long) 0, new ArrayList<CourseStudentDto>());
		}
		return WebResultUtil.success(courseDtoList);
	}

	@ApiOperation(value = "新建课程", notes = "新建一个课程")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "courseDto", value = "课程数据", required = true, paramType = "body", dataType = "CourseDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/declare", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:add") // 权限管理;
	public @ResponseBody WebResult<CourseDto> declare(
			@ApiParam(value = "课程数据", required = true) @RequestBody CourseDto courseDto) throws IOException {
		courseDto = courseService.declare(courseDto);
		return WebResultUtil.success(courseDto);
	}

	@ApiOperation(value = "更新课程", notes = "更新已存在课程")
	@ApiImplicitParam(name = "course", value = "课程数据", required = true, paramType = "body", dataType = "Course")
	@RequestMapping(value = "/update", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:update") // 权限管理;
	public @ResponseBody WebResult<CourseDto> update(
			@ApiParam(value = "课程数据", required = true) @RequestBody CourseDto courseDto) throws IOException {
		courseDto = courseService.update(courseDto);
		return WebResultUtil.success(courseDto);
	}

	@ApiOperation(value = "删除课程", notes = "通过课程工号或者学号删除课程")
	@ApiImplicitParam(name = "primaryKey", value = "课程工号或者学号", required = true, paramType = "body", dataType = "Integer")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Course:delete") // 权限管理;
	public @ResponseBody WebResult<CourseDto> delete(
			@RequestParam(value = "primaryKey", required = true) Integer primaryKey) throws IOException {
		CourseDto courseDto = courseService.findById(primaryKey);
		courseService.delete(primaryKey);
		return WebResultUtil.success(courseDto);
	}

	@ApiOperation(value = "对学生课程评分", notes = "对学生课程评分")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "gradePoint", dataType = "Integer", value = "课程分数", required = true, paramType = "body"),
			@ApiImplicitParam(name = "gradeId", dataType = "Integer", value = "学生课程ID", required = true, paramType = "body") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/changeGrade", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Grade:add") // 权限管理;
	public @ResponseBody WebResult<GradeDto> changeGrade(
			@RequestParam(value = "gradePoint", required = true) Integer gradePoint,
			@RequestParam(value = "gradeId", required = true) Integer gradeId) throws IOException {
		GradeDto GradeDto = GradeService.changeGrade(gradePoint, gradeId);
		return WebResultUtil.success(GradeDto);
	}

}
