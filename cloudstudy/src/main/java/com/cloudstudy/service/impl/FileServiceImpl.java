package com.cloudstudy.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstudy.bo.File;
import com.cloudstudy.dto.FileDto;
import com.cloudstudy.dto.FileQueryParamDto;
import com.cloudstudy.mapper.CourserelstudentMapper;
import com.cloudstudy.mapper.CourserelteacherMapper;
import com.cloudstudy.mapper.FileMapper;
import com.cloudstudy.mapper.FilerelcourseMapper;
import com.cloudstudy.mapper.FilereljobMapper;
import com.cloudstudy.mapper.FilereltaskMapper;
import com.cloudstudy.mapper.JobMapper;
import com.cloudstudy.mapper.TaskMapper;
import com.cloudstudy.service.FileService;
import com.cloudstudy.service.UserService;

@Service
public class FileServiceImpl implements FileService {

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
	private FileMapper fileMapper;
	@Autowired
	private FilerelcourseMapper filerelcourseMapper;
	@Autowired
	private FilereltaskMapper filereltaskMapper;
	@Autowired
	private FilereljobMapper filereljobMapper;

	@Override
	public FileDto add(FileDto fileDto) {
		com.cloudstudy.bo.File file = new File();
		BeanUtils.copyProperties(fileDto, file);

		fileMapper.insert(file);
		fileDto.setId(file.getId());

		if (fileDto.getCourseId() != null) {
			com.cloudstudy.bo.Filerelcourse filerelcourse = new com.cloudstudy.bo.Filerelcourse(fileDto.getId(),
					fileDto.getCourseId());
			filerelcourseMapper.insert(filerelcourse);
		}

		if (fileDto.getTaskId() != null) {
			com.cloudstudy.bo.Filereltask filereltask = new com.cloudstudy.bo.Filereltask(fileDto.getId(),
					fileDto.getTaskId());
			filereltaskMapper.insert(filereltask);
		}

		if (fileDto.getJobId() != null) {
			com.cloudstudy.bo.Filereljob filereljob = new com.cloudstudy.bo.Filereljob(fileDto.getId(),
					fileDto.getJobId());
			filereljobMapper.insert(filereljob);
		}
		return fileDto;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public FileDto update(FileDto fileDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDto findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileDto> find(FileQueryParamDto fileQueryParamDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
