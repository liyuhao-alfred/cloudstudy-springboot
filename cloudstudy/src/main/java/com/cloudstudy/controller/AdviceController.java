package com.cloudstudy.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.cloudstudy.dto.WebResult;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.util.WebResultUtil;

/**
 * controller 增强器
 * 
 * @author liyuhao
 * @since 2018年1月19日09:36:01
 */
@ControllerAdvice
@CrossOrigin
public class AdviceController {

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "liyuhao");
	}

	/**
	 * 全局异常捕捉处理
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public WebResult<?> errorHandler(Exception ex) {
		ex.printStackTrace();
		String errorMsg = "";
		try {
			throw ex;
		} catch (CloudStudyException e) {
			CloudStudyException cloudStudyException = (CloudStudyException) ex;
			errorMsg = cloudStudyException.getErrorMsg();
		} catch (DuplicateKeyException e) {
			errorMsg = "数据库操作错误";
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}

		return WebResultUtil.error(errorMsg);
	}

}