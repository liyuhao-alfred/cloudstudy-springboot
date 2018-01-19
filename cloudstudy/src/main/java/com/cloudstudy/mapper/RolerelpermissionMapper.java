package com.cloudstudy.mapper;

import com.cloudstudy.bo.Rolerelpermission;
import com.cloudstudy.bo.example.RolerelpermissionExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolerelpermissionMapper extends BaseMapper<Rolerelpermission, RolerelpermissionExample> {
	long countByExample(RolerelpermissionExample example);

	int deleteByExample(RolerelpermissionExample example);

	int insert(Rolerelpermission record);

	int insertSelective(Rolerelpermission record);

	List<Rolerelpermission> selectByExample(RolerelpermissionExample example);

	int updateByExampleSelective(@Param("record") Rolerelpermission record,
			@Param("example") RolerelpermissionExample example);

	int updateByExample(@Param("record") Rolerelpermission record, @Param("example") RolerelpermissionExample example);
}