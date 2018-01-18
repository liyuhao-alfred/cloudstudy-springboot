package com.cloudstudy.mapper;

import com.cloudstudy.bo.File;
import com.cloudstudy.bo.example.FileExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends BaseMapper<File, FileExample> {

	List<File> selectByExampleWithBLOBs(FileExample example);

	int updateByExampleWithBLOBs(@Param("record") File record, @Param("example") FileExample example);

	int updateByPrimaryKeyWithBLOBs(File record);

}