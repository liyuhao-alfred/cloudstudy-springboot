package com.cloudstudy.mapper;

import com.cloudstudy.bo.Task;
import com.cloudstudy.bo.example.TaskExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskMapper extends BaseMapper<Task, TaskExample> {

	List<Task> selectByExampleWithBLOBs(TaskExample example);

	int updateByExampleWithBLOBs(@Param("record") Task record, @Param("example") TaskExample example);

	int updateByPrimaryKeyWithBLOBs(Task record);

}