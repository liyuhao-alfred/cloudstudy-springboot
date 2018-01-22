package com.cloudstudy.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.constant.Constant;
import com.cloudstudy.constant.RoleTypeConstant;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.service.MenuService;
import com.cloudstudy.service.UserService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private UserService userService;
	@Autowired
	private Constant constant;

	@Override
	public HashSet<String> generateMenu(String account, Integer roleTyppe) {
		UserDto userDto = userService.findUserByAccount(account);
		List<Integer> roleTypeSet = userDto.getRoleType();
		if (roleTypeSet.contains(RoleTypeConstant.superAdminType)) {
			return getSuperAdminMenu();
		} else if (roleTypeSet.contains(RoleTypeConstant.adminType)) {
			return getAdminMenu();
		} else if (roleTypeSet.contains(RoleTypeConstant.teacherType)) {
			return getTeacherMenu();
		} else if (roleTypeSet.contains(RoleTypeConstant.studentType)) {
			return getStudentMenu();
		} else {
			throw new CloudStudyException("生成菜单失败！");
		}
	}

	private HashSet<String> getSuperAdminMenu() {
		HashSet<String> menuSet = new HashSet<String>();
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);

		return menuSet;
	}

	private HashSet<String> getAdminMenu() {
		HashSet<String> menuSet = new HashSet<String>();
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);

		return menuSet;
	}

	private HashSet<String> getTeacherMenu() {
		HashSet<String> menuSet = new HashSet<String>();
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);

		return menuSet;
	}

	private HashSet<String> getStudentMenu() {
		HashSet<String> menuSet = new HashSet<String>();
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);
		menuSet.add(constant.cloudstudy_project_html_course_student_add);

		return menuSet;
	}

}
