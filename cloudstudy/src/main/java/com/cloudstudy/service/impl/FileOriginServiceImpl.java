package com.cloudstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.iterators.ArrayListIterator;
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
import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToCourseExample;
import com.cloudstudy.bo.FileToHomework;
import com.cloudstudy.bo.FileToHomeworkExample;
import com.cloudstudy.bo.Homework;
import com.cloudstudy.bo.HomeworkExample;
import com.cloudstudy.bo.User;
import com.cloudstudy.bo.UserExample;
import com.cloudstudy.bo.FileOriginExample;
import com.cloudstudy.bo.FileOriginExample.Criteria;
import com.cloudstudy.constant.FileOriginConstant;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.CourseDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.HomeworkDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.UserDto;
import com.cloudstudy.exception.CloudStudyException;
import com.cloudstudy.mapper.GradeMapper;
import com.cloudstudy.mapper.CourseMapper;
import com.cloudstudy.mapper.FileOriginMapper;
import com.cloudstudy.mapper.FileToCourseMapper;
import com.cloudstudy.mapper.FileToHomeworkMapper;
import com.cloudstudy.mapper.HomeworkMapper;
import com.cloudstudy.mapper.UserMapper;
import com.cloudstudy.service.CourseService;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.service.HomeworkService;
import com.cloudstudy.service.UserService;
import com.cloudstudy.util.DateUtil;
import com.cloudstudy.util.FileUtil;
import com.github.pagehelper.PageHelper;

@Service
@SuppressWarnings("unused")
public class FileOriginServiceImpl implements FileOriginService {

	@Autowired
	private UserService userService;
	@Autowired
	private HomeworkMapper homeworkMapper;
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
	private FileToHomeworkMapper fileToHomeworkMapper;
	@Autowired
	private HomeworkMapper HomeworkMapper;
	@Autowired
	private HomeworkService HomeworkService;
	@Autowired
	private CourseService courseService;
	@Value("${web.upload-path}")
	private String filePath;

	@Override
	public FileOriginQueryDto add(MultipartFile multipartFile, Integer courseId, Integer homeworkId, String uploaderNo)
			throws IOException {
		if (multipartFile == null || multipartFile.getSize() == 0) {
			return null;
		}

		FileOrigin fileOrigin = generate(multipartFile, courseId, homeworkId, uploaderNo);
		if (fileOrigin == null) {
			return null;
		}
		fileOriginMapper.insert(fileOrigin);

		FileOriginQueryDto FileOriginQueryDto = generateQueryDto(fileOrigin);
		FileOriginQueryDto.setCourseId(courseId);
		FileOriginQueryDto.setHomeworkId(homeworkId);
		FileOriginQueryDto.setUploaderno(uploaderNo);
		addFileToOther(FileOriginQueryDto);

		return FileOriginQueryDto;
	}

	@Override
	public void deleteById(Integer fileOriginId) throws IOException {
		FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(fileOriginId);
		String absolutePath = filePath + fileOrigin.getPath();
		FileUtil.deleteFile(absolutePath);
		fileOriginMapper.deleteByPrimaryKey(fileOriginId);
	}

	@Override
	public FileOriginQueryDto findById(Integer primaryKey) {
		FileOrigin fileOrigin = fileOriginMapper.selectByPrimaryKey(primaryKey);
		FileOriginQueryDto fileOriginQueryDto = generateQueryDto(fileOrigin);
		return fileOriginQueryDto;
	}

	@Override
	public PageResultDto<List<FileOriginQueryDto>> find(FileOriginQueryParamDto fileOriginQueryDto) {

		FileOriginExample fileOriginExample = new FileOriginExample();
		Criteria fileOriginExampleCriteria = fileOriginExample.createCriteria();

		String keyword = fileOriginQueryDto.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			fileOriginExampleCriteria.andMemoLike("%" + keyword + "%");//
		}

		HashSet<Integer> FileOriginIdSet = new HashSet<Integer>();
		ArrayList<Integer> courseIdList = fileOriginQueryDto.getCourseId();
		if (courseIdList != null && !courseIdList.isEmpty()) {
			com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
			com.cloudstudy.bo.FileToCourseExample.Criteria FileToCourseExampleCriteria = FileToCourseExample
					.createCriteria();
			FileToCourseExampleCriteria.andCourseIdIn(courseIdList);
			List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
			if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
				for (FileToCourse fileToCourse : FileToCourseList) {
					FileOriginIdSet.add(fileToCourse.getFileId());
				}
			}

			HomeworkExample HomeworkExample = new HomeworkExample();
			com.cloudstudy.bo.HomeworkExample.Criteria HomeworkExampleCriteria = HomeworkExample.createCriteria();
			HomeworkExampleCriteria.andCourseIdIn(courseIdList);
			List<Homework> HomeworkList = homeworkMapper.selectByExample(HomeworkExample);
			if (HomeworkList != null && !HomeworkList.isEmpty()) {
				ArrayList<Integer> homeworkIdList = new ArrayList<Integer>();
				for (Homework Homework : HomeworkList) {
					homeworkIdList.add(Homework.getId());
				}

				if (homeworkIdList != null && !homeworkIdList.isEmpty()) {
					com.cloudstudy.bo.FileToHomeworkExample FileToHomeworkExample = new com.cloudstudy.bo.FileToHomeworkExample();
					com.cloudstudy.bo.FileToHomeworkExample.Criteria FileToHomeworkExampleCriteria = FileToHomeworkExample
							.createCriteria();
					FileToHomeworkExampleCriteria.andHomeworkIdIn(homeworkIdList);
					List<FileToHomework> FileToHomeworkList = fileToHomeworkMapper
							.selectByExample(FileToHomeworkExample);
					if (FileToHomeworkList != null && !FileToHomeworkList.isEmpty()) {
						for (FileToHomework FileToHomework : FileToHomeworkList) {
							FileOriginIdSet.add(FileToHomework.getFileId());
						}
					}
				}
			}

		}

		ArrayList<Integer> homeworkIdList = fileOriginQueryDto.getHomeworkId();
		if (homeworkIdList != null && !homeworkIdList.isEmpty()) {
			com.cloudstudy.bo.FileToHomeworkExample FileToHomeworkExample = new com.cloudstudy.bo.FileToHomeworkExample();
			com.cloudstudy.bo.FileToHomeworkExample.Criteria FileToHomeworkExampleCriteria = FileToHomeworkExample
					.createCriteria();
			FileToHomeworkExampleCriteria.andHomeworkIdIn(homeworkIdList);
			List<FileToHomework> FileToHomeworkList = fileToHomeworkMapper.selectByExample(FileToHomeworkExample);
			if (FileToHomeworkList != null && !FileToHomeworkList.isEmpty()) {
				for (FileToHomework FileToHomework : FileToHomeworkList) {
					FileOriginIdSet.add(FileToHomework.getFileId());
				}
			}
		}

		ArrayList<String> userNoList = fileOriginQueryDto.getUserNo();
		if (userNoList != null && !userNoList.isEmpty()) {
			com.cloudstudy.bo.FileToCourseExample FileToCourseExample = new com.cloudstudy.bo.FileToCourseExample();
			com.cloudstudy.bo.FileToCourseExample.Criteria FileToCourseExampleCriteria = FileToCourseExample
					.createCriteria();
			FileToCourseExampleCriteria.andUploadernoIn(userNoList);
			List<FileToCourse> FileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
			if (FileToCourseList != null && !FileToCourseList.isEmpty()) {
				for (FileToCourse fileToCourse : FileToCourseList) {
					FileOriginIdSet.add(fileToCourse.getFileId());
				}
			}

			com.cloudstudy.bo.FileToHomeworkExample FileToHomeworkExample = new com.cloudstudy.bo.FileToHomeworkExample();
			com.cloudstudy.bo.FileToHomeworkExample.Criteria FileToHomeworkExampleCriteria = FileToHomeworkExample
					.createCriteria();
			FileToHomeworkExampleCriteria.andUploadernoIn(userNoList);
			List<FileToHomework> FileToHomeworkList = fileToHomeworkMapper.selectByExample(FileToHomeworkExample);
			if (FileToHomeworkList != null && !FileToHomeworkList.isEmpty()) {
				for (FileToHomework FileToHomework : FileToHomeworkList) {
					FileOriginIdSet.add(FileToHomework.getFileId());
				}
			}
		}

		if (FileOriginIdSet != null && !FileOriginIdSet.isEmpty()) {
			fileOriginExampleCriteria.andIdIn(new ArrayList<Integer>(FileOriginIdSet));
		} else if (courseIdList != null && !courseIdList.isEmpty()) {
			return new PageResultDto<List<FileOriginQueryDto>>((long) 0, new ArrayList<FileOriginQueryDto>());
		} else if (userNoList != null && !userNoList.isEmpty()) {
			return new PageResultDto<List<FileOriginQueryDto>>((long) 0, new ArrayList<FileOriginQueryDto>());
		} else if (homeworkIdList != null && !homeworkIdList.isEmpty()) {
			return new PageResultDto<List<FileOriginQueryDto>>((long) 0, new ArrayList<FileOriginQueryDto>());
		}

		ArrayList<String> dateRangementList = fileOriginQueryDto.getDateRangement();
		if (dateRangementList != null && dateRangementList.size() == 2) {
			fileOriginExampleCriteria.andCreateTimeBetween(DateUtil.stringToDateSpecial(dateRangementList.get(0)),
					DateUtil.stringToDateSpecial(dateRangementList.get(1)));
		}

		long total = fileOriginMapper.countByExample(fileOriginExample);

		if (fileOriginQueryDto.getPageDto() != null) {
			Integer page = fileOriginQueryDto.getPageDto().getCurrent();
			Integer rows = fileOriginQueryDto.getPageDto().getSize();
			PageHelper.startPage(page, rows);
		}

		List<FileOrigin> fileOriginList = fileOriginMapper.selectByExample(fileOriginExample);

		List<FileOriginQueryDto> fileOriginQueryDtoList = new ArrayList<FileOriginQueryDto>();
		if (fileOriginList == null || fileOriginList.isEmpty()) {
			return new PageResultDto<List<FileOriginQueryDto>>(total, fileOriginQueryDtoList);
		}

		for (FileOrigin fileOrigin : fileOriginList) {
			FileOriginQueryDto FileOriginQueryDto = generateQueryDto(fileOrigin);
			fileOriginQueryDtoList.add(FileOriginQueryDto);
		}
		return new PageResultDto<List<FileOriginQueryDto>>(total, fileOriginQueryDtoList);
	}

	private FileOriginQueryDto setCourseAndUploaderAndTeacher(FileOriginQueryDto fileOriginQueryDto) {

		FileToCourseExample fileToCourseExample = new FileToCourseExample();// 设置课程信息
		FileToCourseExample.Criteria fileToCourseCriteria = fileToCourseExample.createCriteria();
		fileToCourseCriteria.andFileIdEqualTo(fileOriginQueryDto.getId());
		List<FileToCourse> fileToCourseList = fileToCourseMapper.selectByExample(fileToCourseExample);

		if (fileToCourseList == null || fileToCourseList.isEmpty()) {
			return fileOriginQueryDto;
		}

		FileToCourse FileToCourse = fileToCourseList.get(0);
		Integer CourseId = FileToCourse.getCourseId();
		Course course = courseMapper.selectByPrimaryKey(CourseId);
		if (course == null) {
			return fileOriginQueryDto;
		}

		fileOriginQueryDto.setCourseId(course.getId());
		fileOriginQueryDto.setCourseName(course.getName());

		User teacher = userMapper.selectByPrimaryKey(course.getTeacherNo());
		if (teacher != null) {
			fileOriginQueryDto.setTeacherName(teacher.getName());
			fileOriginQueryDto.setTeacherNo(teacher.getNo());
		}

		String uploadeNo = FileToCourse.getUploaderno();
		User uploader = userMapper.selectByPrimaryKey(uploadeNo);
		if (uploader != null) {
			fileOriginQueryDto.setUploaderno(uploader.getNo());
			fileOriginQueryDto.setUploaderName(uploader.getName());
		}

		return fileOriginQueryDto;
	}

	private FileOriginQueryDto setHomeworkAndUploader(FileOriginQueryDto fileOriginQueryDto) {

		FileToHomeworkExample fileToHomeworkExample = new FileToHomeworkExample();// 设置任务信息
		FileToHomeworkExample.Criteria fileToHomeworkCriteria = fileToHomeworkExample.createCriteria();
		fileToHomeworkCriteria.andFileIdEqualTo(fileOriginQueryDto.getId());
		List<FileToHomework> fileToHomeworkList = fileToHomeworkMapper.selectByExample(fileToHomeworkExample);
		if (fileToHomeworkList == null || fileToHomeworkList.isEmpty()) {
			return fileOriginQueryDto;
		}
		FileToHomework FileToHomework = fileToHomeworkList.get(0);
		Integer HomeworkId = FileToHomework.getHomeworkId();
		Homework Homework = HomeworkMapper.selectByPrimaryKey(HomeworkId);
		if (Homework == null) {
			return fileOriginQueryDto;
		}

		fileOriginQueryDto.setHomeworkId(Homework.getId());
		fileOriginQueryDto.setHomeworkDeadline(DateUtil.dateToString(Homework.getDeadLine()));
		fileOriginQueryDto.setHomeworkContent(Homework.getContent());
		fileOriginQueryDto.setHomeworkTitle(Homework.getTitle());

		String uploadeNo = FileToHomework.getUploaderno();
		User uploader = userMapper.selectByPrimaryKey(uploadeNo);
		if (uploader != null) {
			fileOriginQueryDto.setUploaderno(uploader.getNo());
			fileOriginQueryDto.setUploaderName(uploader.getName());
		}

		Integer CourseId = Homework.getCourseId();
		Course course = courseMapper.selectByPrimaryKey(CourseId);
		if (course != null) {
			fileOriginQueryDto.setCourseId(course.getId());
			fileOriginQueryDto.setCourseName(course.getName());
		}

		User teacher = userMapper.selectByPrimaryKey(course.getTeacherNo());
		if (teacher != null) {
			fileOriginQueryDto.setTeacherName(teacher.getName());
			fileOriginQueryDto.setTeacherNo(teacher.getNo());
		}

		return fileOriginQueryDto;
	}

	private FileOriginQueryDto generateQueryDto(FileOrigin fileOrigin) {
		if (fileOrigin == null) {
			throw new CloudStudyException();
		}
		FileOriginQueryDto fileOriginQueryDto = new FileOriginQueryDto();
		BeanUtils.copyProperties(fileOrigin, fileOriginQueryDto);

		setCourseAndUploaderAndTeacher(fileOriginQueryDto);
		setHomeworkAndUploader(fileOriginQueryDto);

		fileOriginQueryDto.setSrc("https://localhost:9680/" + fileOrigin.getPath());

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

		String createTime;
		try {
			createTime = DateUtil.dateToString(fileOrigin.getCreateTime());
		} catch (Exception e) {
			createTime = DateUtil.getNowDateAsString();
		}
		fileOriginQueryDto.setCreateTime(createTime);

		String lastModifyTime;
		try {
			lastModifyTime = DateUtil.dateToString(fileOrigin.getLastModifyTime());
		} catch (Exception e) {
			lastModifyTime = DateUtil.getNowDateAsString();
		}
		fileOriginQueryDto.setLastModifyTime(lastModifyTime);

		return fileOriginQueryDto;
	}

	private void addFileToOther(FileOriginQueryDto FileOriginQueryDto) throws IOException {
		Integer fileId = FileOriginQueryDto.getId();
		Integer courseId = FileOriginQueryDto.getCourseId();
		Integer homeworkId = FileOriginQueryDto.getHomeworkId();
		String uploaderNo = FileOriginQueryDto.getUploaderno();

		if (fileId == null || uploaderNo == null || uploaderNo.isEmpty()) {
			return;
		}

		if (courseId != null && uploaderNo != null && !uploaderNo.isEmpty()) {
			FileToCourseExample FileToCourseExample = new FileToCourseExample();
			com.cloudstudy.bo.FileToCourseExample.Criteria criteria = FileToCourseExample.createCriteria();
			criteria.andCourseIdEqualTo(courseId);
			criteria.andFileIdEqualTo(fileId);
			criteria.andUploadernoEqualTo(uploaderNo);
			List<FileToCourse> fileToCourseList = fileToCourseMapper.selectByExample(FileToCourseExample);
			if (fileToCourseList == null || fileToCourseList.isEmpty()) {
				com.cloudstudy.bo.FileToCourse fileToCourse = new com.cloudstudy.bo.FileToCourse();
				fileToCourse.setCourseId(courseId);
				fileToCourse.setFileId(fileId);
				fileToCourse.setUploaderno(uploaderNo);
				fileToCourseMapper.insert(fileToCourse);
			}
		}

		if (homeworkId != null && uploaderNo != null && !uploaderNo.isEmpty()) {
			FileToHomeworkExample FileToHomeworkExample = new FileToHomeworkExample();
			com.cloudstudy.bo.FileToHomeworkExample.Criteria criteria = FileToHomeworkExample.createCriteria();
			criteria.andHomeworkIdEqualTo(homeworkId);
			criteria.andFileIdEqualTo(fileId);
			criteria.andUploadernoEqualTo(uploaderNo);
			List<FileToHomework> fileToHomeworkList = fileToHomeworkMapper.selectByExample(FileToHomeworkExample);
			if (fileToHomeworkList == null || fileToHomeworkList.isEmpty()) {
				com.cloudstudy.bo.FileToHomework fileToHomework = new com.cloudstudy.bo.FileToHomework();
				fileToHomework.setFileId(fileId);
				fileToHomework.setUploaderno(uploaderNo);
				fileToHomework.setHomeworkId(homeworkId);
				fileToHomeworkMapper.insert(fileToHomework);
			}
		}

	}

	private FileOrigin generate(MultipartFile multipartFile, Integer courseId, Integer homeworkId, String uploaderNo)
			throws IllegalStateException, IOException {
		if (multipartFile == null || multipartFile.getSize() == 0) {
			return null;
		}

		FileOrigin fileOrigin = new FileOrigin();

		String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename().replaceAll(" ", "");
		fileOrigin.setName(fileName);

		String fileExtension = FileUtil.getExtensionName(fileName);// 没有点的
		fileOrigin.setType(fileExtension);

		Integer fileSize = (int) multipartFile.getSize();
		fileOrigin.setSize(fileSize);

		String fileOriginPath = null;
		if (uploaderNo != null && !uploaderNo.isEmpty()) {
			if (courseId != null) {
				fileOriginPath = File.separator + "course" + File.separator + uploaderNo + File.separator + fileName;
			} else if (homeworkId != null) {
				fileOriginPath = File.separator + "homework" + File.separator + uploaderNo + File.separator + fileName;
			} else {
				fileOriginPath = File.separator + "default" + File.separator + uploaderNo + File.separator + fileName;
			}
		} else {
			if (courseId != null) {
				fileOriginPath = File.separator + "course" + File.separator + fileName;
			} else if (homeworkId != null) {
				fileOriginPath = File.separator + "homework" + File.separator + fileName;
			} else {
				fileOriginPath = File.separator + "default" + File.separator + fileName;
			}
		}
		String absoluteFileOriginPath = filePath + fileOriginPath;
		File saveFile = FileUtil.createFile(absoluteFileOriginPath);
		multipartFile.transferTo(saveFile);
		fileOrigin.setPath(fileOriginPath);

		String memo = fileOrigin.getName();
		if (courseId != null) {
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

		if (homeworkId != null) {
			Homework homework = homeworkMapper.selectByPrimaryKey(homeworkId);
			String homeworkTitile = homework.getTitle();
			if (memo == null || !memo.contains(homeworkTitile)) {
				memo += "," + homeworkTitile;
			}

			Course course = courseMapper.selectByPrimaryKey(homework.getCourseId());
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

		if (uploaderNo != null && !uploaderNo.isEmpty()) {
			User uploader = userMapper.selectByPrimaryKey(uploaderNo);
			if (uploader != null) {
				String uploaderName = uploader.getNo();
				String uploaderAccount = uploader.getAccount();

				if (memo == null || !memo.contains(uploaderName)) {
					memo += "," + uploaderName;
				}

				if (memo == null || !memo.contains(uploaderAccount)) {
					memo += "," + uploaderAccount;
				}

				if (memo == null || !memo.contains(uploaderNo)) {
					memo += "," + uploaderNo;
				}
			}
		}
		fileOrigin.setMemo(memo);

		fileOrigin.setCreateTime(new Date());
		fileOrigin.setLastModifyTime(new Date());

		return fileOrigin;
	}

	@Override
	public byte[] getFileByte(Integer fileOriginId) throws IOException {
		FileOriginQueryDto FileOriginQueryDto = findById(fileOriginId);

		String absoluteFileOriginPath = filePath + FileOriginQueryDto.getPath();
		File file = new File(absoluteFileOriginPath);
		return FileCopyUtils.copyToByteArray(file);
	}

}
