package com.cloudstudy.mapper;

import com.cloudstudy.bo.Studyfile;
import com.cloudstudy.bo.example.StudyfileExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudyfileMapper extends BaseMapper<Studyfile, StudyfileExample> {

	long countByExample(StudyfileExample example);

	int deleteByExample(StudyfileExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Studyfile record);

	int insertSelective(Studyfile record);

	List<Studyfile> selectByExample(StudyfileExample example);

	Studyfile selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Studyfile record, @Param("example") StudyfileExample example);

	int updateByExample(@Param("record") Studyfile record, @Param("example") StudyfileExample example);

	int updateByPrimaryKeySelective(Studyfile record);

	int updateByPrimaryKey(Studyfile record);

}