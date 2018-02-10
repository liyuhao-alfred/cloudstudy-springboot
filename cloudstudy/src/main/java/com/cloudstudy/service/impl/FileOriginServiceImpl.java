package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.CourseExample;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.JobExample;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToCourseExample;
import com.cloudstudy.bo.FileToJob;
import com.cloudstudy.bo.FileToJobExample;
import com.cloudstudy.bo.FileToTask;
import com.cloudstudy.bo.FileToTaskExample;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.TaskExample;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.FileOriginExample;
import com.cloudstudy.bo.FileOriginExample.Criteria;
import com.cloudstudy.constant.FileOriginConstant;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToCourseMapper;
import com.cloudstudy.mapper.FileToJobMapper;
import com.cloudstudy.mapper.FileToTaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.cloudstudy.util.FileUtil;
import com.github.pagehelper.PageHelper;

@Service
public class FileOriginServiceImpl implements FileOriginService {

	@Autowired
	private UserService userService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private GradeMapper gradeMapper;
	@Autowired
	private FileOriginMapper fileOriginMapper;
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
	private CourseService courseService;
	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public FileOriginDto add(MultipartFile multipartFile) throws IOException {

		String fileName = null;
		if (multipartFile != null && multipartFile.getSize() > 0) {// 上传文件步骤一
			FileOriginDto fileOriginDto = new FileOriginDto();

			fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename().replaceAll(" ", "");
			fileOriginDto.setName(fileName);

			String fileExtension = FileUtil.getExtensionName(fileName);// 没有点的
			fileOriginDto.setType(fileExtension);

			Integer fileSize = (int) multipartFile.getSize();
			fileOriginDto.setSize(fileSize);

			String fileOriginPath = null;
			fileOriginPath = File.separator + "default" + File.separator + fileName;

			String absoluteFileOriginPath = filePath + fileOriginPath;
			File saveFile = FileUtil.createFile(absoluteFileOriginPath);
			multipartFile.transferTo(saveFile);
			fileOriginDto.setPath(fileOriginPath);

			fileOriginDto.setMemo(generateFileOriginMemo(fileOriginDto));

			fileOriginDto.setCreateTime(new Date());
			fileOriginDto.setLastModifyTime(new Date());

			com.cloudstudy.bo.FileOrigin fileOrigin = generate(fileOriginDto);
			fileOriginMapper.insert(fileOrigin);
			fileOriginDto.setId(fileOrigin.getId());

			addFileToOther(fileOriginDto);

			return fileOriginDto;
		} else {
			return null;
		}
	}

	@Override
	public FileOriginQueryDto add(FileToCourse fileToCourse) throws IOException {
		FileToCourseExample FileToCourseExample = new FileToCourseExample();
		com.cloudstudy.bo.FileToCourseExample.Criteria criteria = FileToCourseExample.createCriteria();
		criteria.andCourseIdEqualTo(fileToCourse.getCourseId());
		criteria.andFileIdEqualTo(fileToCourse.getFileId());
		List<FileToCourse> fileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
		if (fileToCourseList == null || fileToCourseList.isEmpty()) {
			fileToCourseMapper.insert(fileToCourse);
		}
		return findById(fileToCourse.getFileId());
	}

	@Override
	public FileOriginDto add(FileOriginDto fileOriginDto) throws IOException {
		File recFile = fileOriginDto.getFile();
		Integer fileDtoType_course = fileOriginDto.getCourseId();
		Integer fileDtoType_task = fileOriginDto.getTaskId();
		Integer fileDtoType_job = fileOriginDto.getJobId();

		String fileName = null;
		if (recFile != null && recFile.length() > 0) {// 上传文件步骤一
			fileName = System.currentTimeMillis() + "_" + recFile.getName();
			fileOriginDto.setName(fileName);

			String fileExtension = FileUtil.getExtensionName(fileName);// 没有点的
			fileOriginDto.setType(fileExtension);

			Integer fileSize = (int) recFile.length();
			fileOriginDto.setSize(fileSize);

			String fileOriginPath = null;
			if (fileDtoType_course != null) {
				fileOriginPath = File.separator + "course" + File.separator + fileName;
			} else if (fileDtoType_task != null) {
				fileOriginPath = File.separator + "task" + File.separator + fileName;
			} else if (fileDtoType_job != null) {
				fileOriginPath = File.separator + "job" + File.separator + fileName;
			} else {
				fileOriginPath = File.separator + "default" + File.separator + fileName;
			}

			String absoluteFileOriginPath = filePath + fileOriginPath;
			File saveFile = FileUtil.createFile(absoluteFileOriginPath);
			FileCopyUtils.copy(recFile, saveFile);
			fileOriginDto.setPath(fileOriginPath);

			fileOriginDto.setMemo(generateFileOriginMemo(fileOriginDto));

			fileOriginDto.setCreateTime(new Date());
			fileOriginDto.setLastModifyTime(new Date());

			com.cloudstudy.bo.FileOrigin fileOrigin = generate(fileOriginDto);
			fileOriginMapper.insert(fileOrigin);
			fileOriginDto.setId(fileOrigin.getId());

			addFileToOther(fileOriginDto);

			return fileOriginDto;

		} else if (fileOriginDto.getId() != null) {// 上传文件步骤2
			addFileToOther(fileOriginDto);

			FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(fileOriginDto.getId());
			return generateDto(fileOrigin);

		} else {
			throw new CloudStudyException();
		}
	}

	@Override
	public void deleteById(Integer fileOriginId) throws IOException {
		FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(fileOriginId);
		String absolutePath = filePath + fileOrigin.getPath();
		FileUtil.deleteFile(absolutePath);
		fileOriginMapper.deleteByPrimaryKey(fileOriginId);
	}

	@Override
	public void deleteByUserNo(String userNo) throws IOException {
		List<FileOriginDto> FileOriginDtoList = findByUserNo(userNo, true);
		if (FileOriginDtoList != null && !FileOriginDtoList.isEmpty()) {
			List<Integer> primaryKeyList = new ArrayList<Integer>();
			for (FileOriginDto fileOriginDto : FileOriginDtoList) {
				primaryKeyList.add(fileOriginDto.getId());
			}
			deleteByIdList(primaryKeyList);
		}
	}

	@Override
	public void deleteByIdList(List<Integer> fileOriginIdList) throws IOException {
		com.cloudstudy.bo.FileOriginExample fileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria3 = fileOriginExample.createCriteria();
		criteria3.andIdIn(fileOriginIdList);
		List<FileOrigin> FileOriginList = fileOriginMapper.selectByExample(fileOriginExample);
		if (FileOriginList != null && !FileOriginList.isEmpty()) {
			for (FileOrigin fileOrigin : FileOriginList) {
				String absolutePath = filePath + fileOrigin.getPath();
				FileUtil.deleteFile(absolutePath);
			}
		}
		fileOriginMapper.deleteByExample(fileOriginExample);
	}

	@Override
	public FileOriginQueryDto findById(Integer primaryKey) {
		FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(primaryKey);
		FileOriginQueryDto fileOriginQueryDto = generateQueryDto(fileOrigin);
		return fileOriginQueryDto;
	}

	@Override
	public List<FileOriginDto> findByIdList(List<Integer> primaryKeyList) {
		com.cloudstudy.bo.FileOriginExample fileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria3 = fileOriginExample.createCriteria();
		criteria3.andIdIn(primaryKeyList);
		List<FileOrigin> fileOriginList = fileOriginMapper.selectByExample(fileOriginExample);
		return generateDto(fileOriginList);
	}

	@Override
	public PageResultDto<List<FileOriginQueryDto>> find(FileOriginQueryParamDto fileOriginQueryDto) {

		FileOriginExample fileOriginExample = new FileOriginExample();
		Criteria criteria = fileOriginExample.createCriteria();

		String keyword = fileOriginQueryDto.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			fileOriginExample.or()//
					.andNameLike("%" + keyword + "%")//
					.andMemoLike("%" + keyword + "%");//
		}

		ArrayList<String> dateRangementList = fileOriginQueryDto.getDateRangement();
		if (dateRangementList != null && dateRangementList.size() == 2) {
			criteria.andCreateTimeBetween(DateUtil.stringToDateSpecial(dateRangementList.get(0)),
					DateUtil.stringToDateSpecial(dateRangementList.get(1)));
		}

		ArrayList<String> fileSizeRangementList = fileOriginQueryDto.getFileSizeRangement();
		if (dateRangementList != null && dateRangementList.size() == 2) {
			criteria.andSizeBetween(Integer.valueOf(fileSizeRangementList.get(0)),
					Integer.valueOf(fileSizeRangementList.get(1)));
		}

		long total = fileOriginMapper.countByExample(fileOriginExample);

		Integer page = fileOriginQueryDto.getPageDto().getCurrent();
		Integer rows = fileOriginQueryDto.getPageDto().getSize();
		PageHelper.startPage(page, rows);
		List<FileOrigin> fileOriginList = fileOriginMapper.selectByExample(fileOriginExample);

		List<FileOriginQueryDto> fileOriginQueryDtoList = new ArrayList<FileOriginQueryDto>();
		if (fileOriginList == null || fileOriginList.isEmpty()) {
			return new PageResultDto<List<FileOriginQueryDto>>(total, fileOriginQueryDtoList);
		}

		for (FileOrigin fileOrigin : fileOriginList) {
			FileOriginQueryDto fileOriginDto = generateQueryDto(fileOrigin);
			fileOriginQueryDtoList.add(fileOriginDto);
		}

		return new PageResultDto<List<FileOriginQueryDto>>(total, fileOriginQueryDtoList);
	}

	private FileOriginQueryDto generateQueryDto(FileOrigin fileOrigin) {
		if (fileOrigin == null) {
			throw new CloudStudyException();
		}
		FileOriginQueryDto fileOriginQueryDto = new FileOriginQueryDto();
		BeanUtils.copyProperties(fileOrigin, fileOriginQueryDto);

		fileOriginQueryDto.setCourseDto(courseService.findByFileOriginId(fileOriginQueryDto.getId()));
		fileOriginQueryDto.setTaskDto(taskService.findByFileOriginId(fileOriginQueryDto.getId()));
		fileOriginQueryDto.setJobDto(jobService.findByFileOriginId(fileOriginQueryDto.getId()));
		if (fileOriginQueryDto.getCourseDto() != null) {
			fileOriginQueryDto.setUserDto(userService.findTeacherByCourseId(fileOriginQueryDto.getCourseDto().getId()));
		} else if (fileOriginQueryDto.getTaskDto() != null) {
			fileOriginQueryDto.setUserDto(userService.findTeacherByTaskId(fileOriginQueryDto.getTaskDto().getId()));
		} else if (fileOriginQueryDto.getJobDto() != null) {
			fileOriginQueryDto.setUserDto(userService.findStudentByJobId(fileOriginQueryDto.getJobDto().getId()));
		}

		fileOriginQueryDto.setSrc("https://localhost:9680/" + fileOriginQueryDto.getPath());

		String fileNametype = fileOrigin.getType();
		if (fileNametype != null && !fileNametype.isEmpty()) {
			fileNametype = fileNametype.replaceAll("\\.", "").toLowerCase();

			if (FileOriginConstant.imgList.contains(fileNametype)) {
				fileOriginQueryDto.setFileTypeName(1);
			} else if (FileOriginConstant.docList.contains(fileNametype)) {
				fileOriginQueryDto.setFileTypeName(2);
			} else if (FileOriginConstant.videoList.contains(fileNametype)) {
				fileOriginQueryDto.setFileTypeName(3);
			}
		}
		return fileOriginQueryDto;
	}

	private void addFileToOther(FileOriginDto fileOriginDto) throws IOException {

		Integer fileDtoType_course = fileOriginDto.getCourseId();
		Integer fileDtoType_task = fileOriginDto.getTaskId();
		Integer fileDtoType_job = fileOriginDto.getJobId();

		if (fileDtoType_course != null) {
			FileToCourseExample FileToCourseExample = new FileToCourseExample();
			com.cloudstudy.bo.FileToCourseExample.Criteria criteria = FileToCourseExample.createCriteria();
			criteria.andCourseIdEqualTo(fileDtoType_course);
			criteria.andFileIdEqualTo(fileOriginDto.getId());
			List<FileToCourse> fileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
			if (fileToCourseList == null || fileToCourseList.isEmpty()) {
				com.cloudstudy.bo.FileToCourse fileToCourse = new com.cloudstudy.bo.FileToCourse();
				fileToCourse.setCourseId(fileOriginDto.getCourseId());
				fileToCourse.setFileId(fileOriginDto.getId());
				fileToCourseMapper.insert(fileToCourse);
			}
		}

		if (fileDtoType_task != null) {
			FileToTaskExample FileToTaskExample = new FileToTaskExample();
			com.cloudstudy.bo.FileToTaskExample.Criteria criteria = FileToTaskExample.createCriteria();
			criteria.andTaskIdEqualTo(fileDtoType_task);
			criteria.andFileIdEqualTo(fileOriginDto.getId());
			List<FileToTask> fileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
			if (fileToTaskList == null || fileToTaskList.isEmpty()) {
				com.cloudstudy.bo.FileToTask fileToTask = new com.cloudstudy.bo.FileToTask();
				fileToTask.setTaskId(fileOriginDto.getTaskId());
				fileToTask.setFileId(fileOriginDto.getId());
				fileToTaskMapper.insert(fileToTask);
			}
		}

		if (fileDtoType_job != null) {
			FileToJobExample FileToJobExample = new FileToJobExample();
			com.cloudstudy.bo.FileToJobExample.Criteria criteria = FileToJobExample.createCriteria();
			criteria.andJobIdEqualTo(fileDtoType_job);
			criteria.andFileIdEqualTo(fileOriginDto.getId());
			List<FileToJob> fileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
			if (fileToJobList == null || fileToJobList.isEmpty()) {
				com.cloudstudy.bo.FileToJob fileToJob = new com.cloudstudy.bo.FileToJob();
				fileToJob.setJobId(fileOriginDto.getJobId());
				fileToJob.setFileId(fileOriginDto.getId());
				fileToJobMapper.insert(fileToJob);
			}
		}
	}

	private String generateFileOriginMemo(FileOriginDto fileOriginDto) {
		Integer fileDtoType_course_id = fileOriginDto.getCourseId();
		Integer fileDtoType_task_id = fileOriginDto.getTaskId();
		Integer fileDtoType_job_id = fileOriginDto.getJobId();

		String memo = fileOriginDto.getName();
		if (fileDtoType_course_id != null) {
			Course course = courseMapper.selectByPrimaryKey(fileDtoType_course_id);
			String courseName = course.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto userDto = userService.findUserByNo(course.getTeacherNo());
			String teacherName = userDto.getName();
			if (memo == null || !memo.contains(teacherName)) {
				memo += "," + teacherName;
			}
		}

		if (fileDtoType_task_id != null) {
			Task task = taskMapper.selectByPrimaryKey(fileDtoType_task_id);
			String taskTitile = task.getTitle();
			if (memo == null || !memo.contains(taskTitile)) {
				memo += "," + taskTitile;
			}

			Integer courseId = task.getCourseId();
			Course course = courseMapper.selectByPrimaryKey(courseId);
			String courseName = course.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto userDto = userService.findUserByNo(course.getTeacherNo());
			String teacherName = userDto.getName();
			if (memo == null || !memo.contains(teacherName)) {
				memo += "," + teacherName;
			}

		}

		if (fileDtoType_job_id != null) {
			Job job = jobMapper.selectByPrimaryKey(fileDtoType_job_id);
			String jobTitile = job.getTitle();
			if (memo == null || !memo.contains(jobTitile)) {
				memo += "," + jobTitile;
			}

			Integer gradeId = job.getGradeId();
			Grade grade = gradeMapper.selectByPrimaryKey(gradeId);
			String studentNo = grade.getStudentNo();
			UserDto studentUserDto = userService.findUserByNo(studentNo);
			String studentName = studentUserDto.getName();
			if (memo == null || !memo.contains(studentName)) {
				memo += "," + studentName;
			}

			Integer courseId = grade.getCourseId();
			Course course = courseMapper.selectByPrimaryKey(courseId);
			String courseName = course.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto teacherUserDto = userService.findUserByNo(course.getTeacherNo());
			String teacherName = teacherUserDto.getName();
			if (memo == null || !memo.contains(teacherName)) {
				memo += "," + teacherName;
			}

		}

		return memo;
	}

	@Override
	public List<FileOriginDto> findByJobId(Integer jobId, boolean isUpRecursion) {

		HashSet<Integer> FileOriginIdSet = new HashSet<Integer>();
		com.cloudstudy.bo.FileToJobExample fileToJobExample = new com.cloudstudy.bo.FileToJobExample();
		com.cloudstudy.bo.FileToJobExample.Criteria criteria1 = fileToJobExample.createCriteria();
		criteria1.andJobIdEqualTo(jobId);
		List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(fileToJobExample);
		if (FileToJobList != null) {
			for (FileToJob fileToJob : FileToJobList) {
				FileOriginIdSet.add(fileToJob.getFileId());
			}
		}

		if (isUpRecursion) {
			TaskDto taskDto = taskService.findByJobId(jobId);

			if (taskDto != null) {
				List<Integer> taskIdList = new ArrayList<Integer>();
				List<Integer> courseIdList = new ArrayList<Integer>();

				taskIdList.add(taskDto.getId());
				courseIdList.add(taskDto.getCourseId());

				com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
				com.cloudstudy.bo.FileToTaskExample.Criteria criteria2 = FileToTaskExample.createCriteria();
				criteria2.andTaskIdIn(taskIdList);
				List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
				if (FileToTaskList != null && !FileToTaskList.isEmpty()) {
					for (FileToTask fileToTask : FileToTaskList) {
						FileOriginIdSet.add(fileToTask.getFileId());
					}
				}

				com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
				com.cloudstudy.bo.FileToCourseExample.Criteria criteria3 = FileToCourseExample.createCriteria();
				criteria3.andCourseIdIn(courseIdList);
				List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
				if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
					for (FileToCourse fileToCourse : FileToCourseList) {
						FileOriginIdSet.add(fileToCourse.getFileId());
					}
				}
			}
		}

		com.cloudstudy.bo.FileOriginExample FileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria4 = FileOriginExample.createCriteria();
		criteria4.andIdIn(new ArrayList<Integer>(FileOriginIdSet));
		List<FileOrigin> FileOriginList = fileOriginMapper.selectByExample(FileOriginExample);

		List<FileOriginDto> FileOriginDtoList = generateDto(FileOriginList);
		return FileOriginDtoList;
	}

	@Override
	public List<FileOriginDto> findByTaskId(Integer taskId, boolean isUpRecursion, boolean isDownRecursion) {

		HashSet<Integer> FileOriginIdSet = new HashSet<Integer>();
		com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
		com.cloudstudy.bo.FileToTaskExample.Criteria criteria1 = FileToTaskExample.createCriteria();
		criteria1.andTaskIdEqualTo(taskId);
		List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
		if (FileToTaskList != null && !FileToTaskList.isEmpty()) {
			for (FileToTask fileToTask : FileToTaskList) {
				FileOriginIdSet.add(fileToTask.getFileId());
			}
		}

		if (isUpRecursion) {
			CourseDto courseDto = courseService.findByTaskId(taskId);

			if (courseDto != null) {
				List<Integer> courseIdList = new ArrayList<Integer>();
				courseIdList.add(courseDto.getId());

				com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
				com.cloudstudy.bo.FileToCourseExample.Criteria criteria3 = FileToCourseExample.createCriteria();
				criteria3.andCourseIdIn(courseIdList);
				List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
				if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
					for (FileToCourse fileToCourse : FileToCourseList) {
						FileOriginIdSet.add(fileToCourse.getFileId());
					}
				}
			}
		}

		if (isDownRecursion) {
			List<JobDto> jobDtoList = jobService.findByTaskId(taskId);

			if (jobDtoList != null && !jobDtoList.isEmpty()) {
				List<Integer> jobIdList = new ArrayList<Integer>();
				for (JobDto jobDto : jobDtoList) {
					jobIdList.add(jobDto.getId());
				}

				com.cloudstudy.bo.FileToJobExample FileToJobExample = new com.cloudstudy.bo.FileToJobExample();
				com.cloudstudy.bo.FileToJobExample.Criteria criteria3 = FileToJobExample.createCriteria();
				criteria3.andJobIdIn(jobIdList);
				List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
				if (FileToJobList != null && !FileToJobList.isEmpty()) {
					for (FileToJob fileToJob : FileToJobList) {
						FileOriginIdSet.add(fileToJob.getFileId());
					}
				}
			}
		}

		com.cloudstudy.bo.FileOriginExample FileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria4 = FileOriginExample.createCriteria();
		criteria4.andIdIn(new ArrayList<Integer>(FileOriginIdSet));
		List<FileOrigin> FileOriginList = fileOriginMapper.selectByExample(FileOriginExample);

		List<FileOriginDto> FileOriginDtoList = generateDto(FileOriginList);
		return FileOriginDtoList;
	}

	@Override
	public List<FileOriginDto> findByUserNo(String userNo, boolean isDownRecursion) {
		HashSet<FileOriginDto> FileOriginDtoSet = new HashSet<FileOriginDto>();
		HashSet<Integer> CourseSet = new HashSet<Integer>();

		com.cloudstudy.bo.CourseExample CourseExample = new com.cloudstudy.bo.CourseExample();
		com.cloudstudy.bo.CourseExample.Criteria criteria1 = CourseExample.createCriteria();
		criteria1.andTeacherNoEqualTo(userNo);
		List<Course> CourseList = courseMapper.selectByExample(CourseExample);
		if (CourseList != null && !CourseList.isEmpty()) {
			for (Course course : CourseList) {
				CourseSet.add(course.getId());
			}
		}

		com.cloudstudy.bo.GradeExample GradeExample = new com.cloudstudy.bo.GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria2 = GradeExample.createCriteria();
		criteria2.andStudentNoEqualTo(userNo);
		List<Grade> GradeList = gradeMapper.selectByExample(GradeExample);
		if (GradeList != null && !GradeList.isEmpty()) {
			for (Grade grade : GradeList) {
				CourseSet.add(grade.getCourseId());
			}
		}

		if (CourseSet != null && !CourseSet.isEmpty()) {
			for (Integer courseId : CourseSet) {
				List<FileOriginDto> FileOriginDtoList = findByCourseId(courseId, isDownRecursion);
				if (FileOriginDtoList != null && !FileOriginDtoList.isEmpty()) {
					for (FileOriginDto fileOriginDto : FileOriginDtoList) {
						FileOriginDtoSet.add(fileOriginDto);
					}
				}
			}
		}
		return new ArrayList<FileOriginDto>(FileOriginDtoSet);
	}

	@Override
	public List<FileOriginDto> findByCourseId(Integer courseId, boolean isDownRecursion) {
		HashSet<Integer> FileOriginIdSet = new HashSet<Integer>();

		com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
		com.cloudstudy.bo.FileToCourseExample.Criteria criteria1 = FileToCourseExample.createCriteria();
		criteria1.andCourseIdEqualTo(courseId);
		List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
		if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
			for (FileToCourse fileToCourse : FileToCourseList) {
				FileOriginIdSet.add(fileToCourse.getFileId());
			}
		}

		if (isDownRecursion) {
			List<TaskDto> taskDtoList = taskService.findByCourseId(courseId);
			if (taskDtoList != null && !taskDtoList.isEmpty()) {
				List<Integer> taskIdList = new ArrayList<Integer>();
				for (TaskDto taskDto : taskDtoList) {
					taskIdList.add(taskDto.getId());
				}

				com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
				com.cloudstudy.bo.FileToTaskExample.Criteria criteria3 = FileToTaskExample.createCriteria();
				criteria3.andTaskIdIn(taskIdList);
				List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
				if (FileToTaskList != null && !FileToTaskList.isEmpty()) {
					for (FileToTask fileToTask : FileToTaskList) {
						FileOriginIdSet.add(fileToTask.getFileId());
					}
				}
			}

			List<JobDto> jobDtoList = jobService.findByCourseId(courseId);
			if (jobDtoList != null && !jobDtoList.isEmpty()) {
				List<Integer> jobIdList = new ArrayList<Integer>();
				for (JobDto jobDto : jobDtoList) {
					jobIdList.add(jobDto.getId());
				}

				com.cloudstudy.bo.FileToJobExample FileToJobExample = new com.cloudstudy.bo.FileToJobExample();
				com.cloudstudy.bo.FileToJobExample.Criteria criteria3 = FileToJobExample.createCriteria();
				criteria3.andJobIdIn(jobIdList);
				List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
				if (FileToJobList != null && !FileToJobList.isEmpty()) {
					for (FileToJob fileToJob : FileToJobList) {
						FileOriginIdSet.add(fileToJob.getFileId());
					}
				}
			}
		}

		com.cloudstudy.bo.FileOriginExample fileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria4 = fileOriginExample.createCriteria();
		criteria4.andIdIn(new ArrayList<Integer>(FileOriginIdSet));
		List<FileOrigin> FileOriginList = fileOriginMapper.selectByExample(fileOriginExample);

		List<FileOriginDto> FileOriginDtoList = generateDto(FileOriginList);
		return FileOriginDtoList;

	}

	private List<FileOriginDto> generateDto(List<FileOrigin> fileOriginList) {
		if (fileOriginList == null || fileOriginList.isEmpty()) {
			return new ArrayList<FileOriginDto>();
		}
		List<FileOriginDto> FileOriginDtoList = new ArrayList<FileOriginDto>();
		for (FileOrigin fileOrigin : fileOriginList) {
			FileOriginDto fileOriginDto = generateDto(fileOrigin);
			FileOriginDtoList.add(fileOriginDto);
		}
		return FileOriginDtoList;
	}

	private FileOriginDto generateDto(FileOrigin fileOrigin) {
		if (fileOrigin == null) {
			throw new CloudStudyException();
		}
		FileOriginDto fileOriginDto = new FileOriginDto();
		BeanUtils.copyProperties(fileOrigin, fileOriginDto);
		return fileOriginDto;
	}

	private FileOrigin generate(FileOriginDto fileOriginDto) {
		if (fileOriginDto == null) {
			throw new CloudStudyException();
		}
		FileOrigin fileOrigin = new FileOrigin();
		BeanUtils.copyProperties(fileOriginDto, fileOrigin);
		return fileOrigin;
	}

}
