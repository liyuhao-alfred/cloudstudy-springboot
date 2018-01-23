package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cloudstudy.bo.Courserelstudent;
import com.cloudstudy.bo.Courserelteacher;
import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.Studyfile;
import com.cloudstudy.bo.Studyfilerelcourse;
import com.cloudstudy.bo.Studyfilereljob;
import com.cloudstudy.bo.Studyfilereltask;
import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.example.CourserelstudentExample;
import com.cloudstudy.bo.example.StudyfileExample;
import com.cloudstudy.dto.JobDto;
import com.cloudstudy.dto.StudyfileDto;
import com.cloudstudy.dto.StudyfileQueryParamDto;
import com.cloudstudy.dto.TaskDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.StudyfileMapper;
import com.cloudstudy.mapper.StudyfilerelcourseMapper;
import com.cloudstudy.mapper.StudyfilereljobMapper;
import com.cloudstudy.mapper.StudyfilereltaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.JobService;
import com.cloudstudy.service.StudyfileService;
import com.cloudstudy.service.TaskService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.FileUtil;
import com.github.pagehelper.PageHelper;

@Service
public class StudyfileServiceImpl implements StudyfileService {

	@Autowired
	private UserService userService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private CourserelteacherMapper courserelteacherMapper;
	@Autowired
	private CourserelstudentMapper courserelstudentMapper;
	@Autowired
	private StudyfileMapper fileMapper;
	@Autowired
	private StudyfilerelcourseMapper filerelcourseMapper;
	@Autowired
	private StudyfilereltaskMapper filereltaskMapper;
	@Autowired
	private StudyfilereljobMapper filereljobMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private JobService jobService;
	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public StudyfileDto add(StudyfileDto fileDto) throws IOException {
		fileDto = generateFileDtoAndSaveFile(fileDto);

		com.cloudstudy.bo.Studyfile file = new Studyfile();
		BeanUtils.copyProperties(fileDto, file);
		fileMapper.insert(file);
		fileDto.setId(file.getId());

		if (fileDto.getCourseId() != null) {
			com.cloudstudy.bo.Studyfilerelcourse filerelcourse = new com.cloudstudy.bo.Studyfilerelcourse(
					fileDto.getId(), fileDto.getCourseId());
			filerelcourseMapper.insert(filerelcourse);
		}

		if (fileDto.getTaskId() != null) {
			com.cloudstudy.bo.Studyfilereltask filereltask = new com.cloudstudy.bo.Studyfilereltask(fileDto.getId(),
					fileDto.getTaskId());
			filereltaskMapper.insert(filereltask);
		}

		if (fileDto.getJobId() != null) {
			com.cloudstudy.bo.Studyfilereljob filereljob = new com.cloudstudy.bo.Studyfilereljob(fileDto.getId(),
					fileDto.getJobId());
			filereljobMapper.insert(filereljob);
		}
		return fileDto;
	}

	@Override
	public void deleteById(Integer id) throws IOException {
		Studyfile studyfile = fileMapper.selectByPrimaryKey(id);

		fileMapper.deleteByPrimaryKey(id);

		com.cloudstudy.bo.example.StudyfilerelcourseExample FilerelcourseExample = new com.cloudstudy.bo.example.StudyfilerelcourseExample();
		com.cloudstudy.bo.example.StudyfilerelcourseExample.Criteria criteria1 = FilerelcourseExample.createCriteria();
		criteria1.andFileIdEqualTo(id);
		filerelcourseMapper.deleteByExample(FilerelcourseExample);

		com.cloudstudy.bo.example.StudyfilereltaskExample FilereltaskExample = new com.cloudstudy.bo.example.StudyfilereltaskExample();
		com.cloudstudy.bo.example.StudyfilereltaskExample.Criteria criteria2 = FilereltaskExample.createCriteria();
		criteria2.andFileIdEqualTo(id);
		filereltaskMapper.deleteByExample(FilereltaskExample);

		com.cloudstudy.bo.example.StudyfilereljobExample FilereljobExample = new com.cloudstudy.bo.example.StudyfilereljobExample();
		com.cloudstudy.bo.example.StudyfilereljobExample.Criteria criteria3 = FilereljobExample.createCriteria();
		criteria3.andFileIdEqualTo(id);
		filereljobMapper.deleteByExample(FilereljobExample);

		String absolutePath = filePath + studyfile.getPath();
		FileUtil.deleteFile(absolutePath);

	}

	@Override
	public void deleteByCourserelteacherId(Integer id) throws IOException {

		com.cloudstudy.bo.example.StudyfilerelcourseExample FilerelcourseExample = new com.cloudstudy.bo.example.StudyfilerelcourseExample();
		com.cloudstudy.bo.example.StudyfilerelcourseExample.Criteria criteria1 = FilerelcourseExample.createCriteria();
		criteria1.andCourseIdEqualTo(id);

		List<Studyfilerelcourse> list = filerelcourseMapper.selectByExample(FilerelcourseExample);
		if (list == null || list.isEmpty()) {
			return;
		}

		for (Studyfilerelcourse studyfilerelcourse : list) {
			deleteById(studyfilerelcourse.getFileId());
		}

		List<TaskDto> taskDtoList = taskService.findByCourseId(id);
		if (taskDtoList != null && !taskDtoList.isEmpty()) {
			for (TaskDto taskDto : taskDtoList) {
				deleteByTaskId(taskDto.getId());

				List<JobDto> jobDtoList = jobService.findByTaskId(id);
				if (jobDtoList != null && !jobDtoList.isEmpty()) {
					for (JobDto jobDto : jobDtoList) {
						deleteByJobId(jobDto.getId());
					}

				}

			}
		}

	}

	@Override
	public void deleteByTaskId(Integer id) throws IOException {
		com.cloudstudy.bo.example.StudyfilereltaskExample FilereltaskExample = new com.cloudstudy.bo.example.StudyfilereltaskExample();
		com.cloudstudy.bo.example.StudyfilereltaskExample.Criteria criteria2 = FilereltaskExample.createCriteria();
		criteria2.andTaskIdEqualTo(id);

		List<Studyfilereltask> list = filereltaskMapper.selectByExample(FilereltaskExample);
		if (list == null || list.isEmpty()) {
			return;
		}

		for (Studyfilereltask studyfilereltask : list) {
			deleteById(studyfilereltask.getFileId());
		}

	}

	@Override
	public void deleteByJobId(Integer id) throws IOException {
		com.cloudstudy.bo.example.StudyfilereljobExample FilereljobExample = new com.cloudstudy.bo.example.StudyfilereljobExample();
		com.cloudstudy.bo.example.StudyfilereljobExample.Criteria criteria3 = FilereljobExample.createCriteria();
		criteria3.andJobIdEqualTo(id);

		List<Studyfilereljob> list = filereljobMapper.selectByExample(FilereljobExample);
		if (list == null || list.isEmpty()) {
			return;
		}

		for (Studyfilereljob studyfilereljob : list) {
			deleteById(studyfilereljob.getFileId());
		}
	}

	@Override
	public StudyfileDto findById(Integer id) {

		Studyfile studyFile = fileMapper.selectByPrimaryKey(id);
		StudyfileDto studyFileDto = new StudyfileDto();
		BeanUtils.copyProperties(studyFile, studyFileDto);

		return studyFileDto;

	}

	@Override
	public List<StudyfileDto> find(StudyfileQueryParamDto fileQueryParamDto) {

		StudyfileExample fileExample = new StudyfileExample();
		com.cloudstudy.bo.example.StudyfileExample.Criteria criteria = fileExample.createCriteria();

		String keyword = fileQueryParamDto.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			criteria.andMemoLike("%" + keyword + "%");
		}

		if (fileQueryParamDto.getFromFileSize() != null) {
			criteria.andSizeGreaterThanOrEqualTo(fileQueryParamDto.getFromFileSize());
		}
		if (fileQueryParamDto.getToFileSize() != null) {
			criteria.andSizeLessThanOrEqualTo(fileQueryParamDto.getToFileSize());
		}

		if (fileQueryParamDto.getFileType() != null) {
			criteria.andTypeEqualTo(fileQueryParamDto.getFileType());
		}

		Integer page = fileQueryParamDto.getPageDto().getPage();
		Integer rows = fileQueryParamDto.getPageDto().getRows();
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}

		List<Studyfile> fileList = fileMapper.selectByExample(fileExample);
		if (fileList == null || fileList.isEmpty()) {
			return null;
		}

		List<StudyfileDto> fileDtoList = new ArrayList<StudyfileDto>();
		for (Studyfile file : fileList) {
			StudyfileDto fileDto = new StudyfileDto();
			BeanUtils.copyProperties(file, fileDto);
			fileDtoList.add(fileDto);
		}
		return fileDtoList;
	}

	public StudyfileDto generateFileDtoAndSaveFile(StudyfileDto studyFileDto) throws IOException {
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

		String absoluteStudyFilePath = filePath + studyFilePath;

		File saveFile = FileUtil.createFile(absoluteStudyFilePath);
		FileCopyUtils.copy(recFile, saveFile);

		studyFileDto.setCreateTime(new Date());
		studyFileDto.setLastModifyTime(new Date());
		studyFileDto.setPath(studyFilePath);
		studyFileDto.setSize(fileSize);
		studyFileDto.setType(fileExtension);
		studyFileDto.setName(fileName);
		studyFileDto.setMemo(generateStudyFileMemo(studyFileDto));

		return studyFileDto;
	}

	private String generateStudyFileMemo(StudyfileDto studyFileDto) {
		Integer fileDtoType_course_id = studyFileDto.getCourseId();
		Integer fileDtoType_task_id = studyFileDto.getTaskId();
		Integer fileDtoType_job_id = studyFileDto.getJobId();

		String memo = studyFileDto.getName();
		if (fileDtoType_course_id != null) {
			Courserelteacher courserelteacher = courserelteacherMapper.selectByPrimaryKey(fileDtoType_course_id);
			String courseName = courserelteacher.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto userDto = userService.findUserByNo(courserelteacher.getTeacherNo());
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

			Integer courserelteacherId = task.getCourserelteacherId();
			Courserelteacher courserelteacher = courserelteacherMapper.selectByPrimaryKey(courserelteacherId);
			String courseName = courserelteacher.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto userDto = userService.findUserByNo(courserelteacher.getTeacherNo());
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

			Integer courserelstudentId = job.getCourserelstudentId();
			Courserelstudent courserelstudent = courserelstudentMapper.selectByPrimaryKey(courserelstudentId);
			String studentNo = courserelstudent.getStudentNo();
			UserDto studentUserDto = userService.findUserByNo(studentNo);
			String studentName = studentUserDto.getName();
			if (memo == null || !memo.contains(studentName)) {
				memo += "," + studentName;
			}

			Integer courserelteacherId = courserelstudent.getCourserelteacherId();
			Courserelteacher courserelteacher = courserelteacherMapper.selectByPrimaryKey(courserelteacherId);
			String courseName = courserelteacher.getName();
			if (memo == null || !memo.contains(courseName)) {
				memo += "," + courseName;
			}

			UserDto teacherUserDto = userService.findUserByNo(courserelteacher.getTeacherNo());
			String teacherName = teacherUserDto.getName();
			if (memo == null || !memo.contains(teacherName)) {
				memo += "," + teacherName;
			}

		}

		return memo;
	}

}
