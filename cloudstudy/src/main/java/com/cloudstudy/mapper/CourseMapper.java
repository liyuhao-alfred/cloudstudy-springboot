package com.cloudstudy.mapper;

import com.cloudstudy.bo.Course;
import com.cloudstudy.bo.example.CourseExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper extends BaseMapper<Course, CourseExample> {

	List<Course> selectByExampleWithBLOBs(CourseExample example);

	int updateByExampleWithBLOBs(@Param("record") Course record, @Param("example") CourseExample example);

	int updateByPrimaryKeyWithBLOBs(Course record);

}