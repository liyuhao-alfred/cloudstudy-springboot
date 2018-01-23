package com.cloudstudy.controller;

import java.io.IOException;
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

import com.cloudstudy.dto.StudyfileDto;
import com.cloudstudy.dto.StudyfileQueryParamDto;
import com.cloudstudy.dto.WebResult;
import com.cloudstudy.service.StudyfileService;
import com.cloudstudy.util.WebResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "文件资源管理模块")
@RestController
@RequestMapping("/cloudstudy/file")
public class StudyFileController {

	@Autowired
	private StudyfileService fileService;

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
	public @ResponseBody WebResult<StudyfileDto> find(@PathVariable("id") Integer id) {
		StudyfileDto fileDto = fileService.findById(id);
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
	public @ResponseBody WebResult<List<StudyfileDto>> find(
			@RequestParam(value = "fileQueryParamDto", required = true) StudyfileQueryParamDto fileQueryParamDto) {
		List<StudyfileDto> fileDtoList = fileService.find(fileQueryParamDto);
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
	public @ResponseBody WebResult<StudyfileDto> add(
			@ApiParam(value = "文件资源管理数据", required = true) @RequestBody StudyfileDto fileDto) throws IOException {
		fileDto = fileService.add(fileDto);
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
	public @ResponseBody WebResult<StudyfileDto> delete(@RequestParam(value = "id", required = true) Integer id)
			throws IOException {
		StudyfileDto fileDto = fileService.findById(id);
		fileService.deleteById(id);
		return WebResultUtil.success(fileDto);
	}

}
