package com.cloudstudy.controller;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.cloudstudy.Application;
import com.cloudstudy.BaseTest;
import com.cloudstudy.bo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class User2ServiceTest {

	@Autowired
	private UserController adminController;

	private MockMvc mockMvc;

	private User user = new User();
	private String str = BaseTest.str;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

		String str = BaseTest.str;

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
	}

	// 验证controller是否正常响应并打印返回结果
	@Test
	public void add() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		System.out.println(json);

		json = JSONObject.toJSONString(user);
		System.out.println(json);

		// 指定表单方法和表单行为
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/add").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(json))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		System.out.println("输出 :" + mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void get() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user").accept(MediaType.APPLICATION_JSON_UTF8)
						.param("account", str).param("password", str))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		System.out.println("输出:" + mvcResult.getResponse().getContentAsString());
	}

}
