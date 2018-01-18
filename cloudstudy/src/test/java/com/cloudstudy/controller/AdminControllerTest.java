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
import com.cloudstudy.bo.Admin;
import com.cloudstudy.service.AdminService;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AdminControllerTest {

	@Autowired
	private AdminController adminController;

	private MockMvc mockMvc;

	@Spy
	@Autowired
	private AdminService adminService;

	private Admin admin = new Admin();
	private String str = BaseTest.str;

	@Before
	public void setup() {

		// this must be called for the @Mock annotations above to be processed
		// and for the mock service to be injected into the controller under
		// test.
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

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
	}

	@Test
	public void getAdmin() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/admin").accept(MediaType.APPLICATION_JSON_UTF8)
						.param("account", str).param("password", str))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		System.out.println("输出:" + mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void testGet() throws Exception {

		int adminId = 3;

		mockMvc.perform(get("/admin/" + adminId)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("data.age", is(15))).andExpect(jsonPath("data.firstName", is("bob")))
				.andExpect(jsonPath("data.id", is(adminId))).andExpect(jsonPath("success", is(true)));
	}

	@Test
	public void testSave() throws Exception {
		mockMvc.perform(post("/api/add").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(BaseTest.convertObjectToJsonBytes(admin))).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		// .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("success",
		// is(true)));
	}

	@Test
	public void testUpdate() throws Exception {
		mockMvc.perform(put("/admin").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(BaseTest.convertObjectToJsonBytes(admin))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("success", is(true)));
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/admin/3")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("success", is(true)));
	}
}