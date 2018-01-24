package com.cloudstudy.mapper;

import com.cloudstudy.bo.FileToJob;
import com.cloudstudy.bo.FileToJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileToJobMapper {
    long countByExample(FileToJobExample example);

    int deleteByExample(FileToJobExample example);

    int insert(FileToJob record);

    int insertSelective(FileToJob record);

    List<FileToJob> selectByExample(FileToJobExample example);

    int updateByExampleSelective(@Param("record") FileToJob record, @Param("example") FileToJobExample example);

    int updateByExample(@Param("record") FileToJob record, @Param("example") FileToJobExample example);
}