package com.cloudstudy.mapper;

import com.cloudstudy.bo.Operatelog;
import com.cloudstudy.bo.example.OperatelogExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperatelogMapper extends BaseMapper<Operatelog, OperatelogExample> {

	List<Operatelog> selectByExampleWithBLOBs(OperatelogExample example);

	int updateByExampleWithBLOBs(@Param("record") Operatelog record, @Param("example") OperatelogExample example);

	int updateByPrimaryKeyWithBLOBs(Operatelog record);

}