package com.cloudstudy.service;

import java.util.ArrayList;
import java.util.List;

import com.cloudstudy.bo.RoleToUser;
import com.cloudstudy.dto.RoleDto;

public interface RoleService {
	/**
	 * 证件用户权限
	 * 
	 * @param no
	 * @return
	 */
	void saveRoleByUserNoAndRoleCode(String roleCode, String userNo);

	/**
	 * 通过用户找权限
	 * 
	 * @param no
	 * @return
	 */
	List<RoleDto> findRoleByUserNo(String userNo);

	/**
	 * 通过用户找权限
	 * 
	 * @param no
	 * @return
	 */
	List<RoleToUser> findRoleByUserNoAndRoleCode(String roleCode, String userNo);

	List<RoleDto> findAllRoleWithPermission();

	ArrayList<String> findRoleStringByUserNo(String userNo);

	void deleteRoleByUserNo(String userNo);

}
