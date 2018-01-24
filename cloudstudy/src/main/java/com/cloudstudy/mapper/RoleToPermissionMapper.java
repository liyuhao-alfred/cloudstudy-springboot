package com.cloudstudy.mapper;

import com.cloudstudy.bo.RoleToPermission;
import com.cloudstudy.bo.RoleToPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleToPermissionMapper {
    long countByExample(RoleToPermissionExample example);

    int deleteByExample(RoleToPermissionExample example);

    int insert(RoleToPermission record);

    int insertSelective(RoleToPermission record);

    List<RoleToPermission> selectByExample(RoleToPermissionExample example);

    int updateByExampleSelective(@Param("record") RoleToPermission record, @Param("example") RoleToPermissionExample example);

    int updateByExample(@Param("record") RoleToPermission record, @Param("example") RoleToPermissionExample example);
}