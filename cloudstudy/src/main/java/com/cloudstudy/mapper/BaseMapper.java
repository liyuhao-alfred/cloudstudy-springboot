package com.cloudstudy.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T, E> {

	long countByExample(E example);

	int deleteByExample(E example);

	int deleteByPrimaryKey(Object primarykey);

	int insert(@Valid T record);

	int insertSelective(T record);

	T select(Object primarykey);

	List<T> selectSelective();

	List<T> selectByExample(E example);

	T selectByPrimaryKey(Object primarykey);

	int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

	int updateByExample(@Param("record") T record, @Param("example") E example);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	int update(T record);

}