package com.cloudstudy.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudstudy.aop.annotation.LogPointcut;
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.GradeExample;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.RoleToUserExample;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.RoleConstant;
import com.cloudstudy.constant.RoleTypeConstant;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryDto;
import com.cloudstudy.dto.GradeDto;
import com.cloudstudy.dto.GradeQueryDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.RoleDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.dto.UserQueryParamDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.mapper.FileToCourseMapper;
import com.cloudstudy.mapper.FileToHomeworkMapper;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RoleToUserMapper;
import com.cloudstudy.mapper.HomeworkMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.RoleService;
import com.cloudstudy.service.HomeworkService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.cloudstudy.util.Util;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleToUserMapper roleToUserMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private HomeworkMapper homeworkMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileToCourseMapper fileToCourseMapper;
	@Autowired
	private FileToHomeworkMapper fileToHomeworkMapper;
	@Autowired
	private HomeworkService HomeworkService;
	@Autowired
	private FileOriginService fileOriginService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FileOriginMapper fileOriginMapper;
	@Autowired
	private CourseService courseService;
	@Value("${web.upload-path}")
	private String filePath;

	@Override
	@LogPointcut(description = "添加用户", code = "save")
	public UserDto save(UserDto userDto) {
		User user = generate(userDto);
		userMapper.insert(user);

		ArrayList<String> roleList = userDto.getRole();
		if (roleList != null && !roleList.isEmpty()) {
			for (String roleCode : roleList) {
				roleService.saveRoleByUserNoAndRoleCode(roleCode, user.getNo());
			}
		}

		return generateDto(user);
	}

	private void deleteFileByUser(String userNo) throws IOException {
		ArrayList<String> userNoList = new ArrayList<String>();
		userNoList.add(userNo);

		FileOriginQueryParamDto FileOriginQueryParamDto = new FileOriginQueryParamDto();
		FileOriginQueryParamDto.setPageDto(null);
		FileOriginQueryParamDto.setUserNo(userNoList);

		PageResultDto<List<FileOriginQueryDto>> FileOriginQueryPageResultDto = fileOriginService
				.find(FileOriginQueryParamDto);
		List<FileOriginQueryDto> content = FileOriginQueryPageResultDto.getContent();
		if (content != null && !content.isEmpty()) {
			for (FileOriginQueryDto fileOriginQueryDto : content) {
				fileOriginService.deleteById(fileOriginQueryDto.getId());
			}
		}
	}

	@Override
	@LogPointcut(description = "删除用户", code = "deleteByNo")
	public void deleteByNo(String userNo) throws IOException {
		deleteFileByUser(userNo);
		userMapper.deleteByPrimaryKey(userNo);
	}

	@Override
	@LogPointcut(description = "更新用户", code = "update")
	public UserDto update(UserDto userDto) {
		User user = userMapper.selectByPrimaryKey(userDto.getNo());
		if (user == null) {
			return save(userDto);
		} else {
			user = generate(userDto);
			userMapper.updateByPrimaryKeyWithBLOBs(user);

			roleService.deleteRoleByUserNo(user.getNo());
			ArrayList<String> roleList = userDto.getRole();
			if (roleList != null && !roleList.isEmpty()) {
				for (String roleCode : roleList) {
					roleService.saveRoleByUserNoAndRoleCode(roleCode, user.getNo());
				}
			}

			return generateDto(user);
		}

	}

	@Override
	@LogPointcut(description = "通过主键查找用户", code = "findByno")
	public UserDto findUserByNo(String no) {
		User user = userMapper.selectByPrimaryKey(no);
		UserDto userDto = generateDto(user);

		return userDto;
	}

	@LogPointcut(description = "通过权限查找用户id", code = "findUserIdByRoleCode")
	private List<String> findUserIdByRoleCode(ArrayList<String> roleCodeList) {

		RoleToUserExample roleToUserExample = new RoleToUserExample();
		com.cloudstudy.bo.RoleToUserExample.Criteria criteria = roleToUserExample.createCriteria();

		if (roleCodeList != null && !roleCodeList.isEmpty()) {
			if (roleCodeList.size() == 1) {
				criteria.andRoleIdEqualTo(RoleConstant.getId(roleCodeList.get(0)));
			} else if (roleCodeList.size() > 1) {
				ArrayList<Integer> roleIdList = new ArrayList<Integer>();
				for (String roleCode : roleCodeList) {
					roleIdList.add(RoleConstant.getId(roleCode));
				}
				criteria.andRoleIdIn(roleIdList);
			}
		}

		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		if (roleToUserList == null || roleToUserList.isEmpty()) {
			return null;
		}

		List<String> userNoList = new ArrayList<String>();
		for (RoleToUser roleToUser : roleToUserList) {
			userNoList.add(roleToUser.getUserNo());
		}

		return userNoList;
	}

	@Override
	@LogPointcut(description = "通过条件查找用户", code = "find")
	public PageResultDto<List<UserQueryDto>> find(UserQueryParamDto userQueryDto) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();

		String keyword = userQueryDto.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			criteria.andAccountLike("%" + keyword + "%")//
					.andEmailLike("%" + keyword + "%")//
					.andNameLike("%" + keyword + "%")//
					.andNoLike("%" + keyword + "%")//
					.andPhoneLike("%" + keyword + "%");//
		}
		ArrayList<Integer> courseIdList = userQueryDto.getCourseId();
		if (courseIdList != null && !courseIdList.isEmpty()) {
			GradeExample GradeExample = new GradeExample();
			GradeExample.Criteria GradeExamplecriteria = GradeExample.createCriteria();
			GradeExamplecriteria.andCourseIdIn(courseIdList);
			List<Grade> GradeList = gradeMapper.selectByExample(GradeExample);
			if (GradeList != null && !GradeList.isEmpty()) {
				ArrayList<String> studentNoList = new ArrayList<String>();
				for (Grade grade : GradeList) {
					studentNoList.add(grade.getStudentNo());
				}

				criteria.andNoIn(studentNoList);
			}
		}

		ArrayList<String> dateRangementList = userQueryDto.getDateRangement();
		if (dateRangementList != null && dateRangementList.size() == 2) {
			criteria.andRegistTimeBetween(DateUtil.stringToDateSpecial(dateRangementList.get(0)),
					DateUtil.stringToDateSpecial(dateRangementList.get(1)));
		}

		String status = userQueryDto.getStatus();
		if (status != null && !status.isEmpty()) {
			if (status.equalsIgnoreCase("on")) {
				criteria.andStatusEqualTo(0);
			} else if (status.equalsIgnoreCase("off")) {
				criteria.andStatusEqualTo(1);
			}
		}

		ArrayList<String> roleCodeList = userQueryDto.getRole();
		if (roleCodeList != null && !roleCodeList.isEmpty()) {
			List<String> userNolist = findUserIdByRoleCode(roleCodeList);
			if (userNolist != null && !userNolist.isEmpty()) {
				criteria.andNoIn(userNolist);
			}
		}

		long total = userMapper.countByExample(userExample);

		if (userQueryDto.getPageDto() != null) {
			Integer page = userQueryDto.getPageDto().getCurrent();
			Integer rows = userQueryDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<User> userList = userMapper.selectByExample(userExample);

		List<UserQueryDto> userQueryDtoList = new ArrayList<UserQueryDto>();
		if (userList == null || userList.isEmpty()) {
			return new PageResultDto<List<UserQueryDto>>(total, userQueryDtoList);
		}

		for (User user : userList) {
			UserQueryDto userDto = generateQueryDto(user);
			userQueryDtoList.add(userDto);
		}

		return new PageResultDto<List<UserQueryDto>>(total, userQueryDtoList);
	}

	@Override
	@LogPointcut(description = "通过账号查找用户", code = "findByAccount")
	public UserDto findUserByAccount(String account) {

		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andAccountEqualTo(account);
		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		User user = userList.get(0);
		UserDto userDto = generateDto(user);

		RoleToUserExample roleToUserExample = new RoleToUserExample();
		com.cloudstudy.bo.RoleToUserExample.Criteria criteria1 = roleToUserExample.createCriteria();
		criteria1.andUserNoEqualTo(userDto.getNo());
		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		HashSet<Integer> roleTypeSet = new HashSet<Integer>();
		if (roleToUserList != null && !roleToUserList.isEmpty()) {
			for (RoleToUser roleToUser : roleToUserList) {
				roleTypeSet.add(roleToUser.getRoleId());
			}
		}
		// userDto.setRole(new ArrayList<String>(roleTypeSet));TODO

		return userDto;
	}

	@Override
	@LogPointcut(description = "登录用户", code = "login")
	public UserDto login(String account, String password) {
		UserExample userExample = new UserExample();

		Criteria criteria = userExample.createCriteria();
		if (!StringUtils.isBlank(account)) {
			criteria.andAccountEqualTo(account);
		}
		if (!StringUtils.isBlank(password)) {
			criteria.andPasswordEqualTo(password);
		}

		userExample.setDistinct(true);
		userExample.setOrderByClause("account");

		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		User user = userList.get(0);
		return generateDto(user);
	}

	private UserDto generateDto(User user) {
		if (user == null) {
			throw new CloudStudyException();
		}
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		if (user.getStatus().equals(0)) {
			userDto.setStatus(true);
		} else {
			userDto.setStatus(false);
		}

		userDto.setRole(roleService.findRoleStringByUserNo(user.getNo()));

		userDto.setToken(userDto.getRole().get(0));
		userDto.setCreateTime(DateUtil.dateToString(user.getCreateTime()));
		userDto.setLastModifyTime(DateUtil.dateToString(user.getLastModifyTime()));
		userDto.setRegistTime(DateUtil.dateToString(user.getRegistTime()));
		userDto.setBirthday(DateUtil.dateToString(user.getBirthday()));

		if (user.getSex() == 0) {
			userDto.setSex("male");
		} else {
			userDto.setSex("female");
		}

		return userDto;
	}

	private UserQueryDto generateQueryDto(User user) {
		if (user == null) {
			throw new CloudStudyException();
		}
		UserQueryDto userDto = new UserQueryDto();
		BeanUtils.copyProperties(user, userDto);

		if (userDto.getRole() == null || userDto.getRole().size() == 0) {
			List<RoleDto> roleDtoList = roleService.findRoleByUserNo(userDto.getNo());

			ArrayList<String> roleTypeList = new ArrayList<String>();
			for (RoleDto roleDto : roleDtoList) {
				roleTypeList.add(roleDto.getName());
			}
			userDto.setRole(roleTypeList);
			userDto.setRoleDto(roleDtoList);
		}

		if (user.getStatus().equals(0)) {
			userDto.setStatus(true);
		} else {
			userDto.setStatus(false);
		}

		if (user.getSex().equals(0)) {
			userDto.setSex("男");
		} else {
			userDto.setSex("女");
		}

		if (userDto.getRole().contains(RoleConstant.superadmin.getDescription())
				|| userDto.getRole().contains(RoleConstant.admin.getDescription())
				|| userDto.getRole().contains(RoleConstant.teacher.getDescription())) {
			String statusMeno = userDto.getStatus() ? "在职" : "离职";
			userDto.setStatusMemo(statusMeno);

			com.cloudstudy.bo.CourseExample CourseExample = new com.cloudstudy.bo.CourseExample();
			com.cloudstudy.bo.CourseExample.Criteria criteria3 = CourseExample.createCriteria();
			criteria3.andTeacherNoEqualTo(userDto.getNo());
			List<Course> CourseList = courseMapper.selectByExample(CourseExample);
			if (CourseList != null && !CourseList.isEmpty()) {
				for (Course course : CourseList) {
					CourseQueryDto courseQueryDto = new CourseQueryDto();
					BeanUtils.copyProperties(course, courseQueryDto);
					userDto.getTeachCourse().add(courseQueryDto);
				}
			}

		} else if (userDto.getRole().contains(RoleConstant.student.getDescription())) {
			String statusMeno = userDto.getStatus() ? "在读" : "肄业";
			userDto.setStatusMemo(statusMeno);

			GradeExample gradeExample = new GradeExample();
			com.cloudstudy.bo.GradeExample.Criteria criteria = gradeExample.createCriteria();
			criteria.andStudentNoEqualTo(userDto.getNo());
			List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
			if (gradeList != null && !gradeList.isEmpty()) {
				for (Grade grade : gradeList) {
					GradeQueryDto gradeQueryDto = new GradeQueryDto();
					BeanUtils.copyProperties(grade, gradeQueryDto);
					userDto.getStudyCourse().add(gradeQueryDto);
				}
			}

		} else {
			String statusMeno = "未知";
			userDto.setStatusMemo(statusMeno);

		}

		String birthday;
		try {
			birthday = DateUtil.dateToString(user.getBirthday());
		} catch (Exception e) {
			birthday = DateUtil.getNowDateAsString();
		}
		userDto.setBirthday(birthday);

		String createTime;
		try {
			createTime = DateUtil.dateToString(user.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		userDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(user.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		userDto.setLastModifyTime(lastModifyTime);

		String registTime;
		try {
			registTime = DateUtil.dateToString(user.getRegistTime());
		} catch (Exception e) {
			registTime = DateUtil.getNowDateAsString();
		}
		userDto.setRegistTime(registTime);

		userDto.setAge(user.getAge() + "");

		if (userDto.getDescription() == null || userDto.getDescription().isEmpty()) {
			userDto.setDescription("暂无个人介绍...");
		}

		return userDto;
	}

	private User generate(UserDto userDto) {
		if (userDto == null) {
			throw new CloudStudyException();
		}

		boolean isExist = false;
		User user = userMapper.selectByPrimaryKey(userDto.getNo());
		if (user != null) {
			BeanUtils.copyProperties(userDto, user);
			if (userDto.getStatus()) {// 状态
				user.setStatus(0);
			} else {
				user.setStatus(1);
			}

			if (userDto.getSex().equalsIgnoreCase("male")) {// 性别
				user.setSex(0);
			} else {
				user.setSex(1);
			}

			try {
				user.setBirthday(DateUtil.stringToDateSpecial(userDto.getBirthday()));
				System.out.println(user.getBirthday());
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				user.setRegistTime(DateUtil.stringToDateSpecial(userDto.getRegistTime()));
			} catch (Exception e) {
				// TODO: handle exception
			}

			int age;
			try {
				age = DateUtil.yearsBetween(DateUtil.dateToString((user.getBirthday())),
						DateUtil.dateToString(new Date()));
			} catch (ParseException e) {
				age = 0;
				e.printStackTrace();
			}
			user.setAge(age);

			if (user.getPassword() == null || user.getPassword().isEmpty()) {
				user.setPassword(user.getAccount());
			}

			user.setLastModifyTime(new Date());

			if (user.getSalt() == null || user.getSalt().isEmpty()) {
				user.setSalt(Util.generateSerialNo());
			}

			return user;

		} else {
			user = new User();
			BeanUtils.copyProperties(userDto, user);

			if (userDto.getStatus()) {// 状态
				user.setStatus(0);
			} else {
				user.setStatus(1);
			}

			if (userDto.getSex().equalsIgnoreCase("male")) {// 性别
				user.setSex(0);
			} else {
				user.setSex(1);
			}

			try {
				user.setBirthday(DateUtil.stringToDateSpecial(userDto.getBirthday()));
				System.out.println(user.getBirthday());
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				user.setRegistTime(DateUtil.stringToDateSpecial(userDto.getRegistTime()));
			} catch (Exception e) {
				// TODO: handle exception
			}

			int age;
			try {
				age = DateUtil.yearsBetween(DateUtil.dateToString((user.getBirthday())),
						DateUtil.dateToString(new Date()));
			} catch (ParseException e) {
				age = 0;
				e.printStackTrace();
			}
			user.setAge(age);

			if (user.getPassword() == null || user.getPassword().isEmpty()) {
				user.setPassword(user.getAccount());
			}

			if (user.getCreateTime() == null) {
				user.setCreateTime(new Date());
			}

			user.setLastModifyTime(new Date());

			if (user.getSalt() == null || user.getSalt().isEmpty()) {
				user.setSalt(Util.generateSerialNo());
			}

			return user;
		}

	}

	@Override
	public UserDto changePassword(String account, String password) {

		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andAccountEqualTo(account);
		List<User> userList = userMapper.selectByExample(userExample);
		if (userList == null || userList.isEmpty()) {
			return null;
		}

		User user = userList.get(0);
		user.setPassword(password);
		userMapper.updateByPrimaryKeySelective(user);
		return generateDto(user);
	}
}
