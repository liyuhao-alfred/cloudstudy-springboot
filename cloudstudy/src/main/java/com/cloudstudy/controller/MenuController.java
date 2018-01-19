package com.cloudstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.dto.AdminDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "菜单模块")
@RestController
@RequestMapping("/cloudstudy/menu")
public class MenuController {

	@Autowired
	private AdminService adminService;

	/**
	 * 获取单个管理员
	 * 
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "获取单个管理员", notes = "传入no获取单个管理员")
	@ApiImplicitParam(name = "no", value = "管理员no", required = true, paramType = "path", dataType = "String") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/api/admin/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String find(@PathVariable("no") String no) {
		Admin admin = adminService.findAdmin(no);
		return admin.toString();
	}

	/**
	 * 模拟异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "模拟异常", notes = "模拟异常")
	@RequestMapping(value = "/api/admin/exception", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String exception() {
		throw new CloudStudyException("101", "错误");
	}

	/**
	 * 获取管理员列表
	 * 
	 * @param admin
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
	@RequestMapping(value = "/api/adminList", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<Admin> find(@RequestParam(value = "admin", required = true) Admin admin,
			@RequestParam(value = "keyword", required = true) String keyword) {
		List<Admin> adminList = adminService.findAdmin(admin, keyword);
		return adminList;
	}

	/**
	 * 
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/api/admin", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String login(@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "password", required = true) String password) {
		Admin admin = adminService.loginAdmin(account, password);
		return admin.toString();
	}

	/**
	 * 新建管理员
	 * 
	 * @param adminDto
	 * @return
	 */
	@ApiOperation(value = "新建管理员", notes = "新建一个管理员")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "adminDto", value = "管理员数据", required = true, paramType = "body", dataType = "AdminDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/api/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AdminDto add(@ApiParam(value = "管理员数据", required = true) @RequestBody AdminDto adminDto) {
		adminDto = adminService.saveAdmin(adminDto);
		return adminDto;
	}

	/**
	 * 更新管理员
	 * 
	 * @param adminDto
	 * @return
	 */
	@ApiOperation(value = "更新管理员", notes = "更新已存在管理员")
	@ApiImplicitParam(name = "admin", value = "管理员数据", required = true, paramType = "body", dataType = "Admin")
	@RequestMapping(value = "/api/update", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Admin update(@RequestBody Admin admin) {
		admin = adminService.updateAdmin(admin);
		return admin;
	}

	/**
	 * 删除管理员
	 *
	 * @param no
	 * @return
	 */
	@ApiOperation(value = "删除管理员", notes = "通过管理员no删除管理员")
	@ApiImplicitParam(name = "no", value = "管理员no", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/api/delete", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String delete(@RequestParam(value = "no", required = true) String no) {
		adminService.deleteAdmin(no);
		return "success 删除Admin" + no;
	}

	/**
	 * 模拟查找
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cloudstudy")
	public String find() {
		Admin admin = adminService.loginAdmin("88888888", "88888888");
		return admin.toString();
	}

}
