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

import com.cloudstudy.bo.Grade;
import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToJob;
import com.cloudstudy.bo.FileToTask;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.FileOriginExample;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.FileOriginQueryDto;
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
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
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
	public FileOriginDto add(FileOriginDto fileDto) throws IOException {
		fileDto = generateFileDtoAndSaveFile(fileDto);

		com.cloudstudy.bo.FileOrigin file = new FileOrigin();
		BeanUtils.copyProperties(fileDto, file);
		fileOriginMapper.insert(file);
		fileDto.setId(file.getId());

		if (fileDto.getCourseId() != null) {
			com.cloudstudy.bo.FileToCourse fileToCourse = new com.cloudstudy.bo.FileToCourse(fileDto.getId(),
					fileDto.getCourseId());
			fileToCourseMapper.insert(fileToCourse);
		}

		if (fileDto.getTaskId() != null) {
			com.cloudstudy.bo.FileToTask fileToTask = new com.cloudstudy.bo.FileToTask(fileDto.getId(),
					fileDto.getTaskId());
			fileToTaskMapper.insert(fileToTask);
		}

		if (fileDto.getJobId() != null) {
			com.cloudstudy.bo.FileToJob fileToJob = new com.cloudstudy.bo.FileToJob(fileDto.getId(),
					fileDto.getJobId());
			fileToJobMapper.insert(fileToJob);
		}
		return fileDto;
	}

	@Override
	public void deleteById(Integer id) throws IOException {
		FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(id);
		String absolutePath = filePath + fileOrigin.getPath();
		FileUtil.deleteFile(absolutePath);
		fileOriginMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIdList(List<Integer> idList) throws IOException {

		com.cloudstudy.bo.FileOriginExample FileOriginExample = new com.cloudstudy.bo.FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria3 = FileOriginExample.createCriteria();
		criteria3.andIdIn(idList);
		List<FileOrigin> FileOriginList = fileOriginMapper.selectByExample(FileOriginExample);
		if (FileOriginList != null) {
			for (FileOrigin fileOrigin : FileOriginList) {
				String absolutePath = filePath + fileOrigin.getPath();
				FileUtil.deleteFile(absolutePath);
			}
		}

		fileOriginMapper.deleteByExample(FileOriginExample);

	}

	@Override
	public FileOriginDto findById(Integer id) {

		FileOrigin studyFile = fileOriginMapper.selectByPrimaryKey(id);
		FileOriginDto studyFileDto = new FileOriginDto();
		BeanUtils.copyProperties(studyFile, studyFileDto);

		return studyFileDto;

	}

	@Override
	public List<FileOriginDto> find(FileOriginQueryDto fileQueryDto) {

		FileOriginExample fileExample = new FileOriginExample();
		com.cloudstudy.bo.FileOriginExample.Criteria criteria = fileExample.createCriteria();

		String keyword = fileQueryDto.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			criteria.andMemoLike("%" + keyword + "%");
		}

		if (fileQueryDto.getFromFileSize() != null) {
			criteria.andSizeGreaterThanOrEqualTo(fileQueryDto.getFromFileSize());
		}
		if (fileQueryDto.getToFileSize() != null) {
			criteria.andSizeLessThanOrEqualTo(fileQueryDto.getToFileSize());
		}

		if (fileQueryDto.getFileType() != null) {
			criteria.andTypeEqualTo(fileQueryDto.getFileType());
		}

		Integer page = fileQueryDto.getPageDto().getPage();
		Integer rows = fileQueryDto.getPageDto().getRows();
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}

		List<FileOrigin> fileList = fileOriginMapper.selectByExample(fileExample);
		if (fileList == null || fileList.isEmpty()) {
			return null;
		}

		List<FileOriginDto> fileDtoList = new ArrayList<FileOriginDto>();
		for (FileOrigin file : fileList) {
			FileOriginDto fileDto = new FileOriginDto();
			BeanUtils.copyProperties(file, fileDto);
			fileDtoList.add(fileDto);
		}
		return fileDtoList;
	}

	public FileOriginDto generateFileDtoAndSaveFile(FileOriginDto studyFileDto) throws IOException {
		File recFile = studyFileDto.getFile();
		String fileName = System.currentTimeMillis() + "_" + recFile.getName();
		String fileExtension = FileUtil.getExtensionName(fileName);// 没有点的
		Integer fileSize = (int) recFile.length();

		Integer fileDtoType_course = studyFileDto.getCourseId();
		Integer fileDtoType_task = studyFileDto.getTaskId();
		Integer fileDtoType_job = studyFileDto.getJobId();

		String studyFilePath = null;
		if (fileDtoType_course != null) {
			studyFilePath = File.separator + "course" + File.separator + fileName;
		} else if (fileDtoType_task != null) {
			studyFilePath = File.separator + "task" + File.separator + fileName;
		} else if (fileDtoType_job != null) {
			studyFilePath = File.separator + "job" + File.separator + fileName;
		} else {
			studyFilePath = File.separator + "default" + File.separator + fileName;
		}

		String absoluteFileOriginPath = filePath + studyFilePath;

		File saveFile = FileUtil.createFile(absoluteFileOriginPath);
		FileCopyUtils.copy(recFile, saveFile);

		studyFileDto.setCreateTime(new Date());
		studyFileDto.setLastModifyTime(new Date());
		studyFileDto.setPath(studyFilePath);
		studyFileDto.setSize(fileSize);
		studyFileDto.setType(fileExtension);
		studyFileDto.setName(fileName);
		studyFileDto.setMemo(generateFileOriginMemo(studyFileDto));

		return studyFileDto;
	}

	private String generateFileOriginMemo(FileOriginDto studyFileDto) {
		Integer fileDtoType_course_id = studyFileDto.getCourseId();
		Integer fileDtoType_task_id = studyFileDto.getTaskId();
		Integer fileDtoType_job_id = studyFileDto.getJobId();

		String memo = studyFileDto.getName();
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
		com.cloudstudy.bo.FileToJobExample FileToJobExample = new com.cloudstudy.bo.FileToJobExample();
		com.cloudstudy.bo.FileToJobExample.Criteria criteria1 = FileToJobExample.createCriteria();
		criteria1.andJobIdEqualTo(jobId);
		List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
		if (FileToJobList != null) {
			for (FileToJob fileToJob : FileToJobList) {
				FileOriginIdSet.add(fileToJob.getFileId());
			}
		}

		if (isUpRecursion) {
			List<TaskDto> taskDtoList = taskService.findByJobId(jobId);

			if (taskDtoList != null) {
				List<Integer> taskIdList = new ArrayList<Integer>();
				List<Integer> courseIdList = new ArrayList<Integer>();

				for (TaskDto taskDto : taskDtoList) {
					taskIdList.add(taskDto.getId());
					courseIdList.add(taskDto.getCourseId());
				}

				com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
				com.cloudstudy.bo.FileToTaskExample.Criteria criteria2 = FileToTaskExample.createCriteria();
				criteria2.andTaskIdIn(taskIdList);
				List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
				if (FileToTaskList != null) {
					for (FileToTask fileToTask : FileToTaskList) {
						FileOriginIdSet.add(fileToTask.getFileId());
					}
				}

				com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
				com.cloudstudy.bo.FileToCourseExample.Criteria criteria3 = FileToCourseExample.createCriteria();
				criteria3.andCourseIdIn(courseIdList);
				List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
				if (FileToCourseList != null) {
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

		List<FileOriginDto> FileOriginDtoList = generate(FileOriginList);
		return FileOriginDtoList;
	}

	private List<FileOriginDto> generate(List<FileOrigin> FileOriginList) {
		List<FileOriginDto> FileOriginDtoList = new ArrayList<FileOriginDto>();
		if (FileOriginList != null) {
			for (FileOrigin fileOrigin : FileOriginList) {
				FileOriginDto FileOriginDto = generate(fileOrigin);
				FileOriginDtoList.add(FileOriginDto);
			}
		}
		return FileOriginDtoList;
	}

	private FileOriginDto generate(FileOrigin fileOrigin) {
		if (fileOrigin == null) {
			throw new CloudStudyException();
		}

		FileOriginDto FileOriginDto = new FileOriginDto();
		BeanUtils.copyProperties(fileOrigin, FileOriginDto);
		return FileOriginDto;
	}

	@Override
	public List<FileOriginDto> findByTaskId(Integer taskId, boolean isUpRecursion, boolean isDownRecursion) {

		HashSet<Integer> FileOriginIdSet = new HashSet<Integer>();
		com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
		com.cloudstudy.bo.FileToTaskExample.Criteria criteria1 = FileToTaskExample.createCriteria();
		criteria1.andTaskIdEqualTo(taskId);
		List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
		if (FileToTaskList != null) {
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
				if (FileToCourseList != null) {
					for (FileToCourse fileToCourse : FileToCourseList) {
						FileOriginIdSet.add(fileToCourse.getFileId());
					}
				}
			}
		}

		if (isDownRecursion) {
			List<JobDto> jobDtoList = jobService.findByTaskId(taskId);

			if (jobDtoList != null) {
				List<Integer> jobIdList = new ArrayList<Integer>();
				for (JobDto jobDto : jobDtoList) {
					jobIdList.add(jobDto.getId());
				}

				com.cloudstudy.bo.FileToJobExample FileToJobExample = new com.cloudstudy.bo.FileToJobExample();
				com.cloudstudy.bo.FileToJobExample.Criteria criteria3 = FileToJobExample.createCriteria();
				criteria3.andJobIdIn(jobIdList);
				List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
				if (FileToJobList != null) {
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

		List<FileOriginDto> FileOriginDtoList = generate(FileOriginList);
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
		if (CourseList != null) {
			for (Course course : CourseList) {
				CourseSet.add(course.getId());
			}
		}

		com.cloudstudy.bo.GradeExample GradeExample = new com.cloudstudy.bo.GradeExample();
		com.cloudstudy.bo.GradeExample.Criteria criteria2 = GradeExample.createCriteria();
		criteria2.andStudentNoEqualTo(userNo);
		List<Grade> GradeList = gradeMapper.selectByExample(GradeExample);
		if (GradeList != null) {
			for (Grade grade : GradeList) {
				CourseSet.add(grade.getCourseId());
			}
		}

		if (CourseSet != null) {
			for (Integer courseId : CourseSet) {
				List<FileOriginDto> FileOriginDtoList = findByCourseId(courseId, isDownRecursion);
				if (FileOriginDtoList != null) {
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
		if (FileToCourseList != null) {
			for (FileToCourse fileToCourse : FileToCourseList) {
				FileOriginIdSet.add(fileToCourse.getFileId());
			}
		}

		if (isDownRecursion) {
			List<TaskDto> taskDtoList = taskService.findByCourseId(courseId);
			if (taskDtoList != null) {
				List<Integer> taskIdList = new ArrayList<Integer>();
				for (TaskDto taskDto : taskDtoList) {
					taskIdList.add(taskDto.getId());
				}

				com.cloudstudy.bo.FileToTaskExample FileToTaskExample = new com.cloudstudy.bo.FileToTaskExample();
				com.cloudstudy.bo.FileToTaskExample.Criteria criteria3 = FileToTaskExample.createCriteria();
				criteria3.andTaskIdIn(taskIdList);
				List<FileToTask> FileToTaskList = fileToTaskMapper.selectByExample(FileToTaskExample);
				if (FileToTaskList != null) {
					for (FileToTask fileToTask : FileToTaskList) {
						FileOriginIdSet.add(fileToTask.getFileId());
					}
				}
			}

			List<JobDto> jobDtoList = jobService.findByCourseId(courseId);
			if (jobDtoList != null) {
				List<Integer> jobIdList = new ArrayList<Integer>();
				for (JobDto jobDto : jobDtoList) {
					jobIdList.add(jobDto.getId());
				}

				com.cloudstudy.bo.FileToJobExample FileToJobExample = new com.cloudstudy.bo.FileToJobExample();
				com.cloudstudy.bo.FileToJobExample.Criteria criteria3 = FileToJobExample.createCriteria();
				criteria3.andJobIdIn(jobIdList);
				List<FileToJob> FileToJobList = fileToJobMapper.selectByExample(FileToJobExample);
				if (FileToJobList != null) {
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

		List<FileOriginDto> FileOriginDtoList = generate(FileOriginList);
		return FileOriginDtoList;

	}

}
