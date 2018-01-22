package com.cloudstudy.service;

import java.util.HashSet;

public interface MenuService {

	/**
	 * 根据账号和权限角色生成菜单
	 * 
	 * @param account
	 * @param roleTyppe
	 * @return
	 */
	HashSet<String> generateMenu(String account, Integer roleTyppe);

}
