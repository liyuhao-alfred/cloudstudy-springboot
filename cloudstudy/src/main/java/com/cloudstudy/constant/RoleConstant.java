/******************************************************************
 *    
 *    Package:     com.cloudstudy.constant
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月16日 下午5:35:56
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月16日 下午5:35:56 
 * @version 1.0.0  
 */
package com.cloudstudy.constant;

import com.cloudstudy.exception.CloudStudyException;

public enum RoleConstant {

	superadmin(1, "superadmin", "超级管理员"), //
	admin(2, "admin", "管理员"), //
	teacher(3, "teacher", "教师"), //
	student(4, "student", "学生"); //

	private int id;
	private String code;
	private String description;

	public static void main(String[] args) {
		for (RoleConstant s : RoleConstant.values())
			System.out.println(s + ", ordinal " + s.ordinal());
	}

	public static int getId(String code) {
		for (RoleConstant roleConstant : RoleConstant.values()) {
			if (roleConstant.getCode().equalsIgnoreCase(code)) {
				return roleConstant.getId();
			}
		}
		throw new CloudStudyException();
	}

	public static String getName(int id) {
		for (RoleConstant roleConstant : RoleConstant.values()) {
			if (roleConstant.getId() == id) {
				return roleConstant.getCode();
			}
		}
		throw new CloudStudyException();
	}

	public static String getDescription(String code) {
		for (RoleConstant roleConstant : RoleConstant.values()) {
			if (roleConstant.getCode().equalsIgnoreCase(code)) {
				return roleConstant.getDescription();
			}
		}
		throw new CloudStudyException();
	}

	RoleConstant(int id, String code, String description) {
		this.code = code;
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}