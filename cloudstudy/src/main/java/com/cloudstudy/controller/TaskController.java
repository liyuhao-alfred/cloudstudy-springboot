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

import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "教师发布的作业模块")
@RestController
@RequestMapping("/cloudstudy/teacher/homewrok")
public class TaskController {

	@Autowired
	private TaskService taskService;

	/**
	 * 获取单个教师发布的作业
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取单个教师发布的作业", notes = "传入工号或者学号获取单个教师发布的作业")
	@ApiImplicitParam(name = "id", value = "教师发布的作业工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("Task:view") // 权限管理;
	public @ResponseBody WebResult<TaskDto> find(@PathVariable("id") Integer id) {
		TaskDto taskDto = taskService.findById(id);
		return WebResultUtil.success(taskDto);
	}

	/**
	 * 获取教师发布的作业列表
	 * 
	 * @param task
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取教师发布的作业列表", notes = "获取教师发布的作业列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Task:del") // 权限管理;
	public @ResponseBody WebResult<List<TaskDto>> find(
			@RequestParam(value = "homeworkQueryDto", required = true) HomeworkQueryDto homeworkQueryDto) {
		List<TaskDto> taskDtoList = taskService.find(homeworkQueryDto);
		return WebResultUtil.success(taskDtoList);
	}

	/**
	 * 新建教师发布的作业
	 * 
	 * @param taskDto
	 * @return
	 */
	@ApiOperation(value = "新建教师发布的作业", notes = "新建一个教师发布的作业")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskDto", value = "教师发布的作业数据", required = true, paramType = "body", dataType = "TaskDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Task:add") // 权限管理;
	public @ResponseBody WebResult<TaskDto> add(
			@ApiParam(value = "教师发布的作业数据", required = true) @RequestBody TaskDto taskDto) {
		taskDto = taskService.add(taskDto);
		return WebResultUtil.success(taskDto);
	}

	/**
	 * 更新教师发布的作业
	 * 
	 * @param taskDto
	 * @return
	 */
	@ApiOperation(value = "更新教师发布的作业", notes = "更新已存在教师发布的作业")
	@ApiImplicitParam(name = "task", value = "教师发布的作业数据", required = true, paramType = "body", dataType = "Task")
	@RequestMapping(value = "/update", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Task:update") // 权限管理;
	public @ResponseBody WebResult<TaskDto> update(
			@ApiParam(value = "教师发布的作业数据", required = true) @RequestBody TaskDto taskDto) {
		taskDto = taskService.update(taskDto);
		return WebResultUtil.success(taskDto);
	}

	/**
	 * 删除教师发布的作业
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除教师发布的作业", notes = "通过教师发布的作业工号或者学号删除教师发布的作业")
	@ApiImplicitParam(name = "id", value = "教师发布的作业工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("Task:delete") // 权限管理;
	public @ResponseBody WebResult<TaskDto> delete(@RequestParam(value = "id", required = true) Integer id) {
		TaskDto taskDto = taskService.findById(id);
		taskService.delete(id);
		return WebResultUtil.success(taskDto);
	}

}
