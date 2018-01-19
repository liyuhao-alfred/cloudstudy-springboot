package com.cloudstudy.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "用户模块")
@RestController
@RequestMapping("/cloudstudy/user")
public class UserController {

	@Autowired
	private UserService userService;

	/***
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@LogPointcut(description = "静态页面请求--跳转到登录页面")
	public String loginPage() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated() ? "redirect:/index" : "login";
	}

	/**
	 * 获取单个用户
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个用户", notes = "传入工号或者学号获取单个用户")
	@ApiImplicitParam(name = "no", value = "用户工号或者学号", required = true, paramType = "path", dataType = "String") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("User:view") // 权限管理;
	public @ResponseBody WebResult<UserDto> find(@PathVariable("no") String no) {
		UserDto userDto = userService.findByno(no);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 模拟异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "模拟异常", notes = "模拟异常")
	@RequestMapping(value = "/exception", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String exception() {
		throw new CloudStudyException("101", "错误");
	}

	/**
	 * 获取用户列表
	 * 
	 * @param user
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取用户列表", notes = "获取用户列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("User:del") // 权限管理;
	public @ResponseBody WebResult<List<UserDto>> find(
			@RequestParam(value = "userQueryParamDto", required = true) UserQueryParamDto userQueryParamDto,
			@RequestParam(value = "keyword", required = true) String keyword) {
		List<UserDto> userDtoList = userService.find(userQueryParamDto, keyword);
		return WebResultUtil.success(userDtoList);
	}

	/**
	 * 新建用户
	 * 
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "新建用户", notes = "新建一个用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userDto", value = "用户数据", required = true, paramType = "body", dataType = "UserDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/api/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("User:add") // 权限管理;
	public @ResponseBody WebResult<UserDto> add(
			@ApiParam(value = "用户数据", required = true) @RequestBody UserDto userDto) {
		userDto = userService.save(userDto);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 更新用户
	 * 
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "更新用户", notes = "更新已存在用户")
	@ApiImplicitParam(name = "user", value = "用户数据", required = true, paramType = "body", dataType = "User")
	@RequestMapping(value = "/api/update", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("User:update") // 权限管理;
	public @ResponseBody WebResult<UserDto> update(
			@ApiParam(value = "用户数据", required = true) @RequestBody UserDto userDto) {
		userDto = userService.update(userDto);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 删除用户
	 *
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "删除用户", notes = "通过用户工号或者学号删除用户")
	@ApiImplicitParam(name = "no", value = "用户工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("User:delete") // 权限管理;
	public @ResponseBody WebResult<UserDto> delete(@RequestParam(value = "no", required = true) String no) {
		UserDto userDto = userService.findByno(no);
		userService.deleteByNo(no);
		return WebResultUtil.success(userDto);
	}

}
