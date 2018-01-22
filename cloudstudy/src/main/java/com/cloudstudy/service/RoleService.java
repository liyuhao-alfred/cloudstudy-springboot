package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.dto.RoleDto;

public interface RoleService {

	/**
	 * 通过用户找权限
	 * 
	 * @param no
	 * @return
	 */
	List<RoleDto> findRoleByUserNo(String no);

}
