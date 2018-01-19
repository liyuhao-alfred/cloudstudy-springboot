package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.bo.Role;

public interface RoleService {

	List<Role> findByUserNo(String no);

}
