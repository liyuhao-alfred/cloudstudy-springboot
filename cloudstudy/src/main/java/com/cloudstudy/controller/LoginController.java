package com.cloudstudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.service.UserService;
import com.cloudstudy.shiro.token.UserToken;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "登录模块")
@RestController
@CrossOrigin
public class LoginController {
	@Autowired
	private UserService userService;

	/**
	 * 登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "登入", notes = "使用账号和密码进行登录")
	@RequestMapping(value = "/login", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@LogPointcut(description = "用户登录", code = "userlogin")
	public @ResponseBody WebResult<?> login(HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "账号", required = true) @RequestParam(value = "account", required = true) String account,
			@ApiParam(value = "密码", required = true) @RequestParam(value = "password", required = true) String password) {

		UserToken token = new UserToken(account, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token); // 完成登录
		UserDto userDto = (UserDto) subject.getPrincipal();
		session.setAttribute("user", userDto);
		subject.isPermitted("User:add");
		return WebResultUtil.success(subject.getPrincipal());
	}

	/**
	 * 登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "获取信息", notes = "使用账号获取信息")
	@RequestMapping(value = "/getUserInfo", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@LogPointcut(description = "用户登录", code = "getUserInfo")
	public @ResponseBody WebResult<?> getUserInfo(
			@ApiParam(value = "账号", required = true) @RequestParam(value = "account", required = true) String account) {
		UserDto userDto = userService.findUserByAccount(account);
		return WebResultUtil.success(userDto);
	}

	/**
	 * 登出
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "登出", notes = "进行登出")
	@RequestMapping(value = "/logout", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@LogPointcut(description = "用户登出", code = "userlogout")
	public @ResponseBody WebResult<?> logout(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			session.removeAttribute("user");
			session.removeAttribute("operationToken");
			session.removeAttribute("operationObjMap");
			subject.logout();
		}
		return WebResultUtil.success();
	}

}
