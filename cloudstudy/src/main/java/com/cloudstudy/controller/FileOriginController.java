package com.cloudstudy.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudstudy.dto.FileOriginDto;
import com.cloudstudy.dto.FileOriginQueryDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.FileOriginService;
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
public class FileOriginController {

	@Autowired
	private FileOriginService fileOriginService;

	/**
	 * 获取单个文件资源管理
	 * 
	 * @param primaryKey
	 * @return
	 */
	@ApiOperation(value = "获取单个文件资源管理", notes = "传入工号或者学号获取单个文件资源管理")
	@ApiImplicitParam(name = "primaryKey", value = "文件资源管理工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{primaryKey}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:view") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> find(@PathVariable("primaryKey") Integer primaryKey) {
		FileOriginDto fileOriginDto = fileOriginService.findById(primaryKey);
		return WebResultUtil.success(fileOriginDto);
	}

	/**
	 * 获取文件资源管理列表
	 * 
	 * @param file
	 * @param keyword
	 * @return
	 */
	@ApiOperation(value = "获取文件资源管理列表", notes = "获取文件资源管理列表")
	@RequestMapping(value = "/list", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("File:del") // 权限管理;
	public @ResponseBody WebResult<List<FileOriginDto>> find(
			@RequestParam(value = "fileQueryDto", required = true) FileOriginQueryDto fileQueryDto) {
		List<FileOriginDto> fileDtoList = fileOriginService.find(fileQueryDto);
		return WebResultUtil.success(fileDtoList);
	}

	/**
	 * 添加文件步骤1
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "添加文件步骤1", notes = "添加文件步骤1")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fileOriginDto", value = "文件数据", required = true, paramType = "body", dataType = "File") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/addFileStep1", method = { RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:add") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> addFileStep1(
			@ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			try {
				/*
				 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到 d:/files大家是否能实现呢？ 等等;
				 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名； 3、文件格式; 4、文件大小的限制;
				 */
				System.out.println(file.getName());

				FileOriginDto fileOriginDto = fileOriginService.add(file);
				return WebResultUtil.success(fileOriginDto);

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

	/**
	 * 添加文件步骤2
	 * 
	 * @param fileOriginDto
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "添加文件步骤2", notes = "添加文件步骤2")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fileOriginDto", value = "文件描述数据", required = true, paramType = "body", dataType = "FileOriginDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/addFileStep2", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	// @RequiresPermissions("File:add") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> addFileStep2(
			@ApiParam(value = "文件描述内容", required = true) @RequestBody FileOriginDto fileOriginDto) throws IOException {
		fileOriginDto = fileOriginService.add(fileOriginDto);
		return WebResultUtil.success(fileOriginDto);
	}

	/**
	 * 删除文件资源管理
	 *
	 * @param primaryKey
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "删除文件资源管理", notes = "通过文件资源管理工号或者学号删除文件资源管理")
	@ApiImplicitParam(name = "primaryKey", value = "文件资源管理工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	// @RequiresPermissions("File:delete") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> delete(
			@RequestParam(value = "primaryKey", required = true) Integer primaryKey) throws IOException {
		FileOriginDto fileOriginDto = fileOriginService.findById(primaryKey);
		fileOriginService.deleteById(primaryKey);
		return WebResultUtil.success(fileOriginDto);
	}

}
