package com.cloudstudy.service;

import java.util.List;

import com.cloudstudy.bo.Admin;
import com.cloudstudy.dto.AdminDto;

public interface AdminService {

	Admin findAdmin(String no);

	List<Admin> findAdmin(Admin admin, String keyword);

	AdminDto saveAdmin(AdminDto adminDto);

	void deleteAdmin(String no);

	Admin updateAdmin(Admin admin);

	Admin loginAdmin(String account, String password);

}
