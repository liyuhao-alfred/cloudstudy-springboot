package com.cloudstudy.mapper;

import com.cloudstudy.bo.FileToCourse;
import com.cloudstudy.bo.FileToCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileToCourseMapper {
    long countByExample(FileToCourseExample example);

    int deleteByExample(FileToCourseExample example);

    int insert(FileToCourse record);

    int insertSelective(FileToCourse record);

    List<FileToCourse> selectByExample(FileToCourseExample example);

    int updateByExampleSelective(@Param("record") FileToCourse record, @Param("example") FileToCourseExample example);

    int updateByExample(@Param("record") FileToCourse record, @Param("example") FileToCourseExample example);
}