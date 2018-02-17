package com.cloudstudy.mapper;

import com.cloudstudy.bo.FileToHomework;
import com.cloudstudy.bo.FileToHomeworkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileToHomeworkMapper {
    long countByExample(FileToHomeworkExample example);

    int deleteByExample(FileToHomeworkExample example);

    int insert(FileToHomework record);

    int insertSelective(FileToHomework record);

    List<FileToHomework> selectByExample(FileToHomeworkExample example);

    int updateByExampleSelective(@Param("record") FileToHomework record, @Param("example") FileToHomeworkExample example);

    int updateByExample(@Param("record") FileToHomework record, @Param("example") FileToHomeworkExample example);
}