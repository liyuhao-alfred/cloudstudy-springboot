/******************************************************************
 *    
 *    Package:     com.cloudstudy.constant
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月16日 下午5:35:56
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月16日 下午5:35:56 
 * @version 1.0.0  
 */
package com.cloudstudy.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class Constant {

	@Value("${cloudstudy.project.name}")
	public String projectName;
	@Value("${cloudstudy.project.version}")
	public String projectVersion;

	@Value("${cloudstudy.project.html.user.add}")
	public String cloudstudy_project_html_user_add;
	@Value("${cloudstudy.project.html.user.delete}")
	public String cloudstudy_project_html_user_delete;
	@Value("${cloudstudy.project.html.user.edit}")
	public String cloudstudy_project_html_user_edit;
	@Value("${cloudstudy.project.html.user.search}")
	public String cloudstudy_project_html_user_search;

	@Value("${cloudstudy.project.html.course.teacher.add}")
	public String cloudstudy_project_html_course_teacher_add;
	@Value("${cloudstudy.project.html.course.teacher.delete}")
	public String cloudstudy_project_html_course_teacher_delete;
	@Value("${cloudstudy.project.html.course.teacher.edit}")
	public String cloudstudy_project_html_course_teacher_edit;
	@Value("${cloudstudy.project.html.course.teacher.search}")
	public String cloudstudy_project_html_course_teacher_search;

	@Value("${cloudstudy.project.html.course.student.add}")
	public String cloudstudy_project_html_course_student_add;
	@Value("${cloudstudy.project.html.course.student.delete}")
	public String cloudstudy_project_html_course_student_delete;
	@Value("${cloudstudy.project.html.course.student.edit}")
	public String cloudstudy_project_html_course_student_edit;
	@Value("${cloudstudy.project.html.course.student.search}")
	public String cloudstudy_project_html_course_student_search;

	@Value("${cloudstudy.project.html.homework.teacher.add}")
	public String cloudstudy_project_html_homework_teacher_add;
	@Value("${cloudstudy.project.html.homework.teacher.delete}")
	public String cloudstudy_project_html_homework_teacher_delete;
	@Value("${cloudstudy.project.html.homework.teacher.edit}")
	public String cloudstudy_project_html_homework_teacher_edit;
	@Value("${cloudstudy.project.html.homework.teacher.search}")
	public String cloudstudy_project_html_homework_teacher_search;

	@Value("${cloudstudy.project.html.homework.student.add}")
	public String cloudstudy_project_html_homework_student_add;
	@Value("${cloudstudy.project.html.homework.student.delete}")
	public String cloudstudy_project_html_homework_student_delete;
	@Value("${cloudstudy.project.html.homework.student.edit}")
	public String cloudstudy_project_html_homework_student_edit;
	@Value("${cloudstudy.project.html.homework.student.search}")
	public String cloudstudy_project_html_homework_student_search;

	@Value("${cloudstudy.project.html.file.add}")
	public String cloudstudy_project_html_file_add;
	@Value("${cloudstudy.project.html.file.delete}")
	public String cloudstudy_project_html_file_delete;
	@Value("${cloudstudy.project.html.file.edit}")
	public String cloudstudy_project_html_file_edit;
	@Value("${cloudstudy.project.html.file.search}")
	public String cloudstudy_project_html_file_search;

	public static String testStr;

}