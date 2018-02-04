package com.cloudstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.service.UserService;

import java.util.List;

/**
 * 城市 Controller 实现 Restful HTTP 服务
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Controller
@RequestMapping("/ftl")
@CrossOrigin
public class FTLController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/{no}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String find(@PathVariable("no") String no, Model model) {
		UserDto user = userService.findUserByNo(no);
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String find(Model model) {
//		List<UserDto> userList = userService.find(new UserQueryDto());
//		model.addAttribute("userList", userList);
		return "userList";
	}
}
