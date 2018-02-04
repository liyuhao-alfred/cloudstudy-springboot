package com.cloudstudy.controller;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "系统用户模块")
@RestController
@RequestMapping("/cloudstudy/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 获取单个系统用户
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个系统用户", notes = "传入工号或者学号获取单个系统用户")
	@ApiImplicitParam(name = "no", value = "系统用户工号或者学号", required = true, paramType = "path", dataType = "String") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("User:view") // 权限管理;
	public @ResponseBody WebResult<UserDto> find(@PathVariable("no") String no) {
		UserDto userDto = userService.findUserByNo(no);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 获取系统用户列表
	 * 
	 * @param user
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取系统用户列表", notes = "获取系统用户列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("User:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<UserQueryDto>>> find(
			@ApiParam(value = "系统用户查询条件", required = true) @RequestBody UserQueryParamDto userQueryDto) {
		PageResultDto<List<UserQueryDto>> userDtoList = userService.find(userQueryDto);
		return WebResultUtil.success(userDtoList);

	}

	/**
	 * 新建系统用户
	 * 
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "新建系统用户", notes = "新建一个系统用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userDto", value = "系统用户数据", required = true, paramType = "body", dataType = "UserDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/api/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("User:add") // 权限管理;
	public @ResponseBody WebResult<UserDto> add(
			@ApiParam(value = "系统用户数据", required = true) @RequestBody UserDto userDto) {
		System.out.println(userDto.getRole());
		userDto = userService.save(userDto);

		return WebResultUtil.success(userDto);
	}

	/**
	 * 更新系统用户
	 * 
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "更新系统用户", notes = "更新已存在系统用户")
	@ApiImplicitParam(name = "user", value = "系统用户数据", required = true, paramType = "body", dataType = "User")
	@RequestMapping(value = "/api/update", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("User:update") // 权限管理;
	public @ResponseBody WebResult<UserDto> update(
			@ApiParam(value = "系统用户数据", required = true) @RequestBody UserDto userDto) {
		userDto = userService.update(userDto);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 删除系统用户
	 *
	 * @param no
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "删除系统用户", notes = "通过系统用户工号或者学号删除系统用户")
	@ApiImplicitParam(name = "no", value = "系统用户工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("User:delete") // 权限管理;
	public @ResponseBody WebResult<UserDto> delete(@RequestParam(value = "no", required = true) String no)
			throws IOException {
		UserDto userDto = userService.findUserByNo(no);
		userService.deleteByNo(no);
		return WebResultUtil.success(userDto);
	}

}
