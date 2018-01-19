package com.cloudstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstudy.exception.UnknownResourceException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cloudstudy") // 避免swagger文档的url被拦截
public class DefaultController {

	@RequestMapping("/**")
	public void unmappedRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		throw new UnknownResourceException("There is no resource for path " + uri);
	}

}
