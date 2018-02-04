package com.cloudstudy.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.PermissionService;
import com.cloudstudy.service.RoleService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "系统权限模块")
@RestController
@RequestMapping("/cloudstudy/role")
@CrossOrigin
@SuppressWarnings("unused")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 获取单个系统权限
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个系统权限", notes = "传入工号或者学号获取单个系统权限")
	@ApiImplicitParam(name = "userNo", value = "系统权限工号或者学号", required = true, paramType = "path", dataType = "String") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{userNo}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("Role:view") // 权限管理;
	public @ResponseBody WebResult<List<RoleDto>> find(@PathVariable("userNo") String userNo) {
		List<RoleDto> roleDtoList = roleService.findRoleByUserNo(userNo);
		return WebResultUtil.success(roleDtoList);
	}

	/**
	 * 获取系统权限列表
	 * 
	 * @param role
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取系统权限列表", notes = "获取系统权限列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("Role:query") // 权限管理;
	public @ResponseBody WebResult<List<RoleDto>> find() {
		List<RoleDto> roleDtoList = roleService.findAllRoleWithPermission();
		return WebResultUtil.success(roleDtoList);
	}

}
