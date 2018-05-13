package com.dt.betting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dt.betting.db.domain.IdGenerator;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.UserDataRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { IdGenerator.class, UserDataRepository.class, UserController.class })
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testDisplaying() throws Exception {
		// given

		// when
		mockMvc.perform(get("/ds/users"))

				// then
				.andExpect(status().isOk())//
				.andExpect(view().name("viewUsers"))//
				.andExpect(forwardedUrl("viewUsers"))//
				.andExpect(model().attribute("users", Arrays.asList()));//

	}

	@Test
	public void testAddUserWithoutName() throws Exception {
		// given

		// when
		mockMvc.perform(get("/ds/user/add"))

				// then
				.andExpect(status().isBadRequest());//
	}

	@Test
	public void testAddUserWithName() throws Exception {
		// given
		User user = createUser();

		// when
		mockMvc.perform(get("/ds/user/add").param("userName", "NewPlayer"))

				// then
				.andExpect(status().isOk())//
				.andExpect(view().name("viewUsers"))//
				.andExpect(forwardedUrl("viewUsers"))//
				.andExpect(model().attribute("users", Arrays.asList(user)));//

	}

	private User createUser() {
		User user = new User();
		user.setId(0L);
		user.setName("NewPlayer");
		return user;
	}
}
