package com.cloudstudy.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.CourserelstudentDto;
import com.cloudstudy.dto.CourserelstudentQueryDto;
import com.cloudstudy.dto.CourserelteacherQueryDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.CourserelstudentService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "课程模块")
@RestController
@RequestMapping("/cloudstudy/student/course")
public class CourseStudentController {

	@Autowired
	private CourserelstudentService courserelstudentService;

	/**
	 * 获取单个课程
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取单个课程", notes = "传入工号或者学号获取单个课程")
	@ApiImplicitParam(name = "id", value = "课程工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("Course:view") // 权限管理;
	public @ResponseBody WebResult<CourserelstudentDto> find(@PathVariable("id") Integer id) {
		CourserelstudentDto courserelteacherDto = courserelstudentService.findById(id);
		return WebResultUtil.success(courserelteacherDto);
	}

	/**
	 * 获取课程列表
	 * 
	 * @param course
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取课程列表", notes = "获取课程列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Course:del") // 权限管理;
	public @ResponseBody WebResult<List<CourserelstudentDto>> find(
			@RequestParam(value = "courseQueryParamDto", required = true) CourserelstudentQueryDto courserelstudentQueryDto) {
		List<CourserelstudentDto> courseDtoList = courserelstudentService.find(courserelstudentQueryDto);
		return WebResultUtil.success(courseDtoList);
	}

	/**
	 * 新建课程
	 * 
	 * @param courserelteacherDto
	 * @return
	 */
	@ApiOperation(value = "新建课程", notes = "新建一个课程")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "courserelteacherDto", value = "课程数据", required = true, paramType = "body", dataType = "CourserelstudentDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Course:add") // 权限管理;
	public @ResponseBody WebResult<CourserelstudentDto> add(
			@ApiParam(value = "课程数据", required = true) @RequestBody CourserelstudentDto courserelteacherDto) {
		courserelteacherDto = courserelstudentService.add(courserelteacherDto);
		return WebResultUtil.success(courserelteacherDto);
	}

	/**
	 * 更新课程
	 * 
	 * @param courserelteacherDto
	 * @return
	 */
	@ApiOperation(value = "更新课程", notes = "更新已存在课程")
	@ApiImplicitParam(name = "course", value = "课程数据", required = true, paramType = "body", dataType = "Course")
	@RequestMapping(value = "/update", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Course:update") // 权限管理;
	public @ResponseBody WebResult<CourserelstudentDto> update(
			@ApiParam(value = "课程数据", required = true) @RequestBody CourserelstudentDto courserelteacherDto) {
		courserelteacherDto = courserelstudentService.update(courserelteacherDto);
		return WebResultUtil.success(courserelteacherDto);
	}

	/**
	 * 删除课程
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除课程", notes = "通过课程工号或者学号删除课程")
	@ApiImplicitParam(name = "id", value = "课程工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Course:delete") // 权限管理;
	public @ResponseBody WebResult<CourserelstudentDto> delete(
			@RequestParam(value = "id", required = true) Integer id) {
		CourserelstudentDto courserelteacherDto = courserelstudentService.findById(id);
		courserelstudentService.delete(id);
		return WebResultUtil.success(courserelteacherDto);
	}

}
