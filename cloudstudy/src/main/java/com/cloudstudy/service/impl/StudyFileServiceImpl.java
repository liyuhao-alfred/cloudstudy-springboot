package com.cloudstudy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cloudstudy.bo.Studyfile;
import com.cloudstudy.bo.example.StudyfileExample;
import com.cloudstudy.constant.SearchType;
import com.cloudstudy.dto.StudyFileDto;
import com.cloudstudy.dto.StudyfileQueryParamDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.StudyfileMapper;
import com.cloudstudy.mapper.StudyfilerelcourseMapper;
import com.cloudstudy.mapper.StudyfilereljobMapper;
import com.cloudstudy.mapper.StudyfilereltaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.StudyFileService;
import com.cloudstudy.service.UserService;

@Service
public class StudyFileServiceImpl implements StudyFileService {

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

	@Override
	public StudyFileDto add(StudyFileDto fileDto) {
		try {
			fileDto = generateFileDto(fileDto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		com.cloudstudy.bo.Studyfile file = new Studyfile();
		BeanUtils.copyProperties(fileDto, file);

		fileMapper.insert(file);
		fileDto.setId(file.getId());

		if (fileDto.getCourseId() != null) {
			com.cloudstudy.bo.Studyfilerelcourse filerelcourse = new com.cloudstudy.bo.Studyfilerelcourse(fileDto.getId(),
					fileDto.getCourseId());
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
	public void delete(Integer id) {
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

	}

	@Override
	public StudyFileDto findById(Integer id) {

		Studyfile studyFile = fileMapper.selectByPrimaryKey(id);
		StudyFileDto studyFileDto = new StudyFileDto();
		BeanUtils.copyProperties(studyFile, studyFileDto);

		return studyFileDto;

	}

	@Override
	public List<StudyFileDto> find(StudyfileQueryParamDto fileQueryParamDto) {

		StudyfileExample fileExample = new StudyfileExample();
		com.cloudstudy.bo.example.StudyfileExample.Criteria criteria = fileExample.createCriteria();

		String keyword = fileQueryParamDto.getKeyword();
		Integer searchType = fileQueryParamDto.getSearchType();
		if (!StringUtils.isEmpty(keyword)) {
			if (searchType == SearchType.fileName.getCode()) {
				criteria.andNameLike("%" + keyword + "%");
			}
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

		List<Studyfile> fileList = fileMapper.selectByExample(fileExample);
		if (fileList == null || fileList.isEmpty()) {
			return null;
		}

		List<StudyFileDto> fileDtoList = new ArrayList<StudyFileDto>();
		for (Studyfile file : fileList) {
			StudyFileDto fileDto = new StudyFileDto();
			BeanUtils.copyProperties(file, fileDto);
			fileDtoList.add(fileDto);
		}
		return fileDtoList;
	}

	public StudyFileDto generateFileDto(StudyFileDto fileDto) throws IOException {
		String fileExtension = com.cloudstudy.util.FileUtil.getExtensionName(fileDto.getFile().getName());
		String tempFilePath = generateAbsoluteFilePath(fileExtension);
		java.io.File tempFile = new java.io.File(tempFilePath);
		tempFile.createNewFile();
		FileCopyUtils.copy(tempFile, fileDto.getFile());

		fileDto.setCreateTime(new Date());
		fileDto.setLastModifyTime(new Date());
		fileDto.setPath(generateFilePath(tempFilePath));
		fileDto.setSize((int) tempFile.length());
		fileDto.setType(fileExtension);
		fileDto.setName(tempFile.getName());
		return fileDto;
	}

	private String generateAbsoluteFilePath(String fileExtension) {
		String filePath = StudyFileService.class.getClassLoader().getResource("").toString();
		filePath = filePath + java.io.File.separator + System.currentTimeMillis() + "." + fileExtension;
		return filePath;
	}

	private String generateFilePath(String tempFilePath) {
		String filePath = StudyFileService.class.getClassLoader().getResource("").toString();
		filePath = filePath + java.io.File.separator + System.currentTimeMillis() + "." + tempFilePath;
		return filePath;
	}

}
