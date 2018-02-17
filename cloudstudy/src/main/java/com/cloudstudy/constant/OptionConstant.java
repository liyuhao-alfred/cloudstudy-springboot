/******************************************************************
 *    
 *    Package:     com.cloudstudy.constant
 *
 *    Filename:    RoleTypeConstant.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月22日 上午9:50:23
 *
 *    - first revision 
 *
 *****************************************************************/
package com.cloudstudy.constant;

import java.util.List;

import com.cloudstudy.dto.CourseQueryDto;
import com.cloudstudy.dto.HomeworkQueryDto;
import com.cloudstudy.dto.PageResultDto;
import com.cloudstudy.dto.UserQueryDto;

public class OptionConstant {
	public static PageResultDto<List<CourseQueryDto>> courseQueryDtoPageResultDto = null;
	public static PageResultDto<List<UserQueryDto>> userQueryDtoPageResultDto = null;
	public static PageResultDto<List<HomeworkQueryDto>> homeworkQueryDtoPageResultDto = null;
	public static PageResultDto<List<UserQueryDto>> teacherQueryDtoPageResultDto = null;
	public static PageResultDto<List<UserQueryDto>> studentQueryDtoPageResultDto = null;

}
