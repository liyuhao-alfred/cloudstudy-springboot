package com.cloudstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstudy.exception.UnknownResourceException;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {
	@RequestMapping("/**")
	public void unmappedRequest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		throw new UnknownResourceException("There is no resource for path " + uri);
	}

}
