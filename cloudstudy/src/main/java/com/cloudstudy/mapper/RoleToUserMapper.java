package com.cloudstudy.mapper;

import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.bo.RoleToUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleToUserMapper {
    long countByExample(RoleToUserExample example);

    int deleteByExample(RoleToUserExample example);

    int insert(RoleToUser record);

    int insertSelective(RoleToUser record);

    List<RoleToUser> selectByExample(RoleToUserExample example);

    int updateByExampleSelective(@Param("record") RoleToUser record, @Param("example") RoleToUserExample example);

    int updateByExample(@Param("record") RoleToUser record, @Param("example") RoleToUserExample example);
}