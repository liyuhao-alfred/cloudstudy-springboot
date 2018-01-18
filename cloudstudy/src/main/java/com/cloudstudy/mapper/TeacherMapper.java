package com.cloudstudy.mapper;

import com.cloudstudy.bo.Teacher;
import com.cloudstudy.bo.example.TeacherExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper extends BaseMapper<Teacher, TeacherExample> {

	List<Teacher> selectByExampleWithBLOBs(TeacherExample example);

	int updateByExampleWithBLOBs(@Param("record") Teacher record, @Param("example") TeacherExample example);

	int updateByPrimaryKeyWithBLOBs(Teacher record);

}