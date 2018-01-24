package com.cloudstudy.mapper;

import com.cloudstudy.bo.FileOrigin;
import com.cloudstudy.bo.FileOriginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileOriginMapper {
    long countByExample(FileOriginExample example);

    int deleteByExample(FileOriginExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FileOrigin record);

    int insertSelective(FileOrigin record);

    List<FileOrigin> selectByExample(FileOriginExample example);

    FileOrigin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FileOrigin record, @Param("example") FileOriginExample example);

    int updateByExample(@Param("record") FileOrigin record, @Param("example") FileOriginExample example);

    int updateByPrimaryKeySelective(FileOrigin record);

    int updateByPrimaryKey(FileOrigin record);
}