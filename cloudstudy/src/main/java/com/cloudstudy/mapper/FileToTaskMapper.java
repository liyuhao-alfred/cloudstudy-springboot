package com.cloudstudy.mapper;

import com.cloudstudy.bo.FileToTask;
import com.cloudstudy.bo.FileToTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileToTaskMapper {
    long countByExample(FileToTaskExample example);

    int deleteByExample(FileToTaskExample example);

    int insert(FileToTask record);

    int insertSelective(FileToTask record);

    List<FileToTask> selectByExample(FileToTaskExample example);

    int updateByExampleSelective(@Param("record") FileToTask record, @Param("example") FileToTaskExample example);

    int updateByExample(@Param("record") FileToTask record, @Param("example") FileToTaskExample example);
}