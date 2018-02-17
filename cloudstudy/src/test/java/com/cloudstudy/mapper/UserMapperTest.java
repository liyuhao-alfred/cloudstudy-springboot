package com.cloudstudy.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cloudstudy.Application;
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToHomework;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.User;
import com.cloudstudy.util.GenerateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {
	@Autowired
	CourseMapper CourseMapper;
	@Autowired
	FileOriginMapper FileOriginMapper;
	@Autowired
	FileToCourseMapper FileToCourseMapper;
	@Autowired
	FileToHomeworkMapper FileToHomeworkMapper;
	@Autowired
	GradeMapper GradeMapper;
	@Autowired
	HomeworkMapper HomeworkMapper;
	@Autowired
	RoleToUserMapper RoleToUserMapper;
	@Autowired
	UserMapper UserMapper;

	public List<User> superAdminList = new ArrayList<User>();
	public List<User> adminList = new ArrayList<User>();
	public List<User> teacherList = new ArrayList<User>();
	public List<User> studentList = new ArrayList<User>();
	public List<Course> courseList = new ArrayList<Course>();
	public List<Homework> homeworkList = new ArrayList<Homework>();
	public List<Grade> gradeList = new ArrayList<Grade>();
	public List<FileOrigin> fileList = new ArrayList<FileOrigin>();
	public List<FileToHomework> fileToHomeworkList = new ArrayList<FileToHomework>();

	int teacherCount = 100;
	int courseCount = 1000;
	int homeworkCount = 0;
	int studentCount = 0;

	@Test
	public void TestInsert() {
		insertSuperAdmin();
		insertAdmin();
		insertTeacher();
		insertStudent();
		insertCourse();
		insertHomeWork();
		insertGrade();
		insertFile();
	}

	public void insertSuperAdmin() {// superadmin
		int total = 5;
		for (int i = 0; i < total; i++) {
			User superAdmin = GenerateUtil.initUser(i, "superadmin");
			UserMapper.insert(superAdmin);

			RoleToUser record = GenerateUtil.initRoleToUser(1, superAdmin.getNo());
			RoleToUserMapper.insert(record);

			superAdminList.add(superAdmin);
		}
	}

	public void insertAdmin() {// admin
		int total = 10;
		for (int i = 0; i < total; i++) {
			User admin = GenerateUtil.initUser(i, "admin");
			UserMapper.insert(admin);

			RoleToUser record = GenerateUtil.initRoleToUser(2, admin.getNo());
			RoleToUserMapper.insert(record);

			adminList.add(admin);
		}
	}

	public void insertTeacher() {// teacher
		int teacherTotal = 36;
		for (int i = 0; i < teacherTotal; i++) {
			teacherCount++;

			User teacher = GenerateUtil.initUser(teacherCount, "teacher");
			UserMapper.insert(teacher);

			RoleToUser RoleToUser = GenerateUtil.initRoleToUser(3, teacher.getNo());
			RoleToUserMapper.insert(RoleToUser);

			teacherList.add(teacher);
		}
	}

	public void insertStudent() {// student
		int studentTotal = 1063;
		for (int j = 0; j < studentTotal; j++) {
			studentCount++;

			User student = GenerateUtil.initUser(studentCount, "student");
			UserMapper.insert(student);

			RoleToUser RoleToStudent = GenerateUtil.initRoleToUser(4, student.getNo());
			RoleToUserMapper.insert(RoleToStudent);

			studentList.add(student);

		}
	}

	public void insertCourse() {// course
		for (User user : this.teacherList) {

			int courseTotal = (int) (Math.random() * 12);
			for (int r = 0; r < courseTotal; r++) {
				courseCount++;

				String courseName = "courseByTeacher" + user.getNo();
				Course Course = GenerateUtil.initCourse(courseCount, courseName, user.getNo());
				CourseMapper.insert(Course);

				courseList.add(Course);
			}
		}
	}

	public void insertHomeWork() {// homework
		for (Course Course : this.courseList) {
			int homeworkTotal = (int) (Math.random() * 25);
			for (int t = 0; t < homeworkTotal; t++) {
				homeworkCount++;

				String homeworkName = "homeworkByCourse" + Course.getId();
				Homework Homework = GenerateUtil.initHomework(homeworkCount, homeworkName, Course.getId());
				HomeworkMapper.insert(Homework);

				homeworkList.add(Homework);
			}
		}
	}

	public void insertGrade() {// grade
		for (User student : this.studentList) {
			int courseSelectTotal = (int) (Math.random() * 21);
			for (int i = 0; i < courseSelectTotal; i++) {
				int randomCourseId = (int) (this.courseList.size() * Math.random());
				if (randomCourseId == 0 || randomCourseId == this.courseList.size()) {
					continue;
				}
				Course Course = courseList.get(randomCourseId);

				Grade Grade = GenerateUtil.initGrade(Course.getId(), student.getNo(), courseSelectTotal,
						courseSelectTotal);
				try {
					GradeMapper.insert(Grade);
				} catch (Exception e) {
					continue;
				}

				gradeList.add(Grade);

			}
		}
	}

	public void insertFile() {// teacher
		fileList = FileOriginMapper.selectByExample(null);

		for (User user : studentList) {

			for (Grade grade : gradeList) {
				if (!grade.getStudentNo().equals(user.getNo())) {
					continue;
				} else {
					for (Course Course : courseList) {
						if (Course.getId().intValue() != grade.getCourseId().intValue()) {
							continue;
						} else {
							for (Homework Homework : homeworkList) {
								if (Homework.getCourseId().intValue() != Course.getId().intValue()) {
									continue;
								} else {
									int tempFileListIndex = (int) (fileList.size() * Math.random());
									FileOrigin FileOrigin = fileList.get(tempFileListIndex);
									FileOrigin.setId(null);
									FileOriginMapper.insert(FileOrigin);
									fileList.add(FileOrigin);

									FileToHomework FileToHomework = new FileToHomework();
									FileToHomework.setFileId(FileOrigin.getId());
									FileToHomework.setHomeworkId(Homework.getId());
									FileToHomework.setUploaderno(user.getNo());
									try {
										FileToHomeworkMapper.insert(FileToHomework);
										fileToHomeworkList.add(FileToHomework);
									} catch (Exception e) {
										continue;
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
