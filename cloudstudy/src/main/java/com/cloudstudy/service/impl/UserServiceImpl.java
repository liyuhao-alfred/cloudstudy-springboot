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
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.RoleToUserExample;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.UserExample.Criteria;
import com.cloudstudy.constant.RoleConstant;
import com.cloudstudy.constant.RoleTypeConstant;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.FileOriginDto;
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
import com.cloudstudy.mapper.FileToJobMapper;
import com.cloudstudy.mapper.FileToTaskMapper;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.RoleMapper;
import com.cloudstudy.mapper.RoleToUserMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.service.GradeService;
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.RoleService;
import com.cloudstudy.service.TaskService;
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
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileToCourseMapper fileToCourseMapper;
	@Autowired
	private FileToTaskMapper fileToTaskMapper;
	@Autowired
	private FileToJobMapper fileToJobMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private JobService jobService;
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
		if (roleList != null) {
			for (String roleCode : roleList) {
				roleService.saveRoleByUserNoAndRoleCode(roleCode, user.getNo());
			}
		}

		return generateDto(user);
	}

	@Override
	@LogPointcut(description = "删除用户", code = "deleteByNo")
	public void deleteByNo(String no) throws IOException {
		fileOriginService.deleteByUserNo(no);
		userMapper.deleteByPrimaryKey(no);
	}

	@Override
	@LogPointcut(description = "更新用户", code = "update")
	public UserDto update(UserDto userDto) {
		User user = generate(userDto);
		userMapper.updateByPrimaryKeyWithBLOBs(user);
		return generateDto(user);
	}

	@Override
	@LogPointcut(description = "通过主键查找用户", code = "findByno")
	public UserDto findUserByNo(String no) {
		User user = userMapper.selectByPrimaryKey(no);
		UserDto userDto = generateDto(user);

		RoleToUserExample roleToUserExample = new RoleToUserExample();
		com.cloudstudy.bo.RoleToUserExample.Criteria criteria = roleToUserExample.createCriteria();
		criteria.andUserNoEqualTo(no);
		List<RoleToUser> roleToUserList = roleToUserMapper.selectByExample(roleToUserExample);
		HashSet<String> roleTypeSet = new HashSet<String>();
		if (roleToUserList != null && !roleToUserList.isEmpty()) {
			for (RoleToUser roleToUser : roleToUserList) {
				// roleTypeSet.add(roleToUser.getRoleId());TODO
			}
		}
		userDto.setRoleByList(new ArrayList<String>(roleTypeSet));

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
			userExample.or()//
					.andAccountLike("%" + keyword + "%")//
					.andEmailLike("%" + keyword + "%")//
					.andNameLike("%" + keyword + "%")//
					.andNoLike("%" + keyword + "%")//
					.andPhoneLike("%" + keyword + "%");//
		}

		ArrayList<String> daterangementList = userQueryDto.getDaterangement();
		if (daterangementList != null && daterangementList.size() == 2) {
			criteria.andRegistTimeBetween(DateUtil.stringToDateSpecial(daterangementList.get(0)),
					DateUtil.stringToDateSpecial(daterangementList.get(1)));
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

		Integer page = userQueryDto.getPageDto().getCurrent();
		Integer rows = userQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);
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

	@Override
	public UserDto findAdminByNo(String no) {
		UserDto userDto = findUserByNo(no);
		List<String> roleTypeSet = userDto.getRole();
		if (roleTypeSet.contains(RoleTypeConstant.adminType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findTeacherByNo(String no) {
		UserDto userDto = findUserByNo(no);
		List<String> roleTypeSet = userDto.getRole();
		if (roleTypeSet.contains(RoleTypeConstant.teacherType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findStudentByNo(String no) {
		UserDto userDto = findUserByNo(no);
		List<String> roleTypeSet = userDto.getRole();
		if (roleTypeSet.contains(RoleTypeConstant.studentType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findAdminByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		List<String> roleTypeSet = userDto.getRole();
		if (roleTypeSet.contains(RoleTypeConstant.adminType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findTeacherByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		List<String> roleTypeSet = userDto.getRole();
		if (roleTypeSet.contains(RoleTypeConstant.teacherType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findStudentByAccount(String account) {
		UserDto userDto = findUserByAccount(account);
		List<String> roleTypeSet = userDto.getRole();
		;
		if (roleTypeSet.contains(RoleTypeConstant.studentType)) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserDto findTeacherByCourseId(Integer courseId) {
		Course course = courseMapper.selectByPrimaryKey(courseId);
		if (course == null) {
			return null;
		}
		User user = userMapper.selectByPrimaryKey(course.getTeacherNo());
		return generateDto(user);
	}

	@Override
	public List<UserDto> findStudentByCourseId(Integer courseId) {
		Course course = courseMapper.selectByPrimaryKey(courseId);
		if (course == null) {
			return null;
		}

		List<GradeDto> gradeDtoList = gradeService.findByCourseId(courseId);
		if (gradeDtoList == null || gradeDtoList.isEmpty()) {
			return null;
		}
		List<String> primaryKeyList = new ArrayList<String>();
		for (GradeDto gradeDto : gradeDtoList) {
			primaryKeyList.add(gradeDto.getStudentNo());
		}

		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andNoIn(primaryKeyList);

		List<User> userList = userMapper.selectByExample(userExample);

		return generateDto(userList);
	}

	@Override
	public UserDto findTeacherByTaskId(Integer taskId) {
		CourseDto courseDto = courseService.findByTaskId(taskId);
		return findTeacherByCourseId(courseDto.getId());
	}

	@Override
	public UserDto findStudentByJobId(Integer jobId) {
		CourseDto courseDto = courseService.findByJobId(jobId);
		return findTeacherByCourseId(courseDto.getId());
	}

	private List<String> getPrimaryKeyList(List<User> userList) {
		if (userList == null || userList.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> primaryKeyList = new ArrayList<String>();
		for (User user : userList) {
			primaryKeyList.add(user.getNo());
		}
		return primaryKeyList;
	}

	private List<UserDto> generateDto(List<User> userList) {
		if (userList == null || userList.isEmpty()) {
			return new ArrayList<UserDto>();
		}
		List<UserDto> UserDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = generateDto(user);
			UserDtoList.add(userDto);
		}
		return UserDtoList;
	}

	private List<User> generate(List<UserDto> userDtoList) {
		if (userDtoList == null || userDtoList.isEmpty()) {
			return new ArrayList<User>();
		}
		List<User> UserList = new ArrayList<User>();
		for (UserDto userDto : userDtoList) {
			User user = generate(userDto);
			UserList.add(user);
		}
		return UserList;
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

		if (userDto.getRole() == null || userDto.getRole().size() == 0) {
			ArrayList<String> roleType = new ArrayList<String>();
			List<RoleDto> roleDtoList = roleService.findRoleByUserNo(userDto.getNo());
			for (RoleDto roleDto : roleDtoList) {
				roleType.add(roleDto.getCode());
			}
			userDto.setRoleByList(roleType);
		}

		userDto.setToken(userDto.getRole().get(0));

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

			List<CourseDto> courseDtoList = courseService.findByTeacherNo(userDto.getNo());
			if (courseDtoList != null) {
				for (CourseDto courseDto : courseDtoList) {
					CourseQueryDto courseQueryDto = new CourseQueryDto();
					BeanUtils.copyProperties(courseDto, courseQueryDto);
					userDto.getTeachCourse().add(courseQueryDto);
				}
			}

		} else if (userDto.getRole().contains(RoleConstant.student.getDescription())) {
			String statusMeno = userDto.getStatus() ? "在读" : "肄业";
			userDto.setStatusMemo(statusMeno);

			List<GradeDto> gradeDtoList = gradeService.findByStudentNo(userDto.getNo());
			if (gradeDtoList != null) {
				for (GradeDto gradeDto : gradeDtoList) {
					GradeQueryDto gradeQueryDto = new GradeQueryDto();
					BeanUtils.copyProperties(gradeDto, gradeQueryDto);
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
		User user = new User();
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
			age = DateUtil.yearsBetween(DateUtil.dateToString((user.getBirthday())), DateUtil.dateToString(new Date()));
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

		if (user.getLastModifyTime() == null) {
			user.setLastModifyTime(new Date());
		}

		if (user.getSalt() == null || user.getSalt().isEmpty()) {
			user.setSalt(Util.generateSerialNo());
		}

		return user;
	}
}
