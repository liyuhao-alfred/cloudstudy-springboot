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
import com.cloudstudy.bo.User;
import com.cloudstudy.dto.UserDto;

/**
 * 逻辑测试,mockito框架
 * 
 * @ClassName UserServiceTest
 * @author alfred
 * @Date 2018年1月18日 下午3:58:37
 * @version 1.0.0
 */
public class UserServiceTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private UserService userService;

	private User user = new User();
	private UserDto userDto = new UserDto();
	private String str = BaseTest.str;

	@Before
	public void setup() {
		user.setAccount(str);
		user.setCreateTime(new Date());
		user.setEmail(str);
		user.setLastModifyTime(new Date());
		user.setName(str);
		user.setNo(str);
		user.setPassword(str);
		user.setPhone(str);
		user.setSex(0);
		user.setStatus(0);

		BeanUtils.copyProperties(user, userDto);
	}

	/**
	 * 模拟返回值
	 * 
	 * @
	 */
	@Test
	public void returnExactValue() {
		UserService userService = Mockito.mock(UserService.class);
		when(userService.findByNo(str)).thenReturn(userDto);
		assertEquals(userDto, userService.findByNo(str));
	}

	/**
	 * 模拟空指针
	 * 
	 * @
	 */
	@Test(expected = NullPointerException.class)
	public void testForNullPointerException() {
		UserService userService = Mockito.mock(UserService.class);
		when(userService.findByNo(str)).thenThrow(new NullPointerException());
		try {
			userService.findByNo(str);
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
		userService.save(userDto);
		verify(userService).save(userDto);

		userService.findByNo(str);
		userService.findByNo(str);
		verify(userService, times(2)).findByNo(str);
	}

}