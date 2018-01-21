package com.cloudstudy.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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

import com.cloudstudy.Application;
import com.cloudstudy.BaseTest;
import com.cloudstudy.bo.User;
import com.cloudstudy.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserControllerTest {

	@Autowired
	private UserController adminController;

	private MockMvc mockMvc;

	@Spy
	@Autowired
	private UserService adminService;

	private User user = new User();
	private String str = BaseTest.str;

	@Before
	public void setup() {

		// this must be called for the @Mock annotations above to be processed
		// and for the mock service to be injected into the controller under
		// test.
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

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

	@Test
	public void getUser() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user").accept(MediaType.APPLICATION_JSON_UTF8)
						.param("account", str).param("password", str))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		System.out.println("输出:" + mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void testGet() throws Exception {

		int adminId = 3;

		mockMvc.perform(get("/user/" + adminId)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("data.age", is(15))).andExpect(jsonPath("data.firstName", is("bob")))
				.andExpect(jsonPath("data.id", is(adminId))).andExpect(jsonPath("success", is(true)));
	}

	@Test
	public void testSave() throws Exception {
		mockMvc.perform(post("/api/add").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(BaseTest.convertObjectToJsonBytes(user))).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		// .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("success",
		// is(true)));
	}

	@Test
	public void testUpdate() throws Exception {
		mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(BaseTest.convertObjectToJsonBytes(user))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("success", is(true)));
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/user/3")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("success", is(true)));
	}
}