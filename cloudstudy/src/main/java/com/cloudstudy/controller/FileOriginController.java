package com.cloudstudy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import io.swagger.models.auth.In;

@Api(value = "文件资源管理模块")
@RestController
@RequestMapping("/cloudstudy/file")
public class FileOriginController {

	@Autowired
	private FileOriginService fileOriginService;

	/**
	 * 获取单个文件资源管理
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取单个文件资源管理", notes = "传入工号或者学号获取单个文件资源管理")
	@ApiImplicitParam(name = "id", value = "文件资源管理工号或者学号", required = true, paramType = "path", dataType = "Integer") // 注意：paramType需要指定为path,不然不能正常获取
	@RequestMapping(value = "/single/{id}", produces = { "application/json; charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@RequiresPermissions("File:view") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> find(@PathVariable("id") Integer id) {
		FileOriginDto fileDto = fileOriginService.findById(id);
		return WebResultUtil.success(fileDto);
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
	@RequiresPermissions("File:del") // 权限管理;
	public @ResponseBody WebResult<List<FileOriginDto>> find(
			@RequestParam(value = "fileQueryDto", required = true) FileOriginQueryDto fileQueryDto) {
		List<FileOriginDto> fileDtoList = fileOriginService.find(fileQueryDto);
		return WebResultUtil.success(fileDtoList);
	}

	/**
	 * 新建文件资源管理
	 * 
	 * @param fileDto
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "新建文件资源管理", notes = "新建一个文件资源管理")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fileDto", value = "文件资源管理数据", required = true, paramType = "body", dataType = "FileDto") }) // 注意：paramType需要指定为body
	@RequestMapping(value = "/add", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("File:add") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> add(
			@ApiParam(value = "文件资源管理数据", required = true) @RequestBody FileOriginDto fileDto) throws IOException {
		fileDto = fileOriginService.add(fileDto);
		return WebResultUtil.success(fileDto);
	}

	/**
	 * 删除文件资源管理
	 *
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "删除文件资源管理", notes = "通过文件资源管理工号或者学号删除文件资源管理")
	@ApiImplicitParam(name = "id", value = "文件资源管理工号或者学号", required = true, paramType = "body", dataType = "String")
	@RequestMapping(value = "/delete", produces = { "application/json; charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresPermissions("File:delete") // 权限管理;
	public @ResponseBody WebResult<FileOriginDto> delete(@RequestParam(value = "id", required = true) Integer id)
			throws IOException {
		FileOriginDto fileDto = fileOriginService.findById(id);
		fileOriginService.deleteById(id);
		return WebResultUtil.success(fileDto);
	}

}
