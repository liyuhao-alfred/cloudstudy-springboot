package com.cloudstudy.mapper;

import com.cloudstudy.bo.OperateLog;
import com.cloudstudy.bo.OperateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateLogMapper {
    long countByExample(OperateLogExample example);

    int deleteByExample(OperateLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByExampleWithBLOBs(OperateLogExample example);

    List<OperateLog> selectByExample(OperateLogExample example);

    OperateLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExampleWithBLOBs(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExample(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKeyWithBLOBs(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
}