package com.cloudstudy.dto;

import java.io.File;
import java.util.Date;

public class FileOriginQueryDto {

	private Integer id;

	private CourseDto courseDto = new CourseDto();

	private TaskDto taskDto = new TaskDto();

	private JobDto jobDto = new JobDto();

	private UserDto userDto = new UserDto();

	private String name;

	private String path;

	private String src;

	/**
	 * 1图片，2文档，3视频
	 */
	private Integer fileTypeName;

	private String type;

	private Integer size;

	private Date createTime;

	private Date lastModifyTime;

	private String memo;

	private File file;

	public FileOriginQueryDto() {
		super();
	}

	public FileOriginQueryDto(File file) {
		super();
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public CourseDto getCourseDto() {
		return courseDto;
	}

	public void setCourseDto(CourseDto courseDto) {
		this.courseDto = courseDto;
	}

	public TaskDto getTaskDto() {
		return taskDto;
	}

	public void setTaskDto(TaskDto taskDto) {
		this.taskDto = taskDto;
	}

	public JobDto getJobDto() {
		return jobDto;
	}

	public void setJobDto(JobDto jobDto) {
		this.jobDto = jobDto;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(Integer fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}