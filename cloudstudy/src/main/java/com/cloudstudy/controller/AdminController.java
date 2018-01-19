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

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/api/admin/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String find(@PathVariable("no") String no) {
		Admin admin = adminService.findAdmin(no);
		return admin.toString();
	}

	@RequestMapping(value = "/api/admin/exception", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String exception() {
		throw new CloudStudyException("101", "错误");
	}

	@RequestMapping(value = "/api/adminList", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<Admin> find(@RequestParam(value = "admin", required = true) Admin admin,
			@RequestParam(value = "keyword", required = true) String keyword) {
		List<Admin> adminList = adminService.findAdmin(admin, keyword);
		return adminList;
	}

	@RequestMapping(value = "/api/admin", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String login(@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "password", required = true) String password) {
		Admin admin = adminService.loginAdmin(account, password);
		return admin.toString();
	}

	@RequestMapping(value = "/api/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AdminDto add(@RequestBody AdminDto adminDto) {
		adminDto = adminService.saveAdmin(adminDto);
		return adminDto;
	}

	@RequestMapping(value = "/cloudstudy")
	public String find() {
		Admin admin = adminService.loginAdmin("88888888", "88888888");
		return admin.toString();
	}

}
