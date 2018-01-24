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

import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.JobService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "学生提交的作业模块")
@RestController
@RequestMapping("/cloudstudy/student/homework")
public class JobController {

	@Autowired
	private JobService jobService;

	/**
	 * 获取单个学生提交的作业
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取单个学生提交的作业", notes = "传入工号或者学号获取单个学生提交的作业")
	@ApiImplicitParam(name = "id", value = "学生提交的作业工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("Job:view") // 权限管理;
	public @ResponseBody WebResult<JobDto> find(@PathVariable("id") Integer id) {
		JobDto jobDto = jobService.findById(id);
		return WebResultUtil.success(jobDto);
	}

	/**
	 * 获取学生提交的作业列表
	 * 
	 * @param job
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取学生提交的作业列表", notes = "获取学生提交的作业列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Job:del") // 权限管理;
	public @ResponseBody WebResult<List<JobDto>> find(
			@RequestParam(value = "jobQueryDto", required = true) HomeworkQueryDto jobQueryDto) {
		List<JobDto> jobDtoList = jobService.find(jobQueryDto);
		return WebResultUtil.success(jobDtoList);
	}

	/**
	 * 新建学生提交的作业
	 * 
	 * @param jobDto
	 * @return
	 */
	@ApiOperation(value = "新建学生提交的作业", notes = "新建一个学生提交的作业")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobDto", value = "学生提交的作业数据", required = true, paramType = "body", dataType = "JobDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Job:add") // 权限管理;
	public @ResponseBody WebResult<JobDto> add(
			@ApiParam(value = "学生提交的作业数据", required = true) @RequestBody JobDto jobDto) {
		jobDto = jobService.add(jobDto);
		return WebResultUtil.success(jobDto);
	}

	/**
	 * 删除学生提交的作业
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除学生提交的作业", notes = "通过学生提交的作业工号或者学号删除学生提交的作业")
	@ApiImplicitParam(name = "id", value = "学生提交的作业工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Job:delete") // 权限管理;
	public @ResponseBody WebResult<JobDto> delete(@RequestParam(value = "id", required = true) Integer id) {
		JobDto jobDto = jobService.findById(id);
		jobService.delete(id);
		return WebResultUtil.success(jobDto);
	}

}
