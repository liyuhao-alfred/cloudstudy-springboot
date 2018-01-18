package com.cloudstudy.mapper;

import com.cloudstudy.bo.Systemlog;
import com.cloudstudy.bo.example.SystemlogExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemlogMapper extends BaseMapper<Systemlog, SystemlogExample> {

	List<Systemlog> selectByExampleWithBLOBs(SystemlogExample example);

	int updateByExampleWithBLOBs(@Param("record") Systemlog record, @Param("example") SystemlogExample example);

	int updateByPrimaryKeyWithBLOBs(Systemlog record);

}