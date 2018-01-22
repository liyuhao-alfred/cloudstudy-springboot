package com.cloudstudy.mapper;

import com.cloudstudy.bo.Courserelteacher;
import com.cloudstudy.bo.example.CourserelteacherExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourserelteacherMapper {
    long countByExample(CourserelteacherExample example);

    int deleteByExample(CourserelteacherExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Courserelteacher record);

    int insertSelective(Courserelteacher record);

    List<Courserelteacher> selectByExampleWithBLOBs(CourserelteacherExample example);

    List<Courserelteacher> selectByExample(CourserelteacherExample example);

    Courserelteacher selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Courserelteacher record, @Param("example") CourserelteacherExample example);

    int updateByExampleWithBLOBs(@Param("record") Courserelteacher record, @Param("example") CourserelteacherExample example);

    int updateByExample(@Param("record") Courserelteacher record, @Param("example") CourserelteacherExample example);

    int updateByPrimaryKeySelective(Courserelteacher record);

    int updateByPrimaryKeyWithBLOBs(Courserelteacher record);

    int updateByPrimaryKey(Courserelteacher record);
}