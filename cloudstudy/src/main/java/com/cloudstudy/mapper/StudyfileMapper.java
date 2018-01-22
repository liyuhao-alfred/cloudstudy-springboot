package com.cloudstudy.mapper;

import com.cloudstudy.bo.Studyfile;
import com.cloudstudy.bo.example.StudyfileExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudyfileMapper extends BaseMapper<Studyfile, StudyfileExample> {

	List<Studyfile> selectByExampleWithBLOBs(StudyfileExample example);

	int updateByExampleWithBLOBs(@Param("record") Studyfile record, @Param("example") StudyfileExample example);

	int updateByPrimaryKeyWithBLOBs(Studyfile record);

}