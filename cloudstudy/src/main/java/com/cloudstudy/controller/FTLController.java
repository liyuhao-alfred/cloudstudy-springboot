package com.cloudstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.service.AdminService;

import java.util.List;

/**
 * 城市 Controller 实现 Restful HTTP 服务
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Controller
public class FTLController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/ftl/admin/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String find(@PathVariable("no") String no, Model model) {
		Admin admin = adminService.findAdmin(no);
		model.addAttribute("admin", admin);
		return "admin";
	}

	@RequestMapping(value = "/ftl/adminList", method = RequestMethod.GET)
	public String find(Model model) {
		List<Admin> adminList = adminService.findAdmin(new Admin(), null);
		model.addAttribute("adminList", adminList);
		return "adminList";
	}
}
