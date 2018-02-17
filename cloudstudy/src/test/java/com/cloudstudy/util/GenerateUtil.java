
package com.cloudstudy.util;

import java.util.Date;

import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.User;

public class GenerateUtil {

	public static User initUser(int index, String name) {
		String text = name + index;
		Date date = new Date();
		Integer status = 0;
		Integer count = index + 1000;

		User User = new User();
		User.setAccount(text);
		User.setAge(count);
		User.setBirthday(date);
		User.setCreateTime(date);
		User.setDescription(text);
		User.setEmail(text);
		User.setLastModifyTime(date);
		User.setName(text);
		User.setNo(text);
		User.setPassword(text);
		User.setPhone(text);
		User.setRegistTime(date);
		User.setSalt(text);
		User.setSex(status);
		User.setStatus(status);

		return User;
	}

	public static RoleToUser initRoleToUser(int roleId, String userNo) {

		RoleToUser RoleToUser = new RoleToUser();
		RoleToUser.setRoleId(roleId);
		RoleToUser.setUserNo(userNo);

		return RoleToUser;
	}

	public static Course initCourse(int index, String name, String teacherNo) {
		String text = name + index;
		Date date = new Date();
		Integer status = 0;
		Integer count = index + 1000;

		Course Course = new Course();
		Course.setAcceptNum(count);
		Course.setBeginTime(date);
		Course.setCount(count);
		Course.setCreateTime(date);
		Course.setDeclareNum(count);
		Course.setDescription(text);
		Course.setEndTime(DateUtil.dateAddMonth(date, 3));
		Course.setLastModifyTime(date);
		Course.setName(text);
		Course.setStatus(status);
		Course.setTeacherNo(teacherNo);

		return Course;
	}

	public static Homework initHomework(int index, String name, int courseId) {
		String text = name + index;
		Date date = new Date();
		Integer status = 0;

		Homework Homework = new Homework();
		Homework.setContent(text);
		Homework.setCourseId(courseId);
		Homework.setCreateTime(date);
		Homework.setDeadLine(DateUtil.dateAddMonth(date, 2));
		Homework.setLastModifyTime(date);
		Homework.setStatus(status);
		Homework.setTitle(text);

		return Homework;
	}

	public static Grade initGrade(int courseId, String studentNo, int homeworkAcceptNum, int homeworkDeclareNum) {
		Date date = new Date();
		Integer status = 0;

		Grade Grade = new Grade();
		Grade.setCourseId(courseId);
		Grade.setCreateTime(date);
		Grade.setGrade((int) Math.random() * 100);
		Grade.setHomeworkAcceptNum(homeworkAcceptNum);
		Grade.setHomeworkDeclareNum(homeworkDeclareNum);
		Grade.setLastModifyTime(date);
		Grade.setStatus(status);
		Grade.setStudentNo(studentNo);

		return Grade;
	}

}