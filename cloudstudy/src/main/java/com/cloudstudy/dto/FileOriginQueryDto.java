package com.cloudstudy.dto;

public class FileOriginQueryDto {

	private Integer id;

	private Integer courseId;

	private String courseName;

	private String teacherNo;

	private String teacherName;

	private Integer homeworkId;

	private String homeworkTitle;

	private String homeworkContent;

	private String homeworkDeadline;

	private String uploaderno;

	private String uploaderName;

	private String name;

	private String path;

	private String src;

	private String type;

	/**
	 * 1图片，2文档，3视频
	 */
	private Integer fileTypeName;

	private Integer size;

	private String createTime;

	private String lastModifyTime;

	private String memo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getHomeworkTitle() {
		return homeworkTitle;
	}

	public void setHomeworkTitle(String homeworkTitle) {
		this.homeworkTitle = homeworkTitle;
	}

	public String getHomeworkContent() {
		return homeworkContent;
	}

	public void setHomeworkContent(String homeworkContent) {
		this.homeworkContent = homeworkContent;
	}

	public String getHomeworkDeadline() {
		return homeworkDeadline;
	}

	public void setHomeworkDeadline(String homeworkDeadline) {
		this.homeworkDeadline = homeworkDeadline;
	}

	public String getUploaderno() {
		return uploaderno;
	}

	public void setUploaderno(String uploaderno) {
		this.uploaderno = uploaderno;
	}

	public String getUploaderName() {
		return uploaderName;
	}

	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(Integer fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}