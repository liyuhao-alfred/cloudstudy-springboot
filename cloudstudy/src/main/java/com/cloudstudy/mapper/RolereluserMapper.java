package com.cloudstudy.mapper;

import com.cloudstudy.bo.Rolereluser;
import com.cloudstudy.bo.example.RolereluserExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolereluserMapper extends BaseMapper<Rolereluser, RolereluserExample> {
	long countByExample(RolereluserExample example);

	int deleteByExample(RolereluserExample example);

	int insert(Rolereluser record);

	int insertSelective(Rolereluser record);

	List<Rolereluser> selectByExample(RolereluserExample example);

	int updateByExampleSelective(@Param("record") Rolereluser record, @Param("example") RolereluserExample example);

	int updateByExample(@Param("record") Rolereluser record, @Param("example") RolereluserExample example);
}