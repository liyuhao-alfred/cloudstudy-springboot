package com.cloudstudy.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudstudy.dto.CourseStudentDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.FileOriginQueryParamDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.FileOriginService;
import com.cloudstudy.util.FileDownloadUtil;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "文件资源管理模块")
@RestController
@RequestMapping("/cloudstudy/file")
@CrossOrigin
@SuppressWarnings("unused")
public class FileOriginController {

	@Autowired
	private FileOriginService fileOriginService;

	@ApiOperation(value = "获取单个文件资源管理", notes = "传入工号或者学号获取单个文件资源管理")
	@ApiImplicitParam(name = "primaryKey", value = "文件资源管理工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{primaryKey}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:view") // 权限管理;
	public @ResponseBody WebResult<FileOriginQueryDto> find(@PathVariable("primaryKey") Integer primaryKey) {
		FileOriginQueryDto fileOriginQueryDto = fileOriginService.findById(primaryKey);
		return WebResultUtil.success(fileOriginQueryDto);
	}

	@ApiOperation(value = "获取文件资源管理列表", notes = "获取文件资源管理列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fileOriginQueryParamDto", value = "文件查询数据", paramType = "body", dataType = "FileOriginQueryParamDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("File:del") // 权限管理;
	public @ResponseBody WebResult<PageResultDto<List<FileOriginQueryDto>>> list(
			@ApiParam(value = "系统文件查询数据") @RequestBody FileOriginQueryParamDto fileOriginQueryParamDto) {
		PageResultDto<List<FileOriginQueryDto>> fileDtoList = fileOriginService.find(fileOriginQueryParamDto);
		if (fileDtoList == null || fileDtoList.getTotal() == null || fileDtoList.getContent() == null
				|| fileDtoList.getContent().isEmpty()) {
			fileDtoList = new PageResultDto<List<FileOriginQueryDto>>((long) 0, new ArrayList<FileOriginQueryDto>());
		}
		return WebResultUtil.success(fileDtoList);
	}

	@ApiOperation(value = "添加文件步骤1", notes = "添加文件步骤1")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "courseId", dataType = "Integer", value = "文件附加数据-课程编号", paramType = "body"),
			@ApiImplicitParam(name = "homeworkId", dataType = "Integer", value = "文件附加数据-作业编号", paramType = "body"),
			@ApiImplicitParam(name = "uploaderNo", dataType = "String", value = "文件附加数据-上传者编号", paramType = "body"),
			@ApiImplicitParam(name = "FileOriginQueryDto", dataType = "File", value = "文件", required = true, paramType = "body") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:add") // 权限管理;
	public @ResponseBody WebResult<FileOriginQueryDto> upload(//
			@RequestParam("courseId") Integer courseId, //
			@RequestParam("homeworkId") Integer homeworkId, //
			@RequestParam("uploaderNo") String uploaderNo, //
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			try {
				FileOriginQueryDto FileOriginQueryDto = fileOriginService.add(file, courseId, homeworkId, uploaderNo);
				return WebResultUtil.success(FileOriginQueryDto);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return WebResultUtil.fail("上传失败," + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				return WebResultUtil.fail("上传失败," + e.getMessage());
			}

		} else {
			return WebResultUtil.fail("上传失败，因为文件是空的");
		}

	}

	@ApiOperation(value = "删除文件资源管理", notes = "通过文件资源管理工号或者学号删除文件资源管理")
	@ApiImplicitParam(name = "primaryKey", value = "文件资源管理工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("File:delete") // 权限管理;
	public @ResponseBody WebResult<FileOriginQueryDto> delete(
			@RequestParam(value = "primaryKey", required = true) Integer primaryKey) throws IOException {
		FileOriginQueryDto fileOriginQueryDto = fileOriginService.findById(primaryKey);
		fileOriginService.deleteById(primaryKey);
		return WebResultUtil.success(fileOriginQueryDto);
	}

	@ApiOperation(value = "删除文件资源管理", notes = "通过文件资源管理工号或者学号删除文件资源管理")
	@ApiImplicitParam(name = "primaryKey", value = "文件资源管理工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/download", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:delete") // 权限管理;
	public @ResponseBody WebResult<FileOriginQueryDto> download(//
			@RequestParam(value = "primaryKey", required = true) Integer primaryKey, //
			HttpServletRequest request, //
			HttpServletResponse response) throws Exception {
		FileOriginQueryDto fileOriginQueryDto = fileOriginService.findById(primaryKey);
		if (fileOriginQueryDto == null) {
			return WebResultUtil.fail();
		}

		byte[] resultByte = fileOriginService.getFileByte(primaryKey);
		String fileName = fileOriginQueryDto.getName();
		FileDownloadUtil.readFileToResponse(response, request, resultByte, fileName);
		return WebResultUtil.success(fileOriginQueryDto);
	}

}
