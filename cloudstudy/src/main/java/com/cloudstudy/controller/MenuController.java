package com.cloudstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "菜单模块")
@RestController
@RequestMapping("/cloudstudy/menu")
public class MenuController {

	@Autowired
	private UserService userService;

	/**
	 * 获取单个管理员
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个管理员", notes = "传入no获取单个管理员")
	@ApiImplicitParam(name = "no", value = "管理员no", required = true, paramType = "path", dataType = "String") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/api/user/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String find(@PathVariable("no") String no) {
		UserDto user = userService.findByNo(no);
		return user.toString();
	}

	/**
	 * 模拟异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "模拟异常", notes = "模拟异常")
	@RequestMapping(value = "/api/user/exception", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String exception() {
		throw new CloudStudyException("101", "错误");
	}

}
