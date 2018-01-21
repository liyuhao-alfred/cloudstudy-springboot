package com.cloudstudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.shiro.token.UserToken;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "登录模块")
@RestController
public class LoginController {

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
	public @ResponseBody WebResult<?> loginIn(HttpSession session, HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "账号", required = true) @RequestParam(value = "account", required = true) String account,
			@ApiParam(value = "密码", required = true) @RequestParam(value = "password", required = true) String password) {

		UserToken token = new UserToken(account, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token); // 完成登录
		UserDto userDto = (UserDto) subject.getPrincipal();
		session.setAttribute("user", userDto);

		if (subject.isPermitted("User:add")) {
			System.out.println("00000000000");
		} else {
			System.out.println("1111111111111");
		}

		return WebResultUtil.success(subject.getPrincipal());
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
	public @ResponseBody String loginOut(HttpSession session, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			session.removeAttribute("superAdminSignPolicy");
			session.removeAttribute("operationToken");
			session.removeAttribute("operationObjMap");
			subject.logout();
		}
		return "redirect:/";
	}

}
