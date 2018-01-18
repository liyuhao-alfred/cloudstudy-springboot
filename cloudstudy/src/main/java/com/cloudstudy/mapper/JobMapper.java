package com.cloudstudy.mapper;

import com.cloudstudy.bo.Job;
import com.cloudstudy.bo.example.JobExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobMapper extends BaseMapper<Job, JobExample> {

	List<Job> selectByExampleWithBLOBs(JobExample example);

	int updateByExampleWithBLOBs(@Param("record") Job record, @Param("example") JobExample example);

	int updateByPrimaryKeyWithBLOBs(Job record);

}