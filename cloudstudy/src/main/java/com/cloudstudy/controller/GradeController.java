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

import com.cloudstudy.dto.CalResultDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.CourseQueryParamDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.GradeQueryParamDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "学生课程模块")
@RestController
@RequestMapping("/cloudstudy/student/course")
@CrossOrigin
@SuppressWarnings("unused")
public class GradeController {

	@Autowired
	private GradeService GradeService;

	@ApiOperation(value = "获取单个学生课程", notes = "传入学生课程编号获取单个学生课程")
	@ApiImplicitParam(name = "primaryKey", value = "学生课程编号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{primaryKey}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Grade:view") // 权限管理;
	public @ResponseBody WebResult<GradeDto> find(@PathVariable("primaryKey") Integer primaryKey) {
		GradeDto GradeDto = GradeService.findById(primaryKey);
		return WebResultUtil.success(GradeDto);
	}

	@ApiOperation(value = "获取学生课程列表", notes = "获取学生课程列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Grade:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<GradeQueryDto>>> list(
			@ApiParam(value = "系统用户查询条件") @RequestBody GradeQueryParamDto GradeQueryParamDto) {
		PageResultDto<List<GradeQueryDto>> GradeDtoList = GradeService.find(GradeQueryParamDto);
		if (GradeDtoList == null || GradeDtoList.getTotal() == null || GradeDtoList.getContent() == null
				|| GradeDtoList.getContent().isEmpty()) {
			GradeDtoList = new PageResultDto<List<GradeQueryDto>>((long) 0, new ArrayList<GradeQueryDto>());
		}
		return WebResultUtil.success(GradeDtoList);
	}

	@ApiOperation(value = "新建学生课程", notes = "新建一个学生课程")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "studentNo", dataType = "String", value = "学生编号", required = true, paramType = "body"),
			@ApiImplicitParam(name = "courseId", dataType = "Integer", value = "课程编号", required = true, paramType = "body") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/changeCommit", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Grade:add") // 权限管理;
	public @ResponseBody WebResult<GradeDto> changeCommit(
			@RequestParam(value = "studentNo", required = true) String studentNo,
			@RequestParam(value = "courseId", required = true) Integer courseId) throws IOException {
		GradeDto GradeDto = GradeService.changeCommit(studentNo, courseId);
		return WebResultUtil.success(GradeDto);
	}

	@ApiOperation(value = "计算学生成绩", notes = "计算学生成绩")
	@ApiImplicitParam(name = "studentNo", dataType = "Integer", value = "学生学号", required = true, paramType = "body")
	@RequestMapping(value = "/calByStudent", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Grade:del") // 权限管理;
	public @ResponseBody WebResult<CalResultDto> calByStudent(
			@RequestParam(value = "studentNo", required = true) String studentNo) {
		CalResultDto CalResultDto = GradeService.calByStudent(studentNo);
		return WebResultUtil.success(CalResultDto);
	}

	@ApiOperation(value = "计算课程成绩", notes = "计算课程成绩")
	@ApiImplicitParam(name = "courseId", dataType = "Integer", value = "课程编号", required = true, paramType = "body")
	@RequestMapping(value = "/calByCourse", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Grade:del") // 权限管理;
	public @ResponseBody WebResult<CalResultDto> calByCourse(
			@RequestParam(value = "courseId", required = true) Integer courseId) {
		CalResultDto CalResultDto = GradeService.calByCourse(courseId);
		return WebResultUtil.success(CalResultDto);
	}
}
