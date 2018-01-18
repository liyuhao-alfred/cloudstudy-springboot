package com.cloudstudy.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.BeanUtils;

import com.cloudstudy.BaseTest;
import com.cloudstudy.bo.Admin;
import com.cloudstudy.dto.AdminDto;

/**
 * 逻辑测试,mockito框架
 * 
 * @ClassName AdminServiceTest
 * @author alfred
 * @Date 2018年1月18日 下午3:58:37
 * @version 1.0.0
 */
public class AdminServiceTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private AdminService adminService;

	private Admin admin = new Admin();
	private AdminDto adminDto = new AdminDto();
	private String str = BaseTest.str;

	@Before
	public void setup() {
		admin.setAccount(str);
		admin.setCreateTime(new Date());
		admin.setEmail(str);
		admin.setLastModifyTime(new Date());
		admin.setLevel(1);
		admin.setName(str);
		admin.setNo(str);
		admin.setPassword(str);
		admin.setPhone(str);
		admin.setSex(0);
		admin.setStatus(0);

		BeanUtils.copyProperties(admin, adminDto);
	}

	/**
	 * 模拟返回值
	 * 
	 * @
	 */
	@Test
	public void returnExactValue() {
		AdminService adminService = Mockito.mock(AdminService.class);
		when(adminService.findAdmin(str)).thenReturn(admin);
		assertEquals(admin, adminService.findAdmin(str));
	}

	/**
	 * 模拟空指针
	 * 
	 * @
	 */
	@Test(expected = NullPointerException.class)
	public void testForNullPointerException() {
		AdminService adminService = Mockito.mock(AdminService.class);
		when(adminService.findAdmin(str)).thenThrow(new NullPointerException());
		try {
			adminService.findAdmin(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法是否被调用
	 * 
	 */
	@Test
	public void verifyNumebrOfExecutions() {
		adminService.saveAdmin(adminDto);
		verify(adminService).saveAdmin(adminDto);

		adminService.findAdmin(str);
		adminService.findAdmin(str);
		verify(adminService, times(2)).findAdmin(str);
	}

}