package com.cloudstudy.mapper;

import com.cloudstudy.bo.Role;
import com.cloudstudy.bo.example.RoleExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role, RoleExample> {
	long countByExample(RoleExample example);

	int deleteByExample(RoleExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Role record);

	int insertSelective(Role record);

	List<Role> selectByExample(RoleExample example);

	Role selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

	int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
}